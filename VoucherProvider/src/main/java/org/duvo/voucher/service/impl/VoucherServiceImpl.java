package org.duvo.voucher.service.impl;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

import org.duvo.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VoucherServiceImpl implements VoucherService {
    
    private static final Logger LOG = LoggerFactory.getLogger(VoucherServiceImpl.class);
    private static final Random r = new Random();
    
    @Value("${delay-in-seconds.min}")
    private int minDelay;
    
    @Value("${delay-in-seconds.max}")
    private int maxDelay;

    @Override
    public String getVoucher() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        String code = Base64.getEncoder().withoutPadding().encodeToString(bb.array()).substring(0, 20);
        
        int secondsToWait = r.nextInt(maxDelay + 1);
        while (secondsToWait < minDelay) {
            secondsToWait = r.nextInt(maxDelay + 1);
        }
        LOG.info("Wait for {} seconds", secondsToWait);
        try {
            Thread.sleep(secondsToWait * 1000);
            return code;
        } catch (InterruptedException e) {
            LOG.error("Getting voucher with error ", e);
            throw new RuntimeException(e);
        }
    }
}
