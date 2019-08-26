package com.example.lagomfurniture.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    @Column(name = "review_thumbnail")
    private String reviewThumbnail;

    @Column(name = "review_title")
    private String reviewTitle;

    @Column(name = "review_content")
    private String reviewContent;

    @Column(name = "review_writer")
    private String reviewWriter;

    @Column(name = "review_date")
    private String reviewData;

    @Column(name = "review_hit")
    private int reviewHit;

    public Review() {
    }

    public Review(String reviewThumbnail, String reviewTitle, String reviewContent, String reviewWriter, String reviewData, int reviewHit) {
        this.reviewThumbnail = reviewThumbnail;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewWriter = reviewWriter;
        this.reviewData = reviewData;
        this.reviewHit = reviewHit;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewNo=" + reviewNo +
                ", reviewThumbnail='" + reviewThumbnail + '\'' +
                ", reviewTitle='" + reviewTitle + '\'' +
                ", reviewContent='" + reviewContent + '\'' +
                ", reviewWriter='" + reviewWriter + '\'' +
                ", reviewData='" + reviewData + '\'' +
                ", reviewHit=" + reviewHit +
                '}';
    }
}
