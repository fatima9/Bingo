package com.example.bingo.demo.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.bingo.model.Player;
import com.example.bingo.model.Ticket;
import com.example.bingo.repository.BingoServiceRepo;
import com.example.bingo.service.BingoPlayService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BingoPlayServiceTest {

	@Autowired
	private BingoPlayService bingoPlayService;

	@BeforeEach
	public void init() {
		Map<Integer, Player> player = new HashMap<Integer, Player>();
		Player p1 = new Player(5, 1, "1");
		Ticket[][] ticket = p1.getTicket();
		ticket[0][0] = new Ticket(2);
		ticket[1][0] = new Ticket(1);
		ticket[2][0] = new Ticket(4);
		ticket[3][0] = new Ticket(3);
		ticket[4][0] = new Ticket(5);
		p1.setPlayerNo("0");
		p1.setTicket(ticket);
		p1.addticketNumberIndicesAndMark(2, 0, 0);
		p1.addticketNumberIndicesAndMark(1, 1, 0);
		p1.addticketNumberIndicesAndMark(4, 2, 0);
		p1.addticketNumberIndicesAndMark(3, 3, 0);
		p1.addticketNumberIndicesAndMark(5, 4, 0);
		player.put(0, p1);
		BingoServiceRepo.playersMap.putAll(player);
		BingoServiceRepo.numbersPerRow = 1;
	}

	@Test
	public void playBingoTest() {
		Boolean isFullHouse = bingoPlayService.playBingo(2);
		assertTrue(BingoServiceRepo.playersMap.get(0).getTicketNumberIndicesAndMarkMap().get(2).isMarked());
		assertTrue(BingoServiceRepo.isTopLineAchieved);
		assertFalse(isFullHouse);
	}

	@Test
	public void playBingoOnFullHouseTest() {
		BingoServiceRepo.playersMap.get(0).getTicketNumberIndicesAndMarkMap().get(5).setMarked(true);
		BingoServiceRepo.playersMap.get(0).getTicketNumberIndicesAndMarkMap().get(4).setMarked(true);
		BingoServiceRepo.playersMap.get(0).getTicketNumberIndicesAndMarkMap().get(3).setMarked(true);
		BingoServiceRepo.playersMap.get(0).getTicketNumberIndicesAndMarkMap().get(2).setMarked(true);
		Boolean isFullHouse = bingoPlayService.playBingo(1);
		assertTrue(BingoServiceRepo.playersMap.get(0).getTicketNumberIndicesAndMarkMap().get(1).isMarked());
		assertTrue(BingoServiceRepo.isFullHouseAchieved);
		assertTrue(isFullHouse);
	}

	@Test
	public void playBingoOnEarlyFiveTest() {
		BingoServiceRepo.playersMap.get(0).getTicketNumberIndicesAndMarkMap().get(5).setMarked(true);
		BingoServiceRepo.playersMap.get(0).getTicketNumberIndicesAndMarkMap().get(4).setMarked(true);
		BingoServiceRepo.playersMap.get(0).getTicketNumberIndicesAndMarkMap().get(3).setMarked(true);
		BingoServiceRepo.playersMap.get(0).getTicketNumberIndicesAndMarkMap().get(2).setMarked(true);
		Boolean isFullHouse = bingoPlayService.playBingo(1);
		assertTrue(BingoServiceRepo.playersMap.get(0).getTicketNumberIndicesAndMarkMap().get(1).isMarked());
		assertTrue(BingoServiceRepo.isEarlyFiveAchieved);
		assertTrue(isFullHouse);
	}

	@Test
	public void playBingoWhenNumberIsNotPresentTest() {
		Boolean isFullHouse = bingoPlayService.playBingo(7);
		assertFalse(BingoServiceRepo.playersMap.get(0).getTicketNumberIndicesAndMarkMap().values().stream()
				.anyMatch(x -> x.isMarked()));
		assertFalse(isFullHouse);
	}
}
