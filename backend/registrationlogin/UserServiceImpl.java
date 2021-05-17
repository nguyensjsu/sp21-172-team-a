package com.example.registrationlogin;


import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import net.guides.springboot.loginregistrationspringbootauthjsp.model.User;
import net.guides.springboot.loginregistrationspringbootauthjsp.repository.RoleRepository;
import net.guides.springboot.loginregistrationspringbootauthjsp.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void save(Customer customer) {
		user.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
		userRepository.save(user);
	}

	@Override
	public User findById(int id) {
		return userRepository.findById(id);
	}
}