package com.imagehub.payment_management.order;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {
    private final JdbcClient jdbcClient;

    public OrderRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    List<Order> getAll(){
        return jdbcClient.sql("SELECT * FROM orders")
                .query(Order.class)
                .list();
    }

    Optional<Order> getById(Integer id){
        return jdbcClient.sql("SELECT * FROM orders WHERE order_id = :id")
                .param("id", id)
                .query(Order.class)
                .optional();
    }

    void create(Order order){
        int updated = jdbcClient.sql("INSERT INTO orders ( user_id, card_id, order_date, payment_method, total_price) VALUES (?, ?, ?, ?, ?)")
                .params(List.of(order.user_id(), order.card_id(), order.order_date(), order.payment_method(), order.total_price()))
                .update();

        Assert.state(updated == 1, "Failed to insert order");
    }

    void update(Order order, Integer id){
        int updated = jdbcClient.sql("UPDATE orders SET user_id = ?, card_id = ?, order_date = ?, payment_method = ?, total_price = ? WHERE order_id = ?")
                .params(List.of(order.user_id(),
                        order.card_id(),
                        order.order_date(),
                        order.payment_method(),
                        order.total_price(),
                        id))
                .update();
        Assert.state(updated == 1, "Failed to update order");
    }

    void delete(Integer id){
        int updated = jdbcClient.sql("DELETE FROM orders WHERE order_id = :id")
                .param("id", id)
                .update();
        Assert.state(updated == 1, "Failed to delete order");
    }

    public int count() {
        return jdbcClient.sql("select * from run").query().listOfRows().size();
    }


}
