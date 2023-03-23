package ru.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shop.entity.RegistryRequest;
import ru.shop.repository.OrganizationRegistryRequestRepository;

@Service
@RequiredArgsConstructor
public class OrganizationRegistryRequestService {

    private final OrganizationRegistryRequestRepository organizationRegistryRequestRepository;


    public RegistryRequest saveRegistryRequest(RegistryRequest registryRequest){
        return organizationRegistryRequestRepository.save(registryRequest);
    }



}
