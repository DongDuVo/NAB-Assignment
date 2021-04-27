package org.duvo.bank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.List;

import org.duvo.bank.client.VoucherClient;
import org.duvo.bank.entity.Voucher;
import org.duvo.bank.repo.VoucherRepository;
import org.duvo.bank.service.impl.VoucherServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(SpringExtension.class)
public class VoucherServiceImpl_GetVoucherTest {

    @Mock
    private VoucherClient voucherClient;
    
    @Mock
    private VoucherRepository voucherRepo;
    
    private TaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
    
    @InjectMocks
    private VoucherServiceImpl voucherService;
    
    @BeforeAll
    public static void setup() {
        MockitoAnnotations.openMocks(VoucherServiceImpl_GetVoucherTest.class);
    }
    
    @BeforeEach
    public void init() {
        ReflectionTestUtils.setField(voucherService, "timeout", 5);
        ReflectionTestUtils.setField(voucherService, "taskExecutor", taskExecutor);
        Mockito.reset(voucherClient, voucherRepo);
    }
    
    @Test
    public void shouldReturnVoucherCode() {
        ArgumentCaptor<Voucher> argCaptor = ArgumentCaptor.forClass(Voucher.class);
        String phoneNumber = "0123456789";
        String code = "code";
        
        when(voucherClient.getVoucher()).thenReturn(code);
        when(voucherRepo.save(Mockito.any(Voucher.class))).then((invoke) -> {
            return getVoucher(invoke);
        });
        
        String voucher = voucherService.getVoucher(phoneNumber);
        
        assertEquals("code", voucher);
        
        Mockito.verify(voucherRepo, times(2)).save(argCaptor.capture());
        List<Voucher> vouchers = argCaptor.getAllValues();
        assertEquals(phoneNumber, vouchers.get(0).getPhoneNumber());
        assertEquals(code, vouchers.get(0).getVoucherCode());
        assertFalse(vouchers.get(0).isProcessed());
        
        assertEquals(phoneNumber, vouchers.get(1).getPhoneNumber());
        assertEquals(code, vouchers.get(1).getVoucherCode());
        assertTrue(vouchers.get(1).isProcessed());
    }

    private Object getVoucher(InvocationOnMock invoke) {
        Voucher arg = invoke.getArgument(0, Voucher.class);
        Voucher v = new Voucher();
        v.setId(1);
        v.setPhoneNumber(arg.getPhoneNumber());
        v.setProcessed(arg.isProcessed());
        v.setVoucherCode(arg.getVoucherCode());
        return v;
    }
    
    @Test
    public void shouldReturnMessageWhenGettingVoucherIsTimeout() {
        String phoneNumber = "0123456789";
        
        when(voucherClient.getVoucher()).then((invoke) -> {
            Thread.sleep(6000);
            return "";
        });
        when(voucherRepo.save(Mockito.any(Voucher.class))).then((invoke) -> {
            return getVoucher(invoke);
        });
        
        String voucher = voucherService.getVoucher(phoneNumber);
        
        assertEquals("The request is being processed within 30 seconds", voucher);
    }
}
