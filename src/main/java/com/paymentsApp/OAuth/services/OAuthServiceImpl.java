/*package com.paymentsApp.OAuth.services;

import com.paymentsApp.OAuth.models.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;*/
/*
@Service
public class OAuthServiceImpl implements OAuthService {

    @Autowired
    private RestTemplate oauthRest;

    @Override
    public Optional<ClientDTO> findByEmail(String email) {
        Optional<ClientDTO> client = Optional.of(oauthRest.getForObject("http://localhost:8090/api/clients/findByEmail/{email}", ClientDTO.class));
        return client;
    }

}*/
