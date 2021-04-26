package org.duvo.bank.client.impl;

import org.duvo.bank.client.VoucherClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VoucherClientImpl implements VoucherClient {
    
    @Value("${voucher-service.url}")
    private String url;
    
    @Value("${voucher-service.api-key}")
    private String apiKey;
    
    private static final String API_KEY = "x-api-key";
    
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getVoucher() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(API_KEY, apiKey);
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
        
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        return responseEntity.getBody();
    }

}
