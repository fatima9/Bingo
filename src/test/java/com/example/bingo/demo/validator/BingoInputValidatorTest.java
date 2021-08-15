package com.example.bingo.demo.validator;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.bingo.exception.BingoServiceValidationException;
import com.example.bingo.validator.BingoInputValidator;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BingoInputValidatorTest {

	@Autowired
	private BingoInputValidator bingoInputValidator;

	@Test
	public void validateInputWhenRangeIsZeroTest() {
		assertThrows(BingoServiceValidationException.class, () -> bingoInputValidator.validateInput(0, 1, "3*2", 1));
	}

	@Test
	public void validateInputWhenNumberOfPlayersIsZeroTest() {
		assertThrows(BingoServiceValidationException.class, () -> bingoInputValidator.validateInput(5, 0, "3*2", 1));
	}

	@Test
	public void validateInputWhenTicketSizeIsInvalidTest() {
		assertThrows(BingoServiceValidationException.class, () -> bingoInputValidator.validateInput(5, 1, "as", 1));
	}

	@Test
	public void validateInputWhenTicketRowIsZeroTest() {
		assertThrows(BingoServiceValidationException.class, () -> bingoInputValidator.validateInput(5, 1, "0*2", 1));
	}

	@Test
	public void validateInputWhenTicketColIsZeroTest() {
		assertThrows(BingoServiceValidationException.class, () -> bingoInputValidator.validateInput(5, 1, "1*0", 1));
	}

	@Test
	public void validateInputWhenRangeIsLessThanTotalNumbersTest() {
		assertThrows(BingoServiceValidationException.class, () -> bingoInputValidator.validateInput(2, 1, "3*2", 1));
	}

	@Test
	public void validateInputWhenNumbersPerRowGreaterThanColTest() {
		assertThrows(BingoServiceValidationException.class, () -> bingoInputValidator.validateInput(5, 1, "3*2", 3));
	}
}
