package com.example.lagomfurniture.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @ManyToOne(fetch =FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(foreignKey = @ForeignKey(name = "user_id"))
    private User user;

    @ManyToOne(fetch =FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(foreignKey = @ForeignKey(name = "product_id"))
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    public Cart(){
    }

    public Cart(User user, Product product, int quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }


}
