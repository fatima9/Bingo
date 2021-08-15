package com.example.bingo.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.bingo.model.Player;

import lombok.Data;

@Data
@Repository
public class BingoServiceRepo {

	public static int numberRange = 0;

	public static int numbersPerRow = 0;

	public static Map<Integer, Player> playersMap = new HashMap<>();

	public static boolean isEarlyFiveAchieved;

	public static boolean isTopLineAchieved;

	public static boolean isFullHouseAchieved;

}
