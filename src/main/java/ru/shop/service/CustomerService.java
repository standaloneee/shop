package ru.shop.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.shop.dto.CustomerDto;
import ru.shop.entity.Customer;
import ru.shop.entity.SellHistory;
import ru.shop.exception.CustomerNotFoundException;
import ru.shop.exception.EmptyPageException;
import ru.shop.mapper.UserMapper;
import ru.shop.repository.CustomerRepository;

import javax.security.auth.message.AuthException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RoleService roleService;
    private final SellHistoryService sellHistoryService;

    public Optional<Customer> getByLogin(@NonNull String login) {
        return customerRepository.findCustomerByUserName(login);
    }

    public void register(CustomerDto customerDto) throws AuthException {
        if(getByLogin(customerDto.getUserName()).isPresent()){
            throw new AuthException("User " + customerDto.getUserName() + " already exists");
        }
//        if(customerDto.getRole().equals("ADMIN")){
//            throw new AuthException("User cannot be set as ADMIN when registering");
//        }
        Customer customer = UserMapper.INSTANCE.register(customerDto, roleService);
        customerRepository.save(customer);
    }

    public Customer findById(UUID id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id.toString()));
    }
    public Customer findByName(String name) {
        return customerRepository.findCustomerByUserName(name).orElseThrow(() -> new CustomerNotFoundException(name));
    }

    public Set<SellHistory> getAllCustomerBuyHistory(UUID id) {

            return sellHistoryService.getAllHistoryByUserId(id);

    }
    public Page<SellHistory> getCustomerBuyHistoryPage(UUID id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if(sellHistoryService.getPageHistoryByUserId(id,pageable).isEmpty()){
            throw new EmptyPageException();
        }
        else {
            return sellHistoryService.getPageHistoryByUserId(id, pageable);
        }
    }
}
