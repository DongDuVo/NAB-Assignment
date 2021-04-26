package org.duvo.bank.schedule;

import java.util.List;

import org.duvo.bank.entity.Voucher;
import org.duvo.bank.repo.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SendSmsScheduler {
    
    private static final Logger LOG = LoggerFactory.getLogger(SendSmsScheduler.class);
    
    @Autowired
    private VoucherRepository voucherRepo;

    @Async("taskExecutor")
    @Scheduled(fixedDelay = 30000, initialDelay = 1000)
    public void sendSms() {
        List<Voucher> vouchers = voucherRepo.findByProcessedFalse();
        LOG.info("Found {} voucher(s) need to be processed", vouchers.size());
        
        if (vouchers.isEmpty()) {
            return;
        }
        
        for (Voucher v : vouchers) {
            // TODO send sms
            
            v.setProcessed(true);
        }
        
        voucherRepo.saveAll(vouchers);
    }
}
