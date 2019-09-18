package com.example.lagomfurniture.controller;

import com.example.lagomfurniture.model.Review;
import com.example.lagomfurniture.service.reviewservice.ReviewCreateService;
import com.example.lagomfurniture.service.reviewservice.ReviewDeleteService;
import com.example.lagomfurniture.service.reviewservice.ReviewReadService;
import com.example.lagomfurniture.service.reviewservice.ReviewUpdateService;
import com.example.lagomfurniture.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("review")
public class ReviewController {

    @Autowired
    private ReviewCreateService reviewCreateService;
    @Autowired
    private ReviewReadService reviewReadService;
    @Autowired
    private ReviewUpdateService reviewUpdateService;
    @Autowired
    private ReviewDeleteService reviewDeleteService;

    private int returnIntValue(String stringToInt) {
        return Integer.parseInt(stringToInt);
    }

    // Review Page : Get
    @GetMapping("")
    public String reviewPaging(@RequestParam(value = "pageNum", defaultValue = "1") String pageNum, Model model) {
        String page = reviewReadService.getReviewList(returnIntValue(pageNum), model);     // 페이징
        return page;
    }

    // Review Create Page : Get
    @GetMapping("/review_create")
    public String reviewWritePage(HttpSession session, Model model) {
        if (!HttpSessionUtils.isLoginUserSession(session)) {    // 사용자 세션 정보가 없으면 로그인 화면으로 이동
            return "view/users/redirect";
        }
        return reviewCreateService.getReviewCreatePage(model);
    }

    // Review Create : POST
    @PostMapping("/review_create")
    public String reviewSave(String reviewTitle, String reviewContent, HttpSession session, Model model, @ModelAttribute("review") Review review) {
        if (!HttpSessionUtils.isLoginUserSession(session)) {    // 사용자 세션 정보가 없으면 로그인 화면으로 이동
            return "view/users/redirect";
        }
        return reviewCreateService.createReview(session, reviewTitle, reviewContent, model, review);
    }


    // Review Read Detail : Get
    @GetMapping("/review_read/{reviewNo}")
    public String reviewReadPage(@PathVariable Long reviewNo, Model model) {
        return reviewReadService.getReviewDetailPage(reviewNo, model);
    }

    // Review Update Page : Get
    @GetMapping("/review_read/{reviewNo}/review_update")
    public String reviewUpdatePage(@PathVariable Long reviewNo, Model model, HttpSession session) {
        System.out.println("컨트롤러 리뷰 업데이트 겟 : " + reviewNo);
        if (!HttpSessionUtils.isLoginUserSession(session)) {    // 사용자 세션 정보가 없으면 로그인 화면으로 이동
            return "view/users/redirect";
        }
        return reviewUpdateService.getReviewUpdatePage(reviewNo, model, session);
    }

    // Review Update : Post
    @PostMapping("/review_read/{reviewNo}/review_update")
    public String reviewUpdate(@PathVariable Long reviewNo, String reviewTitle, String reviewContent, Model model, HttpSession session) {
        System.out.println("컨트롤러 리뷰 업데이트 포스트 : " + reviewNo);
        return reviewUpdateService.reviewUpdate(reviewNo,reviewTitle, reviewContent, session);

    }

    // Review Delete : Post
    @PostMapping("/review_read/{reviewNo}/review_delete")
    public String reviewDelete(@PathVariable Long reviewNo, HttpSession session) {
        return reviewDeleteService.reviewDelete(reviewNo, session);
    }
}