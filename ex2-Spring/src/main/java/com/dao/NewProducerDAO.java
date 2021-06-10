package com.dao;

import com.model.Customer;
import com.model.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewProducerDAO extends JpaRepository<Customer, Long> {
}
