package com.paymentsApp.OAuth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Permiso que van a tener nuestros endpoints del servidor de autorizacion de OAuth2
    // para generar el token y validarlo
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // Endpoints protegidos por HTTP Basic usando las credenciales del usuario enviados en la cabecera
        // de la peticion

        // Endpoint para generar el token y autenticarnos -> POST: /oauth/token
        security.tokenKeyAccess("permitAll()")
        // Valida que el usuario este autenticado
        .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // Registrar usuarios
        clients.inMemory().withClient("usuario")
                .secret(passwordEncoder.encode("123"))
                // Alcance/permiso de la app
                .scopes("read", "write")
                // Como hacer el tipo de autorizacion - como obtener el token -> password (username y contraseña)
                // Envio dos credenciales: la de la app del cliente y las credenciales del usuario
                .authorizedGrantTypes("password", "refresh_token")
                // Cuanto dura el token -> 1 h
                .accessTokenValiditySeconds(3600)
                // Refresh token refresca el token actual antes de que caduque
                .refreshTokenValiditySeconds(3600);
    }

    // Relacionado al endpoint de OAuth2 del servidor de autorizacion que se encarga de generar el endpoint
    // POST -> /oauth/token
    // Recibe el username, password, grantType(tipo de otorgamiento del password via credenciales)
    // y el client_id con el secreto que corresponde a las credenciales del micro de CLients
    // Si toodo sale bien genera un token con los datos y retorna un JSON con el token al usuario
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                // TokenStore: componente que genera el token
                // con los datos del AccessTokenConverter
                .tokenStore(tokenStore())
                // AccessTokenConverter: se encarga de tomar los datos del User y
                // convertirlos en el token codificados
                .accessTokenConverter(accessTokenConverter());
    }

    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    // Se genera con el componente Spring
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        // Asigna la llave de la firma
        tokenConverter.setSigningKey("super_secreto");
        return tokenConverter;
    }

}
