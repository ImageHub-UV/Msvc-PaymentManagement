package com.imagehub.payment_management.card;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CardRepository {
    private final JdbcClient jdbcClient;
    private final EncryptionService encryptionService;

    private Card decryptCardData(Card card) {
        try {
            return new Card(
                    card.card_id(),
                    encryptionService.decrypt(card.card_number()),
                    card.user_id(),
                    encryptionService.decrypt(card.expiration_date()),
                    encryptionService.decrypt(card.cvc_code()),
                    encryptionService.decrypt(card.owner_name())
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt card data", e);
        }
    }

    public CardRepository(JdbcClient jdbcClient, EncryptionService encryptionService){
        this.jdbcClient = jdbcClient;
        this.encryptionService = encryptionService;
    }

    public List<Card> getAll() {
        List<Card> cards = jdbcClient.sql("SELECT * FROM card")
                .query(Card.class)
                .list();

        return cards.stream()
                .map(this::decryptCardData)
                .collect(Collectors.toList());
    }

    public Optional<Card> getById(Integer id) {
        Optional<Card> card = jdbcClient.sql("SELECT * FROM card WHERE card_id = :id")
                .param("id", id)
                .query(Card.class)
                .optional();

        return card.map(this::decryptCardData);
    }

    public void create(Card card) {
        try {
            var updated = jdbcClient.sql("INSERT INTO card (card_number, user_id, expiration_date, cvc_code, owner_name) VALUES (?, ?, ?, ?, ?)")
                    .params(List.of(
                            encryptionService.encrypt(card.card_number()),
                            card.user_id(),
                            encryptionService.encrypt(card.expiration_date()),
                            encryptionService.encrypt(card.cvc_code()),
                            encryptionService.encrypt(card.owner_name())
                    ))
                    .update();

            Assert.state(updated == 1, "Failed to insert card");
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt card data", e);
        }
    }

    public void update(Card card, Integer id) {
        try {
            var updated = jdbcClient.sql("UPDATE card SET card_number = ?, user_id = ?, expiration_date = ?, cvc_code = ?, owner_name = ? WHERE card_id = ?")
                    .params(List.of(
                            encryptionService.encrypt(card.card_number()),
                            card.user_id(),
                            encryptionService.encrypt(card.expiration_date()),
                            encryptionService.encrypt(card.cvc_code()),
                            encryptionService.encrypt(card.owner_name()),
                            id
                    ))
                    .update();

            Assert.state(updated == 1, "Failed to update card");
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt card data", e);
        }
    }

    public void delete(Integer id){
        var updated = jdbcClient.sql("DELETE FROM card WHERE card_id = :id")
                .param("id", id)
                .update();

        Assert.state(updated == 1, "Failed to delete card");
    }
}



