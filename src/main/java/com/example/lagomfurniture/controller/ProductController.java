package com.example.lagomfurniture.controller;

import com.example.lagomfurniture.model.Product;
import com.example.lagomfurniture.service.productservice.ProductPaginationService;
import com.example.lagomfurniture.service.productservice.ProductService;
import com.example.lagomfurniture.utils.PageMakerUtils;
import com.example.lagomfurniture.utils.ProductPageMakerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductPaginationService productPaginationService;

    @Autowired
    private ProductService productService;


    private int returnIntValue(String stringToInt) {
        return Integer.parseInt(stringToInt);
    }

    // 카테고리 침대 : Product 의 메인 카테고리로 이동
    @GetMapping("/bed")
    public String bed(@RequestParam(value = "pageNum", defaultValue = "1") String pageNum, Model model) {
//        String page = paginationService.productList(returnIntValue(pageNum),model);
        Page<Product> productPage = productPaginationService.getProductPage(returnIntValue(pageNum));
        ProductPageMakerUtils productPageMakerUtils = productPaginationService.generateProductPageMaker(returnIntValue(pageNum),6,productPage);
        List<Product> productList = productPaginationService.getProductList(returnIntValue(pageNum));


        if(productPage.getTotalElements()==0){
            model.addAttribute("productList", new ArrayList<Product>());
            model.addAttribute("pageMaker",productPageMakerUtils);
            return "view/shop/product_category/product_bed";
        }
        System.out.println("productList getContent" + productPage.getContent());
        model.addAttribute("product", productList);
        System.out.println("productList.size : " + productList.size());
        System.out.println("pageMaker total : " + productPageMakerUtils.getTotalcount());
        model.addAttribute("pageMaker", productPageMakerUtils);

        return "view/shop/product_category/product_bed";
    }

    // 카테고리 수납으로 이동
    @GetMapping("/chest")
    public String chest(Model model) {
        String CATEGORY = "chest";
        List<Product> productList = productService.getProductListByCategory(CATEGORY);
        model.addAttribute("productlist",productList);
        return "view/shop/product_category/product_chest";
    }

    // 카테고리 책상
    @GetMapping("/table")
    public String table(Model model) {
        String CATEGORY = "table";
        List<Product> productList = productService.getProductListByCategory(CATEGORY);
        model.addAttribute("productlist",productList);
        return "view/shop/product_category/product_table";
    }
    // 카테고리 의자
    @GetMapping("/{productCategory}")
    public String chair(Model model) {
        String CATEGORY = "chair";
        List<Product> productList = productService.getProductListByCategory(CATEGORY);
        model.addAttribute("productlist",productList);
        return "view/shop/product_category/product_chair";
    }
    // 카테고리 조명
    @GetMapping("/lamp")
    public String lamp(Model model) {
        String CATEGORY = "lamp";
        List<Product> productList = productService.getProductListByCategory(CATEGORY);
        model.addAttribute("productlist",productList);
        return "view/shop/product_category/product_lamp";
    }

    //    public Product(String productName, String productPrice, String productExplained1, String productThumnail, String productCategory) {
//    @GetMapping("/testsave")
//    public void testSave() {
//        System.out.println("프로덕트 테스트 세이브");
//        for (int i = 0; i < 100; i++) {
//        Product product = new Product("test2 - " + i,"12345","test explained2 - " + i,"ainaline_elder_bed_thumnail.jpg", "bed");
//        productRepository.save(product);
//        }
//    }
}
