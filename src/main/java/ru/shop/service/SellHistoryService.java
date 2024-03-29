package ru.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.shop.entity.SellHistory;
import ru.shop.exception.ProductNotFoundException;
import ru.shop.repository.SellHistoryRepository;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SellHistoryService {

    private final SellHistoryRepository sellHistoryRepository;

    public SellHistory saveSellHistory(SellHistory sellHistory) {
        return sellHistoryRepository.save(sellHistory);
    }


    public Set<SellHistory> getAllHistoryByUserId(UUID id) {
        return sellHistoryRepository.findSellHistoriesByCustomer_Id(id);
    }
    public Page<SellHistory> getPageHistoryByUserId(UUID id, Pageable pageable) {
        return sellHistoryRepository.findSellHistoriesByCustomer_Id(id, pageable);
    }

    public SellHistory findSellHistoryByProductId(UUID productId, UUID customerId){
        return sellHistoryRepository.findSellHistoryByProduct_IdAndCustomer_Id(productId, customerId).orElseThrow(() -> new ProductNotFoundException(productId.toString()));
    }
}


