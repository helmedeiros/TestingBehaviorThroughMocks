package br.com.caelum.auction.builder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.caelum.auction.domain.Bid;
import br.com.caelum.auction.domain.Auction;
import br.com.caelum.auction.domain.User;

public class AuctionBuilder {

	private String description;
	private Calendar date;
	private List<Bid> bids;
	private boolean closed;

	public AuctionBuilder() {
		this.date = Calendar.getInstance();
		bids = new ArrayList<Bid>();
	}
	
	public AuctionBuilder to(String description) {
		this.description = description;
		return this;
	}
	
	public AuctionBuilder onDate(Calendar date) {
		this.date = date;
		return this;
	}

	public AuctionBuilder Bid(User user, double amount) {
		bids.add(new Bid(user, amount));
		return this;
	}

	public AuctionBuilder closed() {
		this.closed = true;
		return this;
	}

	public Auction build() {
		Auction auction = new Auction(description, date);
		for(Bid bidDado : bids) auction.take(bidDado);
		if(closed) auction.close();
				
		return auction;
	}

}
