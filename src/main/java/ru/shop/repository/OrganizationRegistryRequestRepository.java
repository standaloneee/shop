package ru.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shop.entity.RegistryRequest;

import java.util.UUID;

@Repository
public interface OrganizationRegistryRequestRepository extends JpaRepository<RegistryRequest, UUID> {

}
