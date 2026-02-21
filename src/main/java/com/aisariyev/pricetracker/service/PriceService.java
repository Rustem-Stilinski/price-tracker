package com.aisariyev.pricetracker.service;

import com.aisariyev.pricetracker.exception.ResourceNotFoundException;
import com.aisariyev.pricetracker.model.Price;
import com.aisariyev.pricetracker.model.Product;
import com.aisariyev.pricetracker.model.Store;
import com.aisariyev.pricetracker.model.User;
import com.aisariyev.pricetracker.repository.PriceRepository;
import com.aisariyev.pricetracker.repository.ProductRepository;
import com.aisariyev.pricetracker.repository.StoreRepository;
import com.aisariyev.pricetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceRepository priceRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public List<Price> getAllPrices() {
        return priceRepository.findAll();
    }

    public Price getPriceById(Long id) {
        return priceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Price not found with id: " + id));
    }

    public List<Price> getPricesByProduct(Long productId) {
        return priceRepository.findByProductId(productId);
    }

    public List<Price> getPricesByStore(Long storeId) {
        return priceRepository.findByStoreId(storeId);
    }

    public List<Price> getPricesByProductAndStore(Long productId, Long storeId) {
        return priceRepository.findByProductIdAndStoreId(productId, storeId);
    }

    public List<Price> getCheapestPricesForProduct(Long productId) {
        return priceRepository.findCheapestByProductId(productId);
    }

    public List<Price> getSaleItems() {
        return priceRepository.findByIsOnSaleTrue();
    }

    public Price createPrice(Long productId, Long storeId, Long userId, Price price) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + storeId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        price.setProduct(product);
        price.setStore(store);
        price.setUser(user);

        return priceRepository.save(price);
    }

    public void deletePrice(Long id) {
        Price price = getPriceById(id);
        priceRepository.delete(price);
    }
}
