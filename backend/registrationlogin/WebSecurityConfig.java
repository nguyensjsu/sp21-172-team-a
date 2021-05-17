package com.example.registrationlogin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/**").permitAll().anyRequest().authenticated().and().formLogin()
                .loginPage("/signIn").permitAll().and().logout().logoutSuccessUrl("/signIn").permitAll();
        http.cors().and().csrf().disable();
    }

    // cashier's app & backoffice
    // employee login -> allows you to access everything on this app = not a blacklist/whitelist 

    // customer
    // already on app -> customer login -> allows you to access account info = blacklist

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .antMatchers("/h2-console/**");
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser("admin").password("admin1pass").roles("USER", "ADMIN").and()
                .withUser("user1").password("user1pass").roles("USER").and()
                .withUser("user2").password("user2pass").roles("USER").and()
                .withUser("user3").password("user3pass").roles("USER");
    }

    @Bean
    public InMemoryUserDetailsManager getInMemoryUserDetailsManager(){
        return new InMemoryUserDetailsManager();
    }
}
