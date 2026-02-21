package com.aisariyev.pricetracker.repository;

import com.aisariyev.pricetracker.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByName(String name);
    List<Store> findByLocation(String location);
}
