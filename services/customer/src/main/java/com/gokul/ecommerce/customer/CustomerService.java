package com.gokul.ecommerce.customer;

import com.gokul.ecommerce.exception.CustomerNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public String createCustomer(@Valid CustomerRequest customerRequest) {
        var customer = customerRepository.save(customerMapper.toCustomer(customerRequest));
        return customer.getId();
    }

    public void updateCustomer(@Valid CustomerRequest customerRequest) {
        var customer = customerRepository.findById(customerRequest.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cannot update customer:: Customer with id %s not found", customerRequest.id())
                ));
        mergerCustomer(customer, customerRequest);
        customerRepository.save(customer);
    }

    private void mergerCustomer(Customer customer, CustomerRequest customerRequest) {
        if (StringUtils.isNotBlank(customerRequest.firstName())) {
            customer.setFirstName(customerRequest.firstName());
        }
        if (StringUtils.isNotBlank(customerRequest.lastName())) {
            customer.setLastName(customerRequest.lastName());
        }
        if (StringUtils.isNotBlank(customerRequest.email())) {
            customer.setEmail(customerRequest.email());
        }
        if (customerRequest.address() != null) {
            customer.setAddress(customerRequest.address());
        }

    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean customerExists(String id) {
        if (StringUtils.isBlank(id)) {
            return false;
        }
        return customerRepository.existsById(id);
    }

    public CustomerResponse findCustomerById(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        var customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Customer with id %s not found", id)
                ));
        return customerMapper.fromCustomer(customer);
    }

    public void deleteCustomer(String id) {
        if (StringUtils.isBlank(id)) {
            return;
        }
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException(
                    String.format("Customer with id %s not found", id)
            );
        }
        customerRepository.deleteById(id);
    }
}
