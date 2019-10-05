package com.example.lagomfurniture.service.productservice;

import com.example.lagomfurniture.model.Product;
import com.example.lagomfurniture.repository.ProductRepository;
import com.example.lagomfurniture.service.PageMakerService;
import com.example.lagomfurniture.utils.PageMakerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductPaginationService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PageMakerService pageMakerService;


//    public String productList(int pageNum, Model model){
//
////        PageRequest pageRequest = PageRequest.of(pageNum-1, 6, Sort.Direction.ASC,"productId");
////        Page<Product> productPage = productRepository.findByProductCategory("bed",pageRequest);
////
////        PageMakerUtils pageMakerUtils = pageMakerService.generateProductPageMaker(pageNum, 6, productPage);
////
////        System.out.println("productPage Size :  " + productPage.getSize());
////
////        if(productPage.getSize()==0){
////            model.addAttribute("productList", new ArrayList<Product>());
////            model.addAttribute("pageMaker",pageMakerUtils);
////            return "view/shop/product_category/product_bed";
////        }
//
////        List<Product> productList = productPage.getContent();
//
////        System.out.println("productList getContent" + productPage.getContent());
////        model.addAttribute("product", productList);
////        System.out.println("productList.size : " + productList.size());
////        System.out.println("pageMaker total : " + pageMakerUtils.getTotalcount());
////        model.addAttribute("pageMaker", pageMakerUtils);
////
//        return "";
//    }



    public Page<Product> getProductPage(int pageNum){
        return productRepository.findByProductCategory("bed", PageRequest.of(pageNum-1, 6, Sort.Direction.ASC,"productId"));
    }

    public PageMakerUtils generateProductPageMaker(int pageNum, int contentNum, Page<Product> products){
        PageMakerUtils productPageMakerUtils = pageMakerService.generateProductPageMaker(pageNum, contentNum, products);
        return productPageMakerUtils;
    }

    public List<Product> getProductList(int pageNum){
       return getProductPage(pageNum).getContent();
    }

}
