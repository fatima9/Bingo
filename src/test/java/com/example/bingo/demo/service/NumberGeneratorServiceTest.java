package com.example.bingo.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.bingo.repository.BingoServiceRepo;
import com.example.bingo.service.NumberGenerationService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NumberGeneratorServiceTest {

	@Autowired
	NumberGenerationService numberGenerationService;

	@BeforeEach
	public void init() {
		BingoServiceRepo.numberRange = 5;
	}

	@Test
	public void getTicketNumbersTest() {
		assertEquals(4, numberGenerationService.getTicketNumbers().size());
	}

	@Test
	public void generateRandomNumbersListTest() {
		assertEquals(4, numberGenerationService.generateRandomNumbersList(1, 5, 4).size());
	}

	@Test
	public void generateNextNumberTest() {
		List<Integer> ticketNumbers = new ArrayList<Integer>();
		ticketNumbers.add(1);
		numberGenerationService.setTicketNumbers(ticketNumbers);
		assertEquals(1, numberGenerationService.generateNextNumber());
	}
}
