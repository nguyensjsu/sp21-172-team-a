package com.example.starbucksmenu;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}