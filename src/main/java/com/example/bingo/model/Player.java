package com.example.bingo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.bingo.util.WinningCombo;

import lombok.Data;

@Data
public class Player {

	private Ticket[][] ticket;
	private String playerNo;
	private Map<Integer, TicketIndicesAndMarkInfo> ticketNumberIndicesAndMarkMap;
	private List<WinningCombo> playerWinningCombo;

	public Player(int ticketRow, int ticketCol, String playerNo) {
		ticket = new Ticket[ticketRow][ticketCol];
		this.playerNo = playerNo;
	}

	public void addticketNumberIndicesAndMark(Integer ticketNumber, int row, int col) {
		if (ticketNumberIndicesAndMarkMap == null) {
			ticketNumberIndicesAndMarkMap = new HashMap<>();
		}
		TicketIndicesAndMarkInfo ticketIndicesAndMarkInfo = new TicketIndicesAndMarkInfo();
		Integer[] indices = new Integer[] { row, col };
		ticketIndicesAndMarkInfo.setIndices(indices);
		ticketNumberIndicesAndMarkMap.put(ticketNumber, ticketIndicesAndMarkInfo);
	}

	public void addWinningCombo(WinningCombo winningCombo) {
		if (playerWinningCombo == null) {
			playerWinningCombo = new ArrayList<>();
		}
		playerWinningCombo.add(winningCombo);
	}
}
