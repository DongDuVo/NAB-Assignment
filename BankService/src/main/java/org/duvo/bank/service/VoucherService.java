package org.duvo.bank.service;

import java.util.List;

public interface VoucherService {

    String getVoucher(String phoneNumber);
    
    List<String> getVouchers(String phoneNumber);
}
