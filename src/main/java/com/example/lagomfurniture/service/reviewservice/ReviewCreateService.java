package com.example.lagomfurniture.service.reviewservice;

import com.example.lagomfurniture.model.Review;
import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.repository.ReviewRepository;
import com.example.lagomfurniture.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewCreateService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Value("${file.upload.directory}")
    String uploadRootPath;

    File serverFile;

    public String getReviewCreatePage(Model model) {
        Review review = new Review();
        model.addAttribute("review", review);
        return "view/review/review_create";
    }

    public String createReview(HttpSession session, String reviewTitle, String reviewContent, Model model, Review review) {
        // 루트 디렉토리 : 경로에 폴더가 없으면 새로운 폴더를 생성하고 그 폴더로 이미지를 저장한다
        // 현재는 uploadRootPath 로 프로젝트안의 경로로 잡아 주었다.
        File uploadRootDir = new File(uploadRootPath);
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        MultipartFile[] imageFileDatas = review.getFileDatas();

        List<File> uploadFiles = new ArrayList<>();
        List<String> failedFiles = new ArrayList<>();

        for (MultipartFile fileData : imageFileDatas) {
            // 클라이언트가 보내는 파일 이름을 식별한다.
            String fileName = fileData.getOriginalFilename();
            System.out.println("Review Create - Client FileName : " + fileName);

            if (fileName != null && fileName.length() > 0) {
                try {   // 서버에 이미지를 생성한다.
                    serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + fileName);

                    BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    outputStream.write(fileData.getBytes());
                    outputStream.close();

                    uploadFiles.add(serverFile);
                    model.addAttribute("uploadedFiles", uploadFiles);
                    model.addAttribute("failedFiles", failedFiles);
                    reviewSave(session, reviewTitle, reviewContent, fileName);
                } catch (Exception e) {
                    System.out.println("Review Create - Error Create File : " + fileName);
                    failedFiles.add(fileName);
                }
            }
        }
        return "redirect:/review";
    }

    private void reviewSave(HttpSession session, String reviewTitle, String reviewContent, String fileName) {
        // review DB 저장
        User sessionedUser = HttpSessionUtils.getUserSession(session);

        String imagePath = "/static/reviewimage/";
        String reviewImagePath = imagePath + fileName;
        System.out.println("review imagePath : " + reviewImagePath);
        //for (int i = 0; i < 100; i++) {   // Test Create
        Review createReview = new Review("lamp/davian_pendant_thumnail.jpg", reviewTitle, reviewContent, sessionedUser, 1, reviewImagePath);
        reviewRepository.save(createReview);
        //}
    }
}
