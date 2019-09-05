package com.example.lagomfurniture.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Getter
@Setter
@Table(name = "review")
public class Review {
    // review_no / review_thumbnail / review_title / review_writer / review_date / review_hit

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_no")
    private Long reviewNo;

    //@ManyToOne(targetEntity = Product.class)
    //@JoinColumn(name = "review_thumbnail")
    @Column(name = "review_thumbnail")
    private String reviewThumbnail;

    @Column(name = "review_title")
    private String reviewTitle;

    @Column(name = "review_image1")
    private String reviewImage;

    @Transient      // DB 에 칼럼 만들지 않음.
    private MultipartFile[] fileDatas;  // 다중 파일 업로드를 위해 배열로 설정

    @Column(name = "review_content")
    private String reviewContent;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "review_writer")
    private User reviewWriter;

    @Column(name = "review_date")
    private String reviewDate;

    @Column(name = "review_hit")
    private int reviewHit;

    public Review() {
    }

    // review create
    public Review(String reviewThumbnail, String reviewTitle, String reviewContent, User reviewWriter, String reviewDate, int reviewHit, String reviewImage) {
        this.reviewThumbnail = reviewThumbnail;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewWriter = reviewWriter;
        this.reviewDate = reviewDate;
        this.reviewHit = reviewHit;
        this.reviewImage = reviewImage;
    }

    // review update
    public void reviewUpdate(String reviewTitle, String reviewContent) {
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewNo=" + reviewNo +
                ", reviewThumbnail='" + reviewThumbnail + '\'' +
                ", reviewTitle='" + reviewTitle + '\'' +
                ", reviewImage='" + reviewImage + '\'' +
                ", fileDatas=" + Arrays.toString(fileDatas) +
                ", reviewContent='" + reviewContent + '\'' +
                ", reviewWriter=" + reviewWriter +
                ", reviewDate='" + reviewDate + '\'' +
                ", reviewHit=" + reviewHit +
                '}';
    }
}
