package com.example.lagomfurniture.service.productservice;

import com.example.lagomfurniture.model.Product;
import com.example.lagomfurniture.repository.ProductIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductIdRepository productIdRepository;

    public List<Product> getProductListByCategory(String productCategory){
        return productIdRepository.findByProductCategory(productCategory);
    }
}
