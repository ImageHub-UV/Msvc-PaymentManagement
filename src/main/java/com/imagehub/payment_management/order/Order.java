package com.imagehub.payment_management.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record Order(
        @NotEmpty
        Integer order_id,
        @NotEmpty
        Integer user_id,
        @NotEmpty
        Integer card_id,
        LocalDateTime order_date,
        @NotNull
        String payment_method,
        @NotNull
        Float total_price
        ) { public Order {} }
