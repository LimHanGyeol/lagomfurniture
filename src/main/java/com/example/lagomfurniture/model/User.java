package com.example.lagomfurniture.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.criterion.Order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="user")
public class User {
    // User Table : id , user_email , platform , nickname , password , profile_image

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "platform")
    private String platform;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "password")
    private String password;

    @Column(name = "profile_Image")
    private String profileImage;

    @Transient /* @Transient 어노테이션을 사용한 필드나 메소드는 DB 테이블에 적용되지 않는다.*/
    private String re_password;

    // User은 Product와 다대다 관계 ------ 한 회원은 쇼핑몰의 여러 상품들을 가질 수 있다. 반대도 마찬가지
    // N : M 관계는 관계를 가진 양쪽 엔티티 모두에서 1 : M 관계가 존재할 때 나타나는 모습
    @ManyToMany
    @JoinTable(name="user_product",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<Product>();

    @ManyToMany
    @JoinTable(name="user_order_detail",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "order_detail_id"))
    private List<OrderDetail> orderdetails = new ArrayList<OrderDetail>();

    @ManyToMany(mappedBy = "users")
    private List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();


    public User() {
    }

    // 회원가입
    @Builder
    public User(String userEmail, String platform, String nickname, String password, String profileImage) {
        this.userEmail = userEmail;
        this.platform = platform;
        this.nickname = nickname;
        this.password = password;
        this.profileImage = profileImage;
    }

    // 로그인
    public User(String userEmail, String password) {
        this.userEmail = userEmail;
        this.password = password;
    }

    // getter 를 쓰지 않고 메세지를 보내 id 객체에 접근
    public boolean messageIdCheck(Long id) {
        if (id == null) {
            return false;
        }
        return id.equals(getId());
    }

    // getter 를 쓰지않고 메세지를 보내 password 객체에 접근
    public boolean messagePasswordCheck(String Input_password) {
        if (Input_password == null) {
            return false;
        }
        return Input_password.equals(password);
    }

      @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userEmail='" + userEmail + '\'' +
                ", platform='" + platform + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", profileImage='" + profileImage + '\'' +
                '}';
    }

}
