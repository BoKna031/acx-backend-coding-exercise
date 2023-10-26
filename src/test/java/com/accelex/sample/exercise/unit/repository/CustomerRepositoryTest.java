package com.accelex.sample.exercise.unit.repository;

import com.accelex.sample.exercise.model.Customer;
import com.accelex.sample.exercise.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Customer repository tests")
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;
/*
    @Test
    public void saveCustomer(){
        Customer customer = createCustomer();
        Customer savedCustomer = customerRepository.save(customer);

        assertNotNull(savedCustomer.getId());
        assertEquals(savedCustomer.getFirstName(), customer.getFirstName());
        assertEquals(savedCustomer.getLastName(), customer.getLastName());
        assertEquals(savedCustomer.getBirthDate(), customer.getBirthDate());
    }

    @Test
    public void findCustomerById() {
        Customer customer = createCustomer();
        customerRepository.save(customer);

        Customer foundCustomer = customerRepository.findById(customer.getId()).orElse(null);
        assertNotNull(foundCustomer);
        assertEquals(customer.getFirstName(), foundCustomer.getFirstName());
        assertEquals(customer.getLastName(), foundCustomer.getLastName());
        assertEquals(customer.getBirthDate(), foundCustomer.getBirthDate());
    }
    @Test
    public void findNonExistentCustomer() {
        Customer nonExistentCustomer = customerRepository.findById(-1).orElse(null);

        assertNull(nonExistentCustomer);
    }

    @Test
    public void deleteCustomer(){
        Customer customer = createCustomer();
        Customer savedCustomer = customerRepository.save(customer);
        customerRepository.delete(customer);
        Optional<Customer> searchedCustomer = customerRepository.findById(savedCustomer.getId());

        Assertions.assertThat(searchedCustomer.isEmpty()).isTrue();
    }

    @Test
    public void saveThrowsDataIntegrityViolationExceptionWhenFirstNameIsNull(){
        Customer customer = createCustomer();
        customer.setFirstName(null);
        Assertions.assertThatThrownBy(() -> customerRepository.save(customer))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void saveThrowsDataIntegrityViolationExceptionWhenLastNameIsNull(){
        Customer customer = createCustomer();
        customer.setLastName(null);
        Assertions.assertThatThrownBy(() -> customerRepository.save(customer))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void saveThrowsDataIntegrityViolationExceptionWhenBirthDateIsNull(){
        Customer customer = createCustomer();
        customer.setBirthDate(null);
        Assertions.assertThatThrownBy(() -> customerRepository.save(customer))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    private Customer createCustomer(){
        Customer customer = new Customer();
        customer.setBirthDate(new Date(1996, 10, 21));
        customer.setFirstName("Eva");
        customer.setLastName("Ras");
        return customer;
    }*/

}