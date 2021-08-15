package com.example.bingo.model;

import lombok.Data;

@Data
public class Ticket {

	private Integer ticketNumber;
	private boolean isMarked;

	public Ticket(int ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
}
