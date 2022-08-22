package com.mall.choisinsa.service;

import com.mall.choisinsa.domain.Stock;
import com.mall.choisinsa.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class StockService {

    private final StockRepository stockRepository;

    @Transactional
    public void decrease(Long id, Long quantity) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException());

        stock.decrease(quantity);

        stockRepository.save(stock);
    }
}
