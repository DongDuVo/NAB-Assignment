package org.duvo.bank.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.duvo.bank.client.VoucherClient;
import org.duvo.bank.entity.Voucher;
import org.duvo.bank.repo.VoucherRepository;
import org.duvo.bank.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoucherServiceImpl implements VoucherService {
    
    @Autowired
    private VoucherClient voucherClient;
    
    @Autowired
    private VoucherRepository voucherRepo;

    @Override
    public String getVoucher(String phoneNumber) {
        String voucher = voucherClient.getVoucher();
        
        Voucher v = new Voucher();
        v.setPhoneNumber(phoneNumber);
        v.setVoucherCode(voucher);
        
        voucherRepo.save(v);
        return voucher;
    }

    @Override
    public List<String> getVouchers(String phoneNumber) {
        List<Voucher> vouchers = voucherRepo.findByPhoneNumber(phoneNumber);
        return vouchers.stream().map(Voucher::getVoucherCode).collect(Collectors.toList());
    }

}
