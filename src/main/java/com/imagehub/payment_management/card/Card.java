package com.imagehub.payment_management.card;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record Card(
        @NotEmpty
        @Size(min= 16, max = 16)
        String card_number,
        @NotEmpty
        Integer user_id,
        @Future
        @NotEmpty
        LocalDateTime expiration_date,
        @NotEmpty
        @Size(min= 3, max = 3)
        String cvc
) {
    public Card {}
}
