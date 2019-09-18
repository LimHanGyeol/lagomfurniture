package com.example.lagomfurniture.service.reviewservice;


import com.example.lagomfurniture.model.Review;
import com.example.lagomfurniture.repository.ReviewRepository;
import com.example.lagomfurniture.service.PageMakerService;
import com.example.lagomfurniture.utils.PageMakerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewReadService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private PageMakerService pageMakerService;

    public String getReviewList(int pageNum, Model model) {     // 페이징

        PageMakerUtils pageMakerUtils = pageMakerService.generatePageMaker(pageNum, 10, reviewRepository);

        PageRequest pageRequest = PageRequest.of(pageNum - 1, 10, Sort.Direction.DESC, "reviewNo");
        Page<Review> reviewPage = reviewRepository.findAll(pageRequest);
        System.out.println("reviewPage size : " + reviewPage.getSize());
        if (reviewPage.getSize() == 0) {
            model.addAttribute("reviewlist", new ArrayList<Review>());
            model.addAttribute("pageMaker", pageMakerUtils);
            return "view/review/review";
        }
        List<Review> reviewList = reviewPage.getContent();
        System.out.println("reviewPage getContent : " + reviewPage.getContent());
        model.addAttribute("reviewlist", reviewList);
        System.out.println("revliew list.size : " + reviewList);
        System.out.println("pageMaker total : " + pageMakerUtils.getTotalcount());
        model.addAttribute("pageMaker", pageMakerUtils);

        return "view/review/review";
    }

    public String getReviewDetailPage(Long reviewNo, Model model) {
        Review review = reviewRepository.findById(reviewNo).get();
        review.reviewHitIncrease(); // 조회수 증가
        model.addAttribute("review", review);
        System.out.println("review Hit : " + review.getReviewHit());
        System.out.println("model : " + model);
        reviewRepository.save(review);

        return "view/review/review_read";
    }

}
