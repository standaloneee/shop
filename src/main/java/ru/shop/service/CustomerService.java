package ru.shop.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shop.dto.CustomerDto;
import ru.shop.entity.Customer;
import ru.shop.mapper.UserMapper;
import ru.shop.repository.CustomerRepository;

import javax.security.auth.message.AuthException;
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

    public void register(CustomerDto customerDto) throws AuthException {
        if(getByLogin(customerDto.getUserName()).isPresent()){
            throw new AuthException("User " + customerDto.getUserName() + " already exists");
        }
        if(customerDto.getRole().equals("ADMIN")){
            throw new AuthException("User cannot be set as ADMIN when registering");
        }
        Customer customer = UserMapper.INSTANCE.register(customerDto, roleService);
        customerRepository.save(customer);
    }

    public Customer getById(UUID id) {
        return customerRepository.getById(id);
    }
}
