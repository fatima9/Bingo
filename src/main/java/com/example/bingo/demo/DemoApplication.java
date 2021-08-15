package com.example.bingo.demo;

import static com.example.bingo.util.BingoServiceConstants.CHARACTER_N;
import static com.example.bingo.util.BingoServiceConstants.ENTER_NUMBERS_PER_ROW;
import static com.example.bingo.util.BingoServiceConstants.ENTER_NUMBER_OF_PLAYERS;
import static com.example.bingo.util.BingoServiceConstants.ENTER_NUMBER_RANGE;
import static com.example.bingo.util.BingoServiceConstants.ENTER_TICKET_SIZE;
import static com.example.bingo.util.BingoServiceConstants.LETS_PLAY_HOUSIE;
import static com.example.bingo.util.BingoServiceConstants.NEXT_NUMBER;
import static com.example.bingo.util.BingoServiceConstants.PRESS_N;
import static com.example.bingo.util.BingoServiceConstants.PRESS_Q_TO_QUIT;
import static com.example.bingo.util.BingoServiceConstants.TICKET_CREATED;

import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.bingo.service.BingoInitializationService;
import com.example.bingo.service.BingoPlayService;
import com.example.bingo.service.NumberGenerationService;
import com.example.bingo.util.GameSummaryTranslator;

@SpringBootApplication(scanBasePackages = { "com.example.bingo.*" })
public class DemoApplication {

	@Autowired
	private BingoInitializationService bingoInitializationService;

	@Autowired
	private NumberGenerationService numberGenerationService;

	@Autowired
	private BingoPlayService bingoPlayService;

	@Autowired
	private GameSummaryTranslator gameSummaryTranslator;

	private static BingoPlayService playService;

	private static BingoInitializationService initializationService;

	private static NumberGenerationService numGenerationService;

	private static Scanner scanner;

	private static GameSummaryTranslator gameSummary;

	@PostConstruct
	public void init() {
		initializationService = this.bingoInitializationService;
		numGenerationService = this.numberGenerationService;
		playService = this.bingoPlayService;
		gameSummary = this.gameSummaryTranslator;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		scanner = new Scanner(System.in);
		initialize();
	}

	private static void initialize() {
		System.out.println(LETS_PLAY_HOUSIE);
		System.out.println(PRESS_Q_TO_QUIT);
		System.out.println(ENTER_NUMBER_RANGE);
		int range = scanner.nextInt();
		System.out.println(ENTER_NUMBER_OF_PLAYERS);
		int numberOfPlayers = scanner.nextInt();
		System.out.println(ENTER_TICKET_SIZE);
		String ticketSize = scanner.next();
		System.out.println(ENTER_NUMBERS_PER_ROW);
		int numbersPerRow = scanner.nextInt();
		initializationService.initializeBingoGame(range, numberOfPlayers, ticketSize, numbersPerRow);
		System.out.println(TICKET_CREATED);
		numGenerationService.getTicketNumbers();
		numberGeneration();
		gameSummary.getSummary();
	}

	private static void numberGeneration() {
		String enteredCharacter;
		do {
			System.out.println(PRESS_N);
			enteredCharacter = scanner.next();
			int number = numGenerationService.generateNextNumber();
			System.out.println(NEXT_NUMBER + number);
			boolean isFullHouse = playService.playBingo(number);
			if (isFullHouse) {
				break;
			} else {
				numberGeneration();
			}
		} while (enteredCharacter.compareTo(CHARACTER_N) != 0);
	}

}
