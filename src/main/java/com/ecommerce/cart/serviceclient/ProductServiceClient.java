package com.ecommerce.cart.serviceclient;



import com.ecommerce.cart.dto.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductServiceClient {
    List<ProductDto> getProductsByIds(List<UUID> productIds);
}
