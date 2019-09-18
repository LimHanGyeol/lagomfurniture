package com.example.lagomfurniture.service.reviewservice;

import com.example.lagomfurniture.model.Review;
import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.repository.ReviewRepository;
import com.example.lagomfurniture.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class ReviewDeleteService {

    @Autowired
    private ReviewRepository reviewRepository;

    public String reviewDelete(Long reviewNo, HttpSession session) {
        // 삭제할때 alert 로 확인창 띄우는 작업 필요
        User loginUser = HttpSessionUtils.getUserSession(session);
        Review review = reviewRepository.findById(reviewNo).get();
        if (!review.isSameWriter(loginUser)) {
            return "view/users/redirect";
        }
        reviewRepository.delete(review);

        return "redirect:/review";
    }

}
