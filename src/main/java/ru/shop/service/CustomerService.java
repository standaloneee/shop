package ru.shop.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shop.dto.CustomerDto;
import ru.shop.entity.Customer;
import ru.shop.mapper.UserMapper;
import ru.shop.repository.CustomerRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RoleService roleService;

    public Optional<Customer> getByLogin(@NonNull String login) {
        return customerRepository.findCustomerByUserName(login);
    }

    public void register(CustomerDto customerDto) {
        Customer customer = UserMapper.INSTANCE.register(customerDto, roleService);
        customerRepository.save(customer);
    }

    public Customer getById(UUID id) {
        return customerRepository.getById(id);
    }
}
