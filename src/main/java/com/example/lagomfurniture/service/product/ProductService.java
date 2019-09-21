package com.example.lagomfurniture.service.product;

import com.example.lagomfurniture.model.Product;
import com.example.lagomfurniture.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product getProductDetail(@PathVariable Long id) {
        return productRepository.findById(id).get();
    }

}
