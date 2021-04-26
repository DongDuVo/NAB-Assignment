package org.duvo.voucher.controller;

import org.duvo.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/voucher")
public class VoucherController {
    
    @Autowired
    private VoucherService service;

    @GetMapping
    public String getVoucher() {
        return service.getVoucher();
    }
}
