package org.duvo.bank.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.duvo.bank.service.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class VoucherHistoryController_GetVouchersByPhoneNumberTest {

    @MockBean
    private VoucherService voucherService;
    
    @Autowired
    private MockMvc mockMvc;
    
    private String endpoint = "/voucher-history";
    
    @BeforeEach
    public void init() {
        reset(voucherService);
    }
    
    @WithMockUser(roles = "ADMIN")
    @Test
    public void shouldReturn200ForAdminUser() throws Exception {
        when(voucherService.getVouchers("0123456789")).thenReturn(List.of("code"));
        
        MockHttpServletRequestBuilder requestBuilder = get(endpoint).param("phoneNumber", "0123456789");
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        
        resultActions.andExpect(status().is(HttpStatus.OK.value()));
        
        String result = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals("[\"code\"]", result);
    }
    
    @WithAnonymousUser
    @Test
    public void shouldReturn401ForAnonymousUser() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get(endpoint).param("phoneNumber", "0123456789");
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        
        resultActions.andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }
    
    @WithMockUser(roles = "USER")
    @Test
    public void shouldReturn403ForOtherUser() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get(endpoint).param("phoneNumber", "0123456789");
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        
        resultActions.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }
}
