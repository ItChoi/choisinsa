package com.mall.choisinsa.service;

import com.mall.choisinsa.domain.Stock;
import com.mall.choisinsa.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StockService {
    private final StockRepository stockRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized void decrease(Long id, Long quantity) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException());

        stock.decrease(quantity);

        stockRepository.save(stock);
    }
}
