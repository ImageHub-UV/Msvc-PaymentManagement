package com.imagehub.payment_management.card;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagehub.payment_management.order.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CardControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CardRepository repository;

    @MockBean
    OrderRepository orderRepository;

    private final List<Card> cards = new ArrayList<>();

    @BeforeEach
    void setUp() {
        cards.add(new Card(1,
                "1234567890123456",
                1, "12/23",
                "123",
                "John Doe"));
    }

    @Test
    void shouldFindAllCards() throws Exception {
        when(repository.getAll()).thenReturn(cards);
        mvc.perform(get("/api/card"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(cards.size())));
    }

    @Test
    void shouldFindACard() throws Exception {
        when(repository.getById(1)).thenReturn(cards.stream().findFirst());
        mvc.perform(get("/api/card/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.card_id", is(1)))
                .andExpect(jsonPath("$.card_number", is("1234567890123456")))
                .andExpect(jsonPath("$.user_id", is(1)))
                .andExpect(jsonPath("$.expiration_date", is("12/23")))
                .andExpect(jsonPath("$.cvc_code", is("123")))
                .andExpect(jsonPath("$.owner_name", is("John Doe")));
    }

    @Test
    void shouldCreateACard() throws Exception {
        Card card = new Card(1,
                "1234567890123456",
                1, "12/23",
                "123",
                "John Doe");
        mvc.perform(MockMvcRequestBuilders.post("/api/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(card)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldEditACard() throws Exception {
        Card card = new Card(1,
                "1234567890123456",
                1, "12/23",
                "123",
                "John Doe");
        mvc.perform(MockMvcRequestBuilders.put("/api/card/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(card)))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldDeleteACard() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/card/1"))
                .andExpect(status().isNoContent());
    }
}