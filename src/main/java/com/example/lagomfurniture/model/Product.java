package com.example.lagomfurniture.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "product")
public class Product {
    // Product Table : product_id / product_name / product_price / product_explained1
    // / product_explained2 / product_thumnail / product_category

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private String productPrice;

    @Column(name = "product_explained1")
    private String productExplained1;

    @Column(name = "product_explained2")
    private String productExplained2;

    @Column(name = "product_thumnail")
    private String productThumnail;

    @Column(name = "product_category")
    private String productCategory;

    public Product() {
    }

    @ManyToMany(mappedBy="products")
    private List<User> users = new ArrayList<User>();

    @ManyToMany(mappedBy="products")
    private List<OrderDetail> orderdetails = new ArrayList<OrderDetail>();

}
