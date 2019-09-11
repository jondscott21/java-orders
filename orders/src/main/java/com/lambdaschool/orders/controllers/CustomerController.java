package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class CustomerController
{
    @Autowired
    private CustomerService customerService;

    // GET localhost:2019/customer/order
    @GetMapping(value = "/customer/order", produces = {"application/json"})
    public ResponseEntity<?> getAllCustomers()
    {
        List<Customer> myCustomers = customerService.findAll();
        return new ResponseEntity<>(myCustomers, HttpStatus.OK);
    }

    // GET localhost:2019/customer/name/{custname}

    @GetMapping(value = "/customer/name/{custname}", produces = {"application/json"})
    public ResponseEntity<?> getCustomerByName(@PathVariable String custname)
    {
        Customer myCustomer = customerService.findCustomerByName(custname);
        return new ResponseEntity<>(myCustomer, HttpStatus.OK);
    }

    // POST http://localhost:2019/data/customer/new
    @PostMapping(value = "/data/customer/new",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addNewCustomer(@Valid
                                            @RequestBody
                                                    Customer newCustomer) throws URISyntaxException
    {
        newCustomer = customerService.save(newCustomer);
        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{custcode}").buildAndExpand(newCustomer.getCustcode()).toUri();
        responseHeaders.setLocation(newCustomerURI);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    // DELETE http://localhost:2019/data/customer/delete/{custcode}
    @DeleteMapping(value = "/data/customer/delete/{custcode}")
    public ResponseEntity<?> deleteRestaurantById(
            @PathVariable
                    long custcode)
    {
        customerService.delete((custcode));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // PUT http://localhost:2019/data/customer/update/{custcode}
    @PutMapping(value = "/data/customer/update/{custcode}",
            produces = {"application/json"},
            consumes = {"application/json"})
    public ResponseEntity<?> updateRestaurant(
            @RequestBody
                    Customer updateCustomer,
            @PathVariable
                    long custcode)
    {
        customerService.update(updateCustomer, custcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
