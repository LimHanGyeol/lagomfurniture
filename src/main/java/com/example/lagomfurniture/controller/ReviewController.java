package com.example.lagomfurniture.controller;

import com.example.lagomfurniture.model.Review;
import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.repository.ReviewRepository;
import com.example.lagomfurniture.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("review")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    // 리뷰 페이지 이동
    @GetMapping("")
    public String reviewPage(Model model) {
        List<Review> reviewList = reviewRepository.findAll();
        System.out.println("review list : " + reviewList);
        model.addAttribute("review",reviewList);
        return "view/review/review";
    }

    // 리뷰 작성 페이지로 이동
    @GetMapping("/review_write")
    public String reviewWritePage(HttpSession session) {
        // 세션이 없으면 로그인 페이지로 가기
        //if (!HttpSessionUtils.isLoginUserSession(session)) {    // 로그인 정보가 없으면 로그인 화면으로 이동
        //    return "view/users/redirect";
        //}
        return "view/review/review_write";
    }

    // 리뷰 저장
    @PostMapping("/review_save")
    public String reviewSave(String reviewTitle,String reviewContent, HttpSession session) {
        // 세션이 없으면 로그인 페이지로 가기
        //if (!HttpSessionUtils.isLoginUserSession(session)) {    // 로그인 정보가 없으면 로그인 화면으로 이동
        //    return "view/users/redirect";
        //}

        User sessionedUser = HttpSessionUtils.getUserSession(session);
        System.out.println("세션 확인 : " + sessionedUser);
        // String reviewTitle, String reviewContent, User reviewWriter, String reviewDate, int reviewHit
        Review createReview = new Review("lamp/davian_pendant_thumnail.jpg",reviewTitle, reviewContent, sessionedUser, "2019.08.29.", 1);
        reviewRepository.save(createReview);

        return "redirect:/review";
    }

    // 리뷰 게시물 페이지로 이동
    @GetMapping("/review_read/{reviewNo}")
    public String reviewReadPage(@PathVariable Long reviewNo, Model model) {
        model.addAttribute("review",reviewRepository.findById(reviewNo).get());
        System.out.println("model : " + model);

        return "view/review/review_read";
    }

    // 리뷰 게시물 수정 페이지로 이동
    @GetMapping("/review_read/{reviewNo}/review_update")
    public String reviewUpdatePage(@PathVariable Long reviewNo, Model model, HttpSession session) {
        Review review = reviewRepository.findById(reviewNo).get();
        // result 예외 처리 해야함
        model.addAttribute("review",review);
        return "view/review/review_update";
    }

    // 리뷰 게시물 수정하기
    @PostMapping("/review_read/{reviewNo}/review_update")
    public String reviewUpdate(@PathVariable Long reviewNo, String reviewTitle, String reviewContent, Model model, HttpSession session) {
        Review review = reviewRepository.findById(reviewNo).get();
        // result 예외처리 해야함
        // update textarea 에서 엔터 눌르고 저장해도 줄바꿈 인식 안됨
        review.reviewUpdate(reviewTitle, reviewContent);
        reviewRepository.save(review);
        return String.format("redirect:/review/review_read/%d", reviewNo);

    }

    // 리뷰 게시물 삭제하기
    @PostMapping("/review_read/{reviewNo}/review_delete")
    public String reviewDelete(@PathVariable Long reviewNo, Model model, HttpSession session) {
        Review review = reviewRepository.findById(reviewNo).get();
        // result 예외처리 해야함 (리뷰 버튼 누르면 alert 눌러서 확인 시키기)
        reviewRepository.delete(review);
        return "redirect:/review";
    }
}
