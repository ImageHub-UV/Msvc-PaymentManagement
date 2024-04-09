package com.imagehub.payment_management.card;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record Card(
        @NotEmpty
        Integer card_id,
        @NotEmpty
        @Size(min= 16, max = 16)
        String card_number,
        @NotEmpty
        Integer user_id,
        @Future
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
