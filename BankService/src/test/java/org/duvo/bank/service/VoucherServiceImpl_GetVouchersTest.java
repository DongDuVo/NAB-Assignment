package org.duvo.bank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.duvo.bank.entity.Voucher;
import org.duvo.bank.repo.VoucherRepository;
import org.duvo.bank.service.impl.VoucherServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class VoucherServiceImpl_GetVouchersTest {

    @Mock
    private VoucherRepository voucherRepo;
    
    @InjectMocks
    private VoucherServiceImpl voucherService;
    
    @BeforeAll
    public static void setup() {
        MockitoAnnotations.openMocks(VoucherServiceImpl_GetVouchersTest.class);
    }
    
    @BeforeEach
    public void init() {
        Mockito.reset(voucherRepo);
    }
    
    @Test
    public void shouldReturnVoucherCodes() {
        String phoneNumber = "0123456789";
        when(voucherRepo.findByPhoneNumber(Mockito.anyString())).thenReturn(getVouchers(phoneNumber));
        
        List<String> vouchers = voucherService.getVouchers(phoneNumber);
        assertTrue(vouchers.size() == 1);
        assertEquals("code", vouchers.get(0));
    }
    
    private List<Voucher> getVouchers(String phoneNumber) {
        Voucher v = new Voucher();
        v.setId(1);
        v.setPhoneNumber(phoneNumber);
        v.setVoucherCode("code");
        
        return List.of(v);
    }
}
