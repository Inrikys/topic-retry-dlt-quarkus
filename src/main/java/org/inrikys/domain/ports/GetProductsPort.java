package org.inrikys.domain.ports;

import org.inrikys.domain.models.Product;

import java.util.List;

public interface GetProductsPort {

    List<Product> getProducts();

    Boolean existsById(Long productId);

}
