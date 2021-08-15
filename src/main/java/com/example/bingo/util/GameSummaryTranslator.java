package com.example.bingo.util;

import static com.example.bingo.util.BingoServiceConstants.PLAYER;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.bingo.model.Player;
import com.example.bingo.repository.BingoServiceRepo;

@Component
public class GameSummaryTranslator {

	public void getSummary() {
		Map<Integer, Player> playersMap = BingoServiceRepo.playersMap;
		System.out.println("***** Game Over *****");
		System.out.println("======================");
		System.out.println("Summary:");
		for (Map.Entry<Integer, Player> entry : playersMap.entrySet()) {
			Player player = entry.getValue();
			if (player.getPlayerWinningCombo().isEmpty()) {
				System.out.println(PLAYER + player.getPlayerNo() + ":" + WinningCombo.NOTHING);
			} else {
				String res = player.getPlayerWinningCombo().stream().map(s -> s.name())
						.collect(Collectors.joining("and"));
				System.out.println(PLAYER + player.getPlayerNo() + ":" + res);
			}
		}
		System.out.println("======================");
	}

}
