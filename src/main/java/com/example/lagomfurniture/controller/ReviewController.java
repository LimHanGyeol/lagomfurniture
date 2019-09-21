package com.example.lagomfurniture.controller;

import com.example.lagomfurniture.model.FileUpload;
import com.example.lagomfurniture.model.Review;
import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.service.reviewservice.ReviewCreateService;
import com.example.lagomfurniture.service.reviewservice.ReviewReadService;
import com.example.lagomfurniture.service.reviewservice.ReviewUDService;
import com.example.lagomfurniture.utils.HttpSessionUtils;
import com.example.lagomfurniture.utils.PageMakerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("review")
public class ReviewController {

    @Autowired
    private ReviewCreateService reviewCreateService;
    @Autowired
    private ReviewReadService reviewReadService;
    @Autowired
    private ReviewUDService reviewUDService;

    private int returnIntValue(String stringToInt) {
        return Integer.parseInt(stringToInt);
    }

    // Review Page : Get
    @GetMapping("")
    public String reviewPaging(@RequestParam(value = "pageNum", defaultValue = "1") String pageNum, Model model) {
        // 페이징
        PageMakerUtils pageMakerUtils = reviewReadService.pageMakerUtils(returnIntValue(pageNum));
        Page<Review> reviewPage = reviewReadService.reviewPage(returnIntValue(pageNum));
        List<Review> reviewList = reviewReadService.getReviewList(returnIntValue(pageNum));

        if (reviewPage.getSize() == 0) {
            model.addAttribute("reviewlist", new ArrayList<Review>());
            model.addAttribute("pageMaker", pageMakerUtils);
            return "view/review/review";
        }

        model.addAttribute("reviewlist", reviewList);
        model.addAttribute("pageMaker", pageMakerUtils);
        return "view/review/review";
    }

    // Review Create Page : Get
    @GetMapping("/review_create")
    public String reviewWritePage(HttpSession session, Model model) {
        if (!HttpSessionUtils.isLoginUserSession(session)) {    // 사용자 세션 정보가 없으면 로그인 화면으로 이동
            return "view/users/redirect";
        }
        Review review = new Review();

        model.addAttribute("review", review);
        return "view/review/review_create";
    }

    // Review Create : Post
    @PostMapping("/review_create")
    public String reviewSave(String reviewTitle, String reviewContent, HttpSession session, Model model, @ModelAttribute("review") Review review) {
        if (!HttpSessionUtils.isLoginUserSession(session)) {    // 사용자 세션 정보가 없으면 로그인 화면으로 이동
            return "view/users/redirect";
        }
        User sessionedUser = HttpSessionUtils.getUserSession(session);
        FileUpload reviewCreate = reviewCreateService.createReview(sessionedUser, reviewTitle, reviewContent, review);

        model.addAttribute("uploadedFiles", reviewCreate.getUploadFiles());
        model.addAttribute("failedFiles", reviewCreate.getFailedFiles());
        return "redirect:/review";
    }

    // Review Read Detail : Get
    @GetMapping("/review_read/{reviewNo}")
    public String reviewReadPage(@PathVariable Long reviewNo, Model model) {
        Review review = reviewReadService.getReviewDetailPage(reviewNo);
        model.addAttribute("review", review);
        return "view/review/review_read";
    }

    // Review Update Page : Get
    @GetMapping("/review_read/{reviewNo}/review_update")
    public String reviewUpdatePage(@PathVariable Long reviewNo, Model model, HttpSession session) {
        User sessionedUser = HttpSessionUtils.getUserSession(session);
        if (!HttpSessionUtils.isLoginUserSession(session)) {    // 사용자 세션 정보가 없으면 로그인 화면으로 이동
            return "view/users/redirect";
        }

        Review review = reviewUDService.getReviewCheck(reviewNo);
        if (!review.isSameWriter(sessionedUser)) {
            return "view/users/redirect";
        }

        model.addAttribute("review", review);
        return "view/review/review_update";
    }

    // Review Update : Post
    @PostMapping("/review_read/{reviewNo}/review_update")
    public String reviewUpdate(@PathVariable Long reviewNo, String reviewTitle, String reviewContent, Model model, HttpSession session) {
        User sessionedUser = HttpSessionUtils.getUserSession(session);
        if (!HttpSessionUtils.isLoginUserSession(session)) {    // 사용자 세션 정보가 없으면 로그인 화면으로 이동
            return "view/users/redirect";
        }

        Review review = reviewUDService.getReviewCheck(reviewNo);
        if (!review.isSameWriter(sessionedUser)) {
            return "view/users/redirect";
        }

        reviewUDService.reviewUpdate(reviewNo, reviewTitle, reviewContent);
        return String.format("redirect:/review/review_read/%d", reviewNo);
    }

    // Review Delete : Post
    @PostMapping("/review_read/{reviewNo}/review_delete")
    public String reviewDelete(@PathVariable Long reviewNo, HttpSession session) {
        // 삭제할때 alert 로 확인창 띄우는 작업 필요
        User sessionedUser = HttpSessionUtils.getUserSession(session);
        if (!HttpSessionUtils.isLoginUserSession(session)) {    // 사용자 세션 정보가 없으면 로그인 화면으로 이동
            return "view/users/redirect";
        }

        Review review = reviewUDService.getReviewCheck(reviewNo);
        if (!review.isSameWriter(sessionedUser)) {  // 작성자가 다르면 삭제 못함
            return "view/users/redirect";
        }

        reviewUDService.reviewDelete(review);
        return "redirect:/review";
    }
}