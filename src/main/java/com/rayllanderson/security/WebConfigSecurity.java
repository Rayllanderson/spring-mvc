package com.rayllanderson.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{
    
    @Override //configura as solicitações por http
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()//desabilitando as config padrões de memória
        .authorizeRequests() //permite restringir acessos
        .antMatchers(HttpMethod.GET, "/").permitAll() //qualquer user acessa a url "/" inicial
        .anyRequest().authenticated()
        .and().formLogin().permitAll() // form login permite qualquer user
        .and().logout() //mapeando URL de logout e invalida user atualmente logado
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }
    
    @Override //cria autenticação com user with database ou memória
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.inMemoryAuthentication().withUser("ray").password(passwordEncoder().encode("123")).roles("ADMIN");
    }
    
    @Bean 
    public PasswordEncoder passwordEncoder() { 
        return new BCryptPasswordEncoder(); 
    }

    @Override //ignora url específicas;
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }
    

}
