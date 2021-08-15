package com.example.bingo.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bingo.exception.BingoServiceException;
import com.example.bingo.model.Player;
import com.example.bingo.model.Ticket;
import com.example.bingo.repository.BingoServiceRepo;
import com.example.bingo.validator.BingoInputValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BingoInitializationService {

	@Autowired
	private BingoInputValidator bingoInputValidator;

	@Autowired
	private NumberGenerationService numberGenerationService;

	public void initializeBingoGame(int range, int numberOfPlayers, String ticketSize, int numbersPerRow) {

		bingoInputValidator.validateInput(range, numberOfPlayers, ticketSize, numbersPerRow);

		BingoServiceRepo.numberRange = range;

		BingoServiceRepo.numbersPerRow = numbersPerRow;

		initializePlayers(range, numberOfPlayers, ticketSize, numbersPerRow);

	}

	private void initializePlayers(int range, int numberOfPlayers, String ticketSize, int numbersPerRow) {
		try {
			String[] ticketRowAndCol = ticketSize.split("\\*");
			int row = Integer.parseInt(ticketRowAndCol[0]);
			int col = Integer.parseInt(ticketRowAndCol[1]);
			for (int i = 0; i < numberOfPlayers; i++) {
				log.info("initializing player : " + i);
				Player player = new Player(row, col, String.valueOf(i));
				initializeTicket(range, player, numbersPerRow);
				log.info("Ticket for player" + i + Arrays.deepToString(player.getTicket()));
				BingoServiceRepo.playersMap.put(i, player);
			}
		} catch (Exception ex) {
			throw new BingoServiceException("An exception has occurred " + ex);
		}

	}

	private void initializeTicket(int range, Player player, int numbersPerRow) {
		log.info("initializing ticket");
		Ticket[][] ticket = player.getTicket();
		List<Integer> ticketNumbers = numberGenerationService.generateRandomNumbersList(1, range,
				numbersPerRow * ticket.length);
		for (int i = 0; i < ticket.length; i++) {
			List<Integer> ticketColPositions = numberGenerationService.generateRandomNumbersList(0, ticket[0].length,
					numbersPerRow);
			while (!ticketColPositions.isEmpty()) {
				int col = ticketColPositions.remove(ticketColPositions.size() - 1);
				int ticketNumber = ticketNumbers.remove(ticketNumbers.size() - 1);
				ticket[i][col] = new Ticket(ticketNumber);
				player.addticketNumberIndicesAndMark(ticketNumber, i, col);
			}
		}
	}
}
