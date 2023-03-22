package ru.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shop.entity.SellHistory;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface SellHistoryRepository extends JpaRepository<SellHistory, UUID> {
    Page<SellHistory> findSellHistoriesByCustomer_Id(UUID id, Pageable pageable);
    Set<SellHistory> findSellHistoriesByCustomer_Id(UUID id);

    Optional<SellHistory> findSellHistoryByProduct_IdAndCustomer_Id(UUID productId, UUID customerId);
}
