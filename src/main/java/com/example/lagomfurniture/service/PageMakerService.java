package com.example.lagomfurniture.service;

import com.example.lagomfurniture.controller.ProductController;
import com.example.lagomfurniture.model.Product;
import com.example.lagomfurniture.model.Review;
import com.example.lagomfurniture.utils.PageMakerUtils;
import com.example.lagomfurniture.utils.ProductPageMakerUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PageMakerService {

    public PageMakerUtils generatePageMaker(int pageNum, int contentNum, JpaRepository<Review, Long> repository) {
        PageMakerUtils pageMakerUtils = new PageMakerUtils();

        int totalCount = (int) repository.count();
        pageMakerUtils.setTotalcount(totalCount);   // 전체 게시글 수를 지정한다.
        pageMakerUtils.setPagenum(pageNum -1);      // 현재 페이지를 페이지 객체에 지정한다. -1 를 해야 쿼리에서 사용 할 수 있다.
        pageMakerUtils.setContentnum(contentNum);   // 한 페이지에 몇개씩 게시글을 보여줄지 지정한다.
        pageMakerUtils.setCurrentblock(pageNum);    // 현재 페이지 블록이 몇 번인지 현재 페이지 번호를 통해서 지정한다.
        pageMakerUtils.setCurrentPage(pageNum);     // 마지막 페이지를 지정한다.
        pageMakerUtils.setLastblock(pageMakerUtils.getTotalcount()); // 마지막 블록 번호를 전체 게시글 수를 통해서 지정한다.
        pageMakerUtils.prevnext(pageNum);           // 현재 페이지 번호로 화살표를 나타낼지 정한다.
        pageMakerUtils.setStartPage(pageMakerUtils.getCurrentblock()); // 시작 페이지를 페이지 블록 번호로 지정한다.
        pageMakerUtils.setEndPage(pageMakerUtils.getLastblock(), pageMakerUtils.getCurrentblock());
        // 마지막 페이지를 마지막 페이지 블록과 현재 페이지 블록 번호로 정한다.

        return pageMakerUtils;
    }

    public ProductPageMakerUtils generateProductPageMaker(int pageNum, int contentNum, Page<Product> products) {
        ProductPageMakerUtils productPageMakerUtils = new ProductPageMakerUtils();

        int totalCount = (int) products.getTotalElements();
        productPageMakerUtils.setTotalcount(totalCount);   // 전체 게시글 수를 지정한다.
        productPageMakerUtils.setPagenum(pageNum -1);      // 현재 페이지를 페이지 객체에 지정한다. -1 를 해야 쿼리에서 사용 할 수 있다.
        productPageMakerUtils.setContentnum(contentNum);   // 한 페이지에 몇개씩 게시글을 보여줄지 지정한다.
        productPageMakerUtils.setCurrentblock(pageNum);    // 현재 페이지 블록이 몇 번인지 현재 페이지 번호를 통해서 지정한다.
        productPageMakerUtils.setCurrentPage(pageNum);     // 마지막 페이지를 지정한다.
        productPageMakerUtils.setLastblock(productPageMakerUtils.getTotalcount()); // 마지막 블록 번호를 전체 게시글 수를 통해서 지정한다.
        productPageMakerUtils.prevnext(pageNum);           // 현재 페이지 번호로 화살표를 나타낼지 정한다.
        productPageMakerUtils.setStartPage(productPageMakerUtils.getCurrentblock()); // 시작 페이지를 페이지 블록 번호로 지정한다.
        productPageMakerUtils.setEndPage(productPageMakerUtils.getLastblock(), productPageMakerUtils.getCurrentblock());
        // 마지막 페이지를 마지막 페이지 블록과 현재 페이지 블록 번호로 정한다.

        return productPageMakerUtils;
    }
}
