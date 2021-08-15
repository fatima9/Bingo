package com.example.bingo.validator;

import static com.example.bingo.util.BingoServiceConstants.NUMBERS_PER_ROW_ERROR;
import static com.example.bingo.util.BingoServiceConstants.NUMBERS_PER_ROW_GREATER_ERROR;
import static com.example.bingo.util.BingoServiceConstants.NUMBER_OF_PLAYERS_ERROR;
import static com.example.bingo.util.BingoServiceConstants.RANGE_ERROR;
import static com.example.bingo.util.BingoServiceConstants.RANGE_SMALL_ERROR;
import static com.example.bingo.util.BingoServiceConstants.TICKET_SIZE_ERROR;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.example.bingo.exception.BingoServiceValidationException;
import com.example.bingo.util.BingoServiceConstants;

@Component
public class BingoInputValidator {

	private String ticketSizePattern = "^(\\d+)\\*(\\d+)$";

	public void validateInput(int range, int numberOfPlayers, String ticketSize, int numbersPerRow) {
		if (range <= 0) {
			throw new BingoServiceValidationException(RANGE_ERROR);
		}
		if (numberOfPlayers <= 0) {
			throw new BingoServiceValidationException(NUMBER_OF_PLAYERS_ERROR);
		}
		if (numbersPerRow <= 0) {
			throw new BingoServiceValidationException(NUMBERS_PER_ROW_ERROR);
		}
		validateticketSize(ticketSize);
		String[] ticketRowAndCol = ticketSize.split("\\*");
		int row = Integer.parseInt(ticketRowAndCol[0]);
		int col = Integer.parseInt(ticketRowAndCol[1]);
		if (row <= 0 || col <= 0) {
			throw new BingoServiceValidationException(TICKET_SIZE_ERROR);
		}
		if (range < row * numbersPerRow) {
			throw new BingoServiceValidationException(RANGE_SMALL_ERROR);
		}
		if (numbersPerRow > col) {
			throw new BingoServiceValidationException(NUMBERS_PER_ROW_GREATER_ERROR);
		}
	}

	private void validateticketSize(String ticketSize) {
		if (!Pattern.matches(ticketSizePattern, ticketSize)) {
			throw new BingoServiceValidationException(BingoServiceConstants.TICKET_SIZE_PATTERN_ERROR);
		}
	}

}
