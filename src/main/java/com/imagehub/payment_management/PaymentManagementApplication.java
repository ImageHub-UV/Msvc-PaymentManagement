package com.imagehub.payment_management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.imagehub.payment_management.card.Card;
import com.imagehub.payment_management.card.CardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class PaymentManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentManagementApplication.class, args);
	}

	@Bean
	CommandLineRunner runner (CardRepository cardRepository){
		return args -> {
			if (cardRepository.getAll().isEmpty()){
				System.out.println("No cards found, creating test cards");
				ObjectMapper mapper = new ObjectMapper();
				mapper.registerModule(new JavaTimeModule());
				TypeReference<List<Card>> typeReference = new TypeReference<>(){};
				InputStream inputStream = TypeReference.class.getResourceAsStream("/data/cards.json");
				try {
					List<Card> cards = mapper.readValue(inputStream,typeReference);
					for (Card card : cards) {
						cardRepository.create(card);
					}
					System.out.println("Cards saved successfully");
				} catch (IOException e){
					System.out.println("Unable to save cards: " + e.getMessage());
				}
			} else {
				System.out.println("Cards already exist");
			}
		};
	}
}
