package com.lambdaschool.orders.repos;

import com.lambdaschool.orders.models.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomersRepository extends CrudRepository<Customer, Long>
{
    Customer findByCustname(String name);
}
