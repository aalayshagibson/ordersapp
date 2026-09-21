package com.shadsluiter.ordersapp.controllers;

import com.shadsluiter.ordersapp.models.OrderModel;
import com.shadsluiter.ordersapp.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    // ==============================
    // CREATE ORDER
    // ==============================
    @PostMapping
    public ResponseEntity<OrderModel> createOrder(
            @RequestBody OrderModel order,
            Authentication authentication) {

        // Get the username of the logged-in user
        String username = authentication.getName();

        // Use the logged-in user's username as the customer ID
        order.setCustomerid(username);

        // Save the order
        OrderModel savedOrder = orderService.save(order);

        return ResponseEntity.ok(savedOrder);
    }

    // ==============================
    // GET ALL ORDERS
    // ==============================
    @GetMapping
    public ResponseEntity<List<OrderModel>> getAllOrders() {

        List<OrderModel> orders = orderService.findAll();

        return ResponseEntity.ok(orders);
    }

    // ==============================
    // GET ORDERS BY CUSTOMER ID
    // ==============================
    @GetMapping("/{customerid}")
    public ResponseEntity<List<OrderModel>> getOrdersByCustomerId(
            @PathVariable String customerid) {

        List<OrderModel> orders =
                orderService.findByCustomerid(customerid);

        return ResponseEntity.ok(orders);
    }

    // ==============================
    // UPDATE ORDER
    // ==============================
    @PutMapping("/{id}")
    public ResponseEntity<OrderModel> updateOrder(
            @PathVariable String id,
            @RequestBody OrderModel order) {

        OrderModel updatedOrder =
                orderService.updateOrder(id, order);

        return ResponseEntity.ok(updatedOrder);
    }

    // ==============================
    // DELETE ORDER
    // ==============================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable String id) {

        orderService.delete(id);

        return ResponseEntity.ok().build();
    }
}