package org.duvo.bank.service.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.duvo.bank.client.VoucherClient;
import org.duvo.bank.entity.Voucher;
import org.duvo.bank.repo.VoucherRepository;
import org.duvo.bank.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class VoucherServiceImpl implements VoucherService {
    
    @Autowired
    private VoucherClient voucherClient;
    
    @Autowired
    private VoucherRepository voucherRepo;
    
    @Autowired
    private TaskExecutor taskExecutor;
    
    @Value("${thread-pool.timeout-in-seconds}")
    private int timeout;

    @Override
    public String getVoucher(String phoneNumber) {
        Voucher voucher;
        try {
            voucher = CompletableFuture.supplyAsync(() -> {
                String voucherCode = voucherClient.getVoucher();
                Voucher v = new Voucher();
                v.setPhoneNumber(phoneNumber);
                v.setVoucherCode(voucherCode);
                return voucherRepo.save(v);
            }, taskExecutor).completeOnTimeout(null, timeout, TimeUnit.SECONDS).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        if (voucher != null) {
            voucher.setProcessed(true);
            voucherRepo.save(voucher);
            return voucher.getVoucherCode();
        }
        return "The request is being processed within 30 seconds";
    }

    @Override
    public List<String> getVouchers(String phoneNumber) {
        List<Voucher> vouchers = voucherRepo.findByPhoneNumber(phoneNumber);
        return vouchers.stream().map(Voucher::getVoucherCode).collect(Collectors.toList());
    }

}
