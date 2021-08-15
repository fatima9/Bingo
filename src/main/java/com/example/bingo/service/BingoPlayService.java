package com.example.bingo.service;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.example.bingo.exception.BingoServiceException;
import com.example.bingo.model.Player;
import com.example.bingo.model.Ticket;
import com.example.bingo.model.TicketIndicesAndMarkInfo;
import com.example.bingo.repository.BingoServiceRepo;
import com.example.bingo.util.WinningCombo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BingoPlayService {

	public boolean playBingo(int number) {
		try {
			for (Map.Entry<Integer, Player> entry : BingoServiceRepo.playersMap.entrySet()) {
				Player player = entry.getValue();
				checkAndMarkTicketNumber(player, number);
				checkForEarlyFive(player);
				checkForTopLine(player);
				checkForFullHouse(player);
			}
		} catch (Exception ex) {
			throw new BingoServiceException("An exception has occurred " + ex);
		}
		return BingoServiceRepo.isFullHouseAchieved;
	}

	private void checkForFullHouse(Player player) {
		if (!BingoServiceRepo.isFullHouseAchieved) {
			log.info("Checking for full house for player " + player.getPlayerNo());
			int totalNumbers = player.getTicket().length * BingoServiceRepo.numbersPerRow;
			boolean isFullHouse = isFullHouse(player.getTicketNumberIndicesAndMarkMap(), totalNumbers);
			if (isFullHouse) {
				log.info("Adding full house for player " + player.getPlayerNo());
				updatePlayersWinningCombo(player, WinningCombo.FULLHOUSE);
				BingoServiceRepo.isFullHouseAchieved = true;
				System.out.println("We have a winner: Player#" + player.getPlayerNo() + " has won "
						+ WinningCombo.FULLHOUSE + " winning combination");
			}
		}

	}

	private boolean isFullHouse(Map<Integer, TicketIndicesAndMarkInfo> map, int totalNumbers) {
		int fullHouseCount = 0;
		boolean isFullHouse = false;
		for (Entry<Integer, TicketIndicesAndMarkInfo> entry : map.entrySet()) {
			TicketIndicesAndMarkInfo ticketIndicesAndMarkInfo = entry.getValue();
			if (ticketIndicesAndMarkInfo.isMarked()) {
				fullHouseCount++;
				if (fullHouseCount == totalNumbers) {
					isFullHouse = true;
					break;
				}
			}
		}
		return isFullHouse;
	}

	private void checkForTopLine(Player player) {
		if (!BingoServiceRepo.isTopLineAchieved) {
			log.info("Checking for top line for player " + player.getPlayerNo());
			boolean isTopLine = isTopLine(player.getTicket());
			if (isTopLine) {
				log.info("Adding top line for player " + player.getPlayerNo());
				updatePlayersWinningCombo(player, WinningCombo.TOPLINE);
				BingoServiceRepo.isTopLineAchieved = true;
				System.out.println("We have a winner: Player#" + player.getPlayerNo() + " has won "
						+ WinningCombo.TOPLINE + " winning combination");
			}

		}
	}

	private boolean isTopLine(Ticket[][] playerticket) {
		int topLineCount = 0;
		boolean isTopLine = false;
		for (int j = 0; j < playerticket[0].length; j++) {
			Ticket ticket = playerticket[0][j];
			if (ticket != null && ticket.isMarked()) {
				topLineCount++;
				if (topLineCount == BingoServiceRepo.numbersPerRow) {
					isTopLine = true;
					break;
				}
			}
		}
		return isTopLine;
	}

	private void checkForEarlyFive(Player player) {
		if (!BingoServiceRepo.isEarlyFiveAchieved) {
			log.info("Checking for early five for player " + player.getPlayerNo());
			boolean isEarlyFive = isEarlyFive(player.getTicketNumberIndicesAndMarkMap());
			if (isEarlyFive) {
				log.info("Adding early five for player " + player.getPlayerNo());
				updatePlayersWinningCombo(player, WinningCombo.EARLYFIVE);
				BingoServiceRepo.isEarlyFiveAchieved = true;
				System.out.println("We have a winner: Player#" + player.getPlayerNo() + " has won "
						+ WinningCombo.EARLYFIVE + " winning combination");
			}
		}
	}

	private boolean isEarlyFive(Map<Integer, TicketIndicesAndMarkInfo> map) {
		int earlyFiveCount = 0;
		boolean isEarlyFive = false;
		for (Entry<Integer, TicketIndicesAndMarkInfo> entry : map.entrySet()) {
			TicketIndicesAndMarkInfo ticketIndicesAndMarkInfo = entry.getValue();
			if (ticketIndicesAndMarkInfo.isMarked()) {
				earlyFiveCount++;
				if (earlyFiveCount == 5) {
					isEarlyFive = true;
					break;
				}
			}
		}
		return isEarlyFive;
	}

	private void checkAndMarkTicketNumber(Player player, int number) {
		if (player.getTicketNumberIndicesAndMarkMap().containsKey(number)) {
			Ticket[][] tickets = player.getTicket();
			TicketIndicesAndMarkInfo ticketIndicesAndMarkInfo = player.getTicketNumberIndicesAndMarkMap().get(number);
			Integer[] indices = ticketIndicesAndMarkInfo.getIndices();
			int row = indices[0];
			int col = indices[1];
			Ticket ticket = tickets[row][col];
			if (ticket != null && ticket.getTicketNumber() != null && ticket.getTicketNumber() == number
					&& !ticket.isMarked()) {
				log.info("Marking ticket number " + number + " for player " + player.getPlayerNo());
				ticket.setMarked(true);
				ticketIndicesAndMarkInfo.setMarked(true);
			}

		}
	}

	private void updatePlayersWinningCombo(Player player, WinningCombo winningCombo) {
		player.addWinningCombo(winningCombo);
	}
}
