package br.com.caelum.auction.domain;

import java.util.Calendar;

public class Payment {

	private double amount;
	private Calendar date;

	public Payment(double amount, Calendar date) {
		this.amount = amount;
		this.date = date;
	}
	public double getAmount() { return amount; }
	public Calendar getDate() { return date; }
}
