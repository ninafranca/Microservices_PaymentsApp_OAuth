package com.paymentsApp.OAuth.clients;

import com.paymentsApp.OAuth.models.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clients-service")
public interface UserFeignClient {
    @GetMapping("/api/client/findByEmail/{email}")
    public ClientDTO findByEmail(@PathVariable String email);

}
