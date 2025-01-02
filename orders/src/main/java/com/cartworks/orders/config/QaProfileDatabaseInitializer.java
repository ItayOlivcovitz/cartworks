package com.cartworks.orders.config;

import com.cartworks.orders.entity.Order;
import com.cartworks.orders.entity.OrderItem;
import com.cartworks.orders.enums.OrderStatus;
import com.cartworks.orders.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
@Profile("qa") // Activate this bean only when the 'qa' profile is active
public class QaProfileDatabaseInitializer implements CommandLineRunner {

    private final OrderRepository orderRepository;

    public QaProfileDatabaseInitializer(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void run(String... args) {
        // Create Order 1
        Order order1 = Order.builder()
                .userEmail("itay.olivcovitz@gmail.com")
                .orderDate(LocalDate.now())
                .totalAmount(150.0)
                .status(OrderStatus.PENDING)
                .build();

        OrderItem item1 = OrderItem.builder()
                .order(order1)
                .productId(1L)
                .quantity(1)
                .price(50.0)
                .build();

        OrderItem item2 = OrderItem.builder()
                .order(order1)
                .productId(2L)
                .quantity(2)
                .price(50.0)
                .build();

        order1.setItems(Set.of(item1, item2));

        // Create Order 2
        Order order2 = Order.builder()
                .userEmail("itay.olivcovitz@gmail.com")
                .orderDate(LocalDate.now().minusDays(2))
                .totalAmount(300.0)
                .status(OrderStatus.SHIPPED)
                .build();

        OrderItem item3 = OrderItem.builder()
                .order(order2)
                .productId(1L)
                .quantity(3)
                .price(100.0)
                .build();

        order2.setItems(Set.of(item3));

        // Save Orders
        orderRepository.save(order1);
        orderRepository.save(order2);

        System.out.println("Sample orders have been seeded for the 'qa' profile.");
    }
}
