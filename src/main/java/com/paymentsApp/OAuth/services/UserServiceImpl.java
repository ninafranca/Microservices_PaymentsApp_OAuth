package com.paymentsApp.OAuth.services;


import com.paymentsApp.OAuth.clients.UserFeignClient;
import com.paymentsApp.OAuth.models.ClientDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserFeignClient userFeignClient;
    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ClientDTO clientDTO1 = userFeignClient.findByEmail(username);
        if (clientDTO1 == null) {
            throw new UsernameNotFoundException("The following client: " + username + " is not in the system");
        }
        List<GrantedAuthority> authorities = clientDTO1.getRoles().stream().map(roles -> new SimpleGrantedAuthority(roles.getName())).collect(Collectors.toList());
        log.info("User " + username + " authenticated");
        return new User(clientDTO1.getEmail(), clientDTO1.getPassword(), authorities);
    }

    /*@Autowired
    private ClientDTO clientDTO;
    @Autowired
    private OAuthService oAuthService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ClientDTO> clientDTO1 = oAuthService.findByEmail(username);
        if (clientDTO1.isEmpty()) {
            throw new UsernameNotFoundException("The following client: " + username + " is not in the system");
        }
        List<GrantedAuthority> authorities = clientDTO.getRoles().stream().map(roles -> new SimpleGrantedAuthority(roles.getName())).collect(Collectors.toList());
        return new User(clientDTO1.get().getEmail(), clientDTO1.get().getPassword(), authorities);
    }*/

}
