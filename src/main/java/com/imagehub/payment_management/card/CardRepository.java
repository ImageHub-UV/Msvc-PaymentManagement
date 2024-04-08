package com.imagehub.payment_management.card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class CardRepository {
    private static final Logger log = LoggerFactory.getLogger(CardRepository.class);
    private final JdbcClient jdbcClient;

    public CardRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Card> getAll() {
        return jdbcClient.sql("SELECT * FROM card")
                .query(Card.class)
                .list();
    }

    public Optional<Card> getById(Integer id) {
        return jdbcClient.sql("SELECT * FROM card WHERE card_id = :id")
                .param("id", id)
                .query(Card.class)
                .optional();
    }

    public void create(Card card) {
        var updated = jdbcClient.sql("INSERT INTO card (card_number, user_id, expiration_date, cvc_code, owner_name) VALUES (?, ?, ?, ?, ?)")
                .params(List.of(
                        card.card_number(),
                        card.user_id(),
                        card.expiration_date(),
                        card.cvc_code(),
                        card.owner_name()
                ))
                .update();

        Assert.state(updated == 1, "Failed to insert card");
    }

    public void update(Card card, Integer id) {
        var updated = jdbcClient.sql("UPDATE card SET card_number = :card_number, user_id = :user_id, expiration_date = :expiration_date, cvc_code = :cvc, owner_name = :owner_name WHERE card_id = :id")
                .params(List.of(
                        card.card_number(),
                        card.user_id(),
                        card.expiration_date(),
                        card.cvc_code(),
                        card.owner_name(),
                        id
                ))
                .update();

        Assert.state(updated == 1, "Failed to update card");
    }

    public void delete(Integer id){
        var updated = jdbcClient.sql("DELETE FROM card WHERE card_id = :id")
                .param("id", id)
                .update();

        Assert.state(updated == 1, "Failed to delete card");
    }
}



