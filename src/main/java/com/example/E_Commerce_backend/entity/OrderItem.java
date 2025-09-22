package com.example.E_Commerce_backend.entity;

import com.example.E_Commerce_backend.dto.OrderItemId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "order_item")
public class OrderItem {

    @EmbeddedId
    private OrderItemId orderItemId =new OrderItemId();

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Products product;

    @Column(name = "unit_price")
    private double unitPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_cost")
    private  double totalCost;

    @Column(name = "discount")
    private  double discount;

}
