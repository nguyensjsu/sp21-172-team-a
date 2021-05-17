package com.example.registrationlogin;
import org.springframework.data.jpa.repository.JpaRepository;

import net.guides.springboot.loginregistrationspringbootauthjsp.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}