package com.aisariyev.pricetracker.repository;

import com.aisariyev.pricetracker.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findByProductId(Long productId);
    List<Price> findByStoreId(Long storeId);
    List<Price> findByUserId(Long userId);
    List<Price> findByProductIdAndStoreId(Long productId, Long storeId);

    @Query("SELECT p FROM Price p WHERE p.product.id = :productId ORDER BY p.price ASC")
    List<Price> findCheapestByProductId(@Param("productId") Long productId);

    List<Price> findByIsOnSaleTrue();
}
