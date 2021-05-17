
package com.example.registrationlogin;
import net.guides.springboot.loginregistrationspringbootauthjsp.model.User;

public interface UserService {
    void save(Customer customer);

    Customer findByUsername(int id);
}