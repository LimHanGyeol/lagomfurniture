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
public class ReviewUpdateService {

    @Autowired
    private ReviewRepository reviewRepository;

    public String getReviewUpdatePage(Long reviewNo, Model model, HttpSession session) {
        User loginUser = HttpSessionUtils.getUserSession(session);
        Review review = reviewRepository.findById(reviewNo).get();
        if (!review.isSameWriter(loginUser)) {
            return "view/users/redirect";
        }
        model.addAttribute("review", review);

        return "view/review/review_update";
    }

    public String reviewUpdate(Long reviewNo, String reviewTitle, String reviewContent, HttpSession session) {
        // update textarea 에서 엔터 눌르고 저장해도 줄바꿈 인식 안됨
        User loginUser = HttpSessionUtils.getUserSession(session);
        Review review = reviewRepository.findById(reviewNo).get();
        if (!review.isSameWriter(loginUser)) {
            return "view/users/redirect";
        }
        review.reviewUpdate(reviewTitle, reviewContent);
        reviewRepository.save(review);
        return String.format("redirect:/review/review_read/%d", reviewNo);
    }
}
