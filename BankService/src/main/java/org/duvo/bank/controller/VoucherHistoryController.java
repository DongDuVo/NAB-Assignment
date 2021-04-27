package org.duvo.bank.controller;

import java.util.List;

import org.duvo.bank.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/voucher-history")
public class VoucherHistoryController {
    
    @Autowired
    private VoucherService voucherService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<String> getVouchersByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
        return voucherService.getVouchers(phoneNumber);
    }
}
