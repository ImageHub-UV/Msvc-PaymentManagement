package com.imagehub.payment_management.order;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.imagehub.payment_management.card.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class OrderJsonDataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(OrderJsonDataLoader.class);
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

    public OrderJsonDataLoader(OrderRepository orderRepository, ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (orderRepository.getAll().isEmpty()){
            logger.info("No orders found, creating test orders");
            TypeReference<List<Order>> typeReference = new TypeReference<>(){};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/data/orders.json");
            try {
                List<Order> orders = objectMapper.readValue(inputStream,typeReference);
                for (Order order : orders) {
                    orderRepository.create(order);
                }
                logger.info("Orders saved successfully");
            } catch (IOException e){
                logger.error("Unable to save orders: " + e.getMessage());
            }
        } else {
            System.out.println("Orders already exist");
        }
    }
}
