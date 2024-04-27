package com.imagehub.payment_management.card;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record Card(
        Integer card_id,
        @NotEmpty
        @Size(min= 16, max = 16)
        String card_number,
        @NotNull
        Integer user_id,
        @NotEmpty
        String expiration_date,
        @NotEmpty
        @Size(min= 3, max = 3)
        String cvc_code,
        @NotEmpty
        String owner_name
) {
        public Card {}
}
