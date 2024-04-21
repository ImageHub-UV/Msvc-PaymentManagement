package com.imagehub.payment_management.card;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/card")
public class CardController {
    private final CardRepository cardRepository;

    public CardController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @GetMapping("")
    List<Card> getAll(){
        return cardRepository.getAll();
    }

    @GetMapping("/{card_id}")
    Card getById(@PathVariable Integer card_id) {
        Optional<Card> card = cardRepository.getById(card_id);
        if(card.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
        } else {
            return card.get();
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Card card){
        cardRepository.create(card);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{card_id}")
    void update(@Valid @RequestBody Card card, @PathVariable Integer card_id){
        cardRepository.update(card, card_id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{card_id}")
    void delete (@PathVariable Integer card_id){
        cardRepository.delete(card_id);
    }


}
