package com.example.lagomfurniture.controller;

import com.example.lagomfurniture.model.ImageUpload;
import com.example.lagomfurniture.model.Review;
import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.repository.ReviewRepository;
import com.example.lagomfurniture.service.ReviewService;
import com.example.lagomfurniture.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("review")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewService reviewService;

    @Value("${file.upload.directory}")
    String uploadRootPath;

    File serverFile;

    // 리뷰 페이지 이동
    @GetMapping("")
    public String reviewPage(Model model) {
        List<Review> reviewList = reviewRepository.findAll();
        System.out.println("review list : " + reviewList);
        model.addAttribute("reviewlist",reviewList);
        return "view/review/review";
    }

    // 리뷰 작성 페이지로 이동
    @GetMapping("/review_create")
    public String reviewWritePage(HttpSession session, Model model) {
        // 세션이 없으면 로그인 페이지로 가기
        //if (!HttpSessionUtils.isLoginUserSession(session)) {    // 로그인 정보가 없으면 로그인 화면으로 이동
        //    return "view/users/redirect";
        //}

        ImageUpload imageUpload = new ImageUpload();
        model.addAttribute("imageUpload",imageUpload);
        return "view/review/review_create";
    }

    // 리뷰 저장
    @PostMapping("/review_save")
    public String reviewSave(HttpServletRequest request,
                             HttpSession session,
                             Model model ,
                             @ModelAttribute("imageUpload") ImageUpload imageUpload) {
        // 세션이 없으면 로그인 페이지로 가기
        //if (!HttpSessionUtils.isLoginUserSession(session)) {    // 로그인 정보가 없으면 로그인 화면으로 이동
        //    return "view/users/redirect";
        //}

        System.out.println("request from review create :" + request);
        //System.out.println("review title : " + reviewTitle);
        //System.out.println("reviewContent : " + reviewContent);
        System.out.println("review file : " + request.getContentType());
        //String reviewImagePath = String.valueOf(serverFile);
        //System.out.println("Review Create - reviewImagePath : " + reviewImagePath);
        //System.out.println("Review Create - reviewTitle : " + reviewTitle);
        //System.out.println("Review Create - reviewContent : " + reviewContent);
        //Review createReview = new Review("lamp/davian_pendant_thumnail.jpg",reviewTitle, reviewContent, sessionedUser, "2019.08.29.", 1, reviewImagePath);
        //model.addAttribute("uploadedFiles", uploadedFiles);
        //model.addAttribute("failedFiles",failedFiles);
        //reviewRepository.save(createReview);

        return this.imageUpload(request, model, imageUpload);
    }

    // 리뷰 게시물 페이지로 이동
    @GetMapping("/review_read/{reviewNo}")
    public String reviewReadPage(@PathVariable Long reviewNo, Model model) {
        Review review = reviewRepository.findById(reviewNo).get();
        model.addAttribute("review",review);
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

    private String imageUpload(HttpServletRequest request , Model model,ImageUpload imageUpload) {
        // 루트 디렉토리 : 아래의 코드를 사용하면 하드 디스크에 새로운 폴더를 생성하고, 그 폴더로 이미지를 저장한다.
        System.out.println("upload Path : " + uploadRootPath);
        System.out.println("imageUpload request:" + request);
        System.out.println("imageUpload model:" + model);
        System.out.println("imageUpload imageUpload:" + imageUpload);

        File uploadRootDir = new File(uploadRootPath);
        if (!uploadRootDir.exists()) {  // 폴더가 없으면 새로 만든다.
            uploadRootDir.mkdirs();
        }
        MultipartFile[] imageFileDatas = imageUpload.getFileDatas();

        List<File> uploadedFiles = new ArrayList<>();
        List<String> failedFiles = new ArrayList<>();

        for (MultipartFile fileData : imageFileDatas) {
            // 클라이언트가 보내는 파일 이름을 식별한다.
            String filename = fileData.getOriginalFilename();
            System.out.println("Review Create - Client FileName : " + filename);

            if (filename != null && filename.length() > 0) {
                try {
                    // 서버에 이미지(파일)를 생성한다.
                    serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + filename);

                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(fileData.getBytes());
                    stream.close();

                    uploadedFiles.add(serverFile);
                    System.out.println("Review Create - Create File : " + serverFile);
                    //User sessionedUser = HttpSessionUtils.getUserSession(session);
                    //System.out.println("세션 확인 : " + sessionedUser);

                } catch (Exception e) {
                    System.out.println("Review Create - Error Create File : " + filename);
                    failedFiles.add(filename);
                }
            }
        }
        System.out.println("uploadedFiles : " + uploadedFiles);
        System.out.println("failedFiles : " + failedFiles);
        model.addAttribute("uploadedFiles", uploadedFiles);
        model.addAttribute("failedFiles", failedFiles);

        return "redirect:/review";
    }
}
