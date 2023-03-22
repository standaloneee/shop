package ru.shop.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.shop.dto.CustomerDto;
import ru.shop.entity.Customer;
import ru.shop.entity.Notification;
import ru.shop.entity.Organization;
import ru.shop.entity.RegistryRequest;
import ru.shop.entity.SellHistory;
import ru.shop.exception.AdminRoleChangingException;
import ru.shop.exception.CustomerNotFoundException;
import ru.shop.exception.EmptyPageException;
import ru.shop.mapper.UserMapper;
import ru.shop.repository.CustomerRepository;
import ru.shop.utils.ByteSequenceGenerator;

import javax.security.auth.message.AuthException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RoleService roleService;
    private final SellHistoryService sellHistoryService;
    private final NotificationService notificationService;

    private final OrganizationRegistryRequestService organizationRegistryRequestService;


    public Optional<Customer> getByLogin(@NonNull String login) {
        return customerRepository.findCustomerByUserName(login);
    }

    public void register(CustomerDto customerDto) throws AuthException {
        if (getByLogin(customerDto.getUserName()).isPresent()) {
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
        if (sellHistoryService.getPageHistoryByUserId(id, pageable).isEmpty()) {
            throw new EmptyPageException();
        } else {
            return sellHistoryService.getPageHistoryByUserId(id, pageable);
        }
    }

    public Customer editBalance(UUID customerId, double balance) {
        Customer customer = customerRepository.findCustomerById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId.toString()));
        customer.setBalance(balance);
        return customerRepository.save(customer);
    }

    public Customer addBalance(UUID customerId, double amount) {
        Customer customer = customerRepository.findCustomerById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId.toString()));
        customer.setBalance(customer.getBalance() + amount);
        return customerRepository.save(customer);
    }

    public Set<Customer> findAllCustomers() {
        return new HashSet<>(customerRepository.findAll()); // не очень хороший вариант решения, из List -> Set, но пока так
    }

    public Customer findCustomer(UUID customerId) {
        return customerRepository.findCustomerById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId.toString()));
    }


    public String deleteCustomer(UUID customerId) {
        Customer customer = customerRepository.findCustomerById(customerId).orElseThrow(
                () -> new CustomerNotFoundException(customerId.toString())
        );
        customerRepository.delete(customer);
        return "Successful!";
    }

    public Customer detainCustomer(UUID customerId) {
        Customer customer = customerRepository.findCustomerById(customerId).orElseThrow(
                () -> new CustomerNotFoundException(customerId.toString()
                )
        );
        if(customer.getRoles().stream().anyMatch(item -> item.getRoleName().equals("ADMIN"))){
            throw new AdminRoleChangingException();
        }
        customer.setRoles(Set.of(roleService.findRoleByName("DETAINED")));
        return customerRepository.save(customer);
    }

    public Customer sendNotification(UUID customerId, Notification notification) {
        Customer customer = customerRepository.findCustomerById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId.toString()));
        notification.setId(UUID.randomUUID());
        notificationService.saveNotification(notification);
        customer.addNotification(notification);
        return customerRepository.save(customer);
    }

    public Set<Notification> getNotifications(UUID customerId) {
        return customerRepository.findCustomerById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId.toString())).getNotifications();
    }

    public RegistryRequest requestOrganizationRegistry(Customer customer, Organization organization) {
        organization.setId(UUID.nameUUIDFromBytes(ByteSequenceGenerator.StringsToByteArray(organization.getDescription(), organization.getLogo_url())));
        RegistryRequest registryRequest = new RegistryRequest();
        organization.setStatus("Pending");
        registryRequest.setOrganization(organization);
        registryRequest.setCustomer(customer);
        registryRequest.setApplication_status("Pending");
        registryRequest.setId(UUID.nameUUIDFromBytes(ByteSequenceGenerator.UUIDtoByteArray(organization.getId(), customer.getId())));
        return organizationRegistryRequestService.saveRegistryRequest(registryRequest);
    }
}


