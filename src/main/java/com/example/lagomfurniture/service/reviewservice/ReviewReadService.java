package com.example.lagomfurniture.service.reviewservice;


import com.example.lagomfurniture.model.Review;
import com.example.lagomfurniture.repository.ReviewRepository;
import com.example.lagomfurniture.service.PageMakerService;
import com.example.lagomfurniture.utils.PageMakerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewReadService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private PageMakerService pageMakerService;

    private Review getReviewCheck(Long reviewNo) {
        return reviewRepository.findById(reviewNo).get();
    }

    private Page<Review> getReviewPage(PageRequest pageRequest) {
        return reviewRepository.findAll(pageRequest);
    }

    private PageRequest pageRequest(int pageNum) {
        return PageRequest.of(pageNum - 1, 10, Sort.Direction.DESC, "reviewNo");
    }

    public PageMakerUtils pageMakerUtils(int pageNum) {
        return pageMakerService.generatePageMaker(pageNum, 10, reviewRepository);
    }

    public Page<Review> reviewPage(int pageNum) {
        Page<Review> reviewPage = getReviewPage(pageRequest(pageNum));
        return reviewPage;
    }

    public List<Review> getReviewList(int pageNum) {     // 페이징
        //List<Review> reviewList = reviewPage(pageNum).getContent();
        return reviewPage(pageNum).getContent();
    }

    public Review getReviewDetailPage(Long reviewNo) {
        Review review = getReviewCheck(reviewNo);
        review.reviewHitIncrease(); // 조회수 증가
        reviewRepository.save(review);
        return review;
    }
}