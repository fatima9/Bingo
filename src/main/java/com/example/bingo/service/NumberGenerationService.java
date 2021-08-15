package com.example.bingo.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.bingo.repository.BingoServiceRepo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Data
public class NumberGenerationService {

	private List<Integer> ticketNumbers;

	public List<Integer> getTicketNumbers() {
		return ticketNumbers = generateRandomNumbersList(1, BingoServiceRepo.numberRange,
				BingoServiceRepo.numberRange - 1);
	}

	public int generateNextNumber() {
		int nextNumber = 0;
		if (ticketNumbers.isEmpty()) {
			log.info("Game is over. No numbers available");
		} else {
			nextNumber = ticketNumbers.remove(ticketNumbers.size() - 1);
		}
		return nextNumber;
	}

	public List<Integer> generateRandomNumbersList(int min, int max, int numbers) {
		Random random = new Random();
		List<Integer> randomNumbers = random.ints(min, max).distinct().limit(numbers).boxed()
				.collect(Collectors.toList());
		return randomNumbers;
	}
}
