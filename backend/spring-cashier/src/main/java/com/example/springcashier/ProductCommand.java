package com.example.springcashier;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table()
@Data
@RequiredArgsConstructor
class ProductCommand {
    private @Id Long id;
    private String name;
    private int quantity;
    private double price;

}
