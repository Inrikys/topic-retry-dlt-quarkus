package org.inrikys.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import org.inrikys.adapters.store.entities.ProductEntity;
import org.inrikys.adapters.store.repository.ProductRepository;
import org.inrikys.domain.models.Product;
import org.inrikys.domain.ports.GetProductsPort;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class GetProductsAdapter implements GetProductsPort {

    private final ProductRepository productRepository;

    public GetProductsAdapter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProducts() {
        List<ProductEntity> productEntities = productRepository.findAll().list();
        return productEntities.stream().map(ProductEntity::toProduct).toList();
    }

    @Override
    public Boolean existsById(Long productId) {
        Optional<ProductEntity> possibleProduct = productRepository.findByIdOptional(productId);
        return possibleProduct.isPresent();
    }
}
