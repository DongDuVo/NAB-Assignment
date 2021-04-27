package org.duvo.bank.schedule;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.duvo.bank.entity.Voucher;
import org.duvo.bank.repo.VoucherRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class SendSmsScheduler_SendSmsTest {

    @Mock
    private VoucherRepository voucherRepo;
    
    @Captor
    private ArgumentCaptor<List<Voucher>> argCaptor;
    
    @InjectMocks
    private SendSmsScheduler scheduler;
    
    @BeforeAll
    public static void setup() {
        MockitoAnnotations.openMocks(SendSmsScheduler_SendSmsTest.class);
    }
    
    @BeforeEach
    public void init() {
        Mockito.reset(voucherRepo);
    }
    
    @Test
    public void shouldSendSmsAndUpdateDB() {
        when(voucherRepo.findByProcessedFalse()).thenReturn(getVouchers());
        
        scheduler.sendSms();
        
        verify(voucherRepo, times(1)).saveAll(argCaptor.capture());
        List<Voucher> vouchers = argCaptor.getValue();
        assertTrue(vouchers.size() == 1);
        assertTrue(vouchers.get(0).isProcessed());
    }
    
    private List<Voucher> getVouchers() {
        Voucher v = new Voucher();
        v.setId(1);
        v.setPhoneNumber("0123456789");
        v.setVoucherCode("code");
        v.setProcessed(false);
        
        return List.of(v);
    }
}
