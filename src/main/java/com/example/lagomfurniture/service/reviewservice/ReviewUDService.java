package com.example.lagomfurniture.service.reviewservice;


import com.example.lagomfurniture.model.Review;
import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.repository.ReviewRepository;
import com.example.lagomfurniture.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

@Service
public class ReviewUDService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review getReviewCheck(Long reviewNo) {
        return reviewRepository.findById(reviewNo).get();
    }

    public void reviewDelete(Review review) {
        reviewRepository.delete(review);
    }

    public Review reviewUpdate(Long reviewNo, String reviewTitle, String reviewContent) {
        // update textarea 에서 엔터 눌르고 저장해도 줄바꿈 인식 안됨
        Review review = getReviewCheck(reviewNo);
        review.reviewUpdate(reviewTitle, reviewContent);
        reviewRepository.save(review);
        return review;
    }
}
