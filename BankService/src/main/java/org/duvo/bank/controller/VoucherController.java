package org.duvo.bank.controller;

import org.duvo.bank.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/voucher")
public class VoucherController {
    
    @Autowired
    private VoucherService voucherService;

    @GetMapping
    public String getVoucher(@RequestParam("phoneNumber") String phoneNumber) {
        return voucherService.getVoucher(phoneNumber);
    }
}
