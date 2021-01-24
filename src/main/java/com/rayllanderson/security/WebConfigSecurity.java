package com.rayllanderson.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.rayllanderson.services.UserService;

@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private UserService userService;
    
    @Override //configura as solicitações por http
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()//desabilitando as config padrões de memória
        .authorizeRequests() //permite restringir acessos
        .antMatchers(HttpMethod.GET, "/").permitAll() //qualquer user acessa a url "/" inicial
        .antMatchers(HttpMethod.GET, "/usuarios/**").hasAnyRole("ADMIN") //apenas admin podem acessar controller usuarios por GET
        .antMatchers(HttpMethod.POST, "/usuarios/**").hasAnyRole("ADMIN") //apenas admin podem acessar controller usuarios por POST
        .anyRequest().authenticated()
        .and().formLogin().permitAll() // form login permite qualquer user
        .and().logout() //mapeando URL de logout e invalida user atualmente logado
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }
    
    @Override //cria autenticação com user with database ou memória
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(userService).passwordEncoder(this.passwordEncoder());
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
