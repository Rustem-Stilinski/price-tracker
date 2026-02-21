package com.aisariyev.pricetracker.service;

import com.aisariyev.pricetracker.exception.ResourceNotFoundException;
import com.aisariyev.pricetracker.model.Store;
import com.aisariyev.pricetracker.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    @Cacheable("stores")
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Cacheable(value = "stores", key = "#id")
    public Store getStoreById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + id));
    }

    public List<Store> getStoresByLocation(String location) {
        return storeRepository.findByLocation(location);
    }

    @CacheEvict(value = "stores", allEntries = true)
    public Store createStore(Store store) {
        return storeRepository.save(store);
    }

    @CacheEvict(value = "stores", allEntries = true)
    public Store updateStore(Long id, Store updated) {
        Store existing = getStoreById(id);
        existing.setName(updated.getName());
        existing.setLocation(updated.getLocation());
        existing.setWebsite(updated.getWebsite());
        return storeRepository.save(existing);
    }

    @CacheEvict(value = "stores", allEntries = true)
    public void deleteStore(Long id) {
        Store store = getStoreById(id);
        storeRepository.delete(store);
    }
}
