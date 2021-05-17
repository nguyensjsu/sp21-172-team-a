package com.example.registrationlogin;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.guides.springboot.loginregistrationspringbootauthjsp.model.Role;
import net.guides.springboot.loginregistrationspringbootauthjsp.model.User;
import net.guides.springboot.loginregistrationspringbootauthjsp.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private CustomerRepository customerRepository;
	
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User customer = customerRepository.findById(id);
        
        return new org.springframework.security.core.userdetails.Customer(customer.getUsername(), customer.getPassword(), grantedAuthorities);
    }
}