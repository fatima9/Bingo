package com.example.bingo.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.bingo.exception.BingoServiceException;
import com.example.bingo.exception.BingoServiceValidationException;
import com.example.bingo.repository.BingoServiceRepo;
import com.example.bingo.service.BingoInitializationService;
import com.example.bingo.service.NumberGenerationService;
import com.example.bingo.validator.BingoInputValidator;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BingoInitializationServiceTest {

	@MockBean
	private BingoInputValidator bingoInputValidator;

	@MockBean
	private NumberGenerationService numberGenerationService;

	@Autowired
	private BingoInitializationService bingoInitializationService;

	@Test
	public void initializeBingoGameTest() {

		List<Integer> numbers = new ArrayList<>();
		numbers.add(0);
		numbers.add(1);
		doReturn(numbers).when(numberGenerationService).generateRandomNumbersList(any(Integer.class),
				any(Integer.class), any(Integer.class));
		bingoInitializationService.initializeBingoGame(10, 2, "2*2", 1);
		assertEquals(2, BingoServiceRepo.playersMap.size());
		assertEquals(2, BingoServiceRepo.playersMap.get(0).getTicket().length);
	}

	@Test
	public void initializeBingoGameTestOnValidationException() {
		doThrow(BingoServiceValidationException.class).when(bingoInputValidator).validateInput(any(Integer.class),
				any(Integer.class), any(String.class), any(Integer.class));
		assertThrows(BingoServiceValidationException.class,
				() -> bingoInitializationService.initializeBingoGame(10, 2, "2*2", 1));
	}

	@Test
	public void initializeBingoGameTestOnException() {
		assertThrows(BingoServiceException.class,
				() -> bingoInitializationService.initializeBingoGame(10, 2, "ytrg", 1));
	}

}
