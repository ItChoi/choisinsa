package com.mall.choisinsa.repository;

import com.mall.choisinsa.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
