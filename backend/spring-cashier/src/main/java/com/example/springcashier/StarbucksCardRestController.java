package com.example.springcashier;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
public class StarbucksCardRestController {
    
    @Autowired
    private CustomerRepository repository ;


    @PostMapping("/cards")
    StarbucksCard newCard() {
        StarbucksCard newcard = new StarbucksCard();

        Random random = new Random();
        int num = random.nextInt(900000000) + 100000000;
        int code = random.nextInt(900) + 100;

        newcard.setCardNumber(String.valueOf(num));
        newcard.setCardCode(String.valueOf(code));
        newcard.setBalance(20.00);
        newcard.setActivated(false);
        newcard.setStatus("New Card");

        Customer customer = repository.findById(newcard.getCustomerId());

        return repository.save(newcard);

    }

    @GetMapping("/cards")
    List<StarbucksCard> all() {
        return repository.findAll();
    }

    @DeleteMapping("/cards")
    void deleteAll() {
        repository.deleteAllInBatch();
    }

    @GetMapping("/card/{num}")
    StarbucksCard getOne(@PathVariable String num, HttpServletResponse response) {
        StarbucksCard card = repository.findByCardNumber(num);
        if(card == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error. Card Not Found!");
        return card;
    }

}
