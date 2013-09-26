package br.com.caelum.auction.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Represents a public sale in which goods or property are sold to the highest bidder.
 */
public class Auction {

	private String description;
	private Calendar date;
	private List<Bid> bids;
	private boolean closed;
	private int id;
	
	public Auction(String description) {
		this(description, Calendar.getInstance());
	}
	
	public Auction(String description, Calendar date) {
		this.description = description;
		this.date = date;
		this.bids = new ArrayList<Bid>();
	}
	
	public void take(Bid bid) {
		if(bids.isEmpty() || canTakeBidFrom(bid.getUser())) {
			bids.add(bid);
		}
	}

	private boolean canTakeBidFrom(User user) {
		return !lastBidTake().getUser().equals(user) && amountOfBidsFrom(user) <5;
	}

	private int amountOfBidsFrom(User user) {
		int total = 0;
		for(Bid l : bids) {
			if(l.getUser().equals(user)) total++;
		}
		return total;
	}

	private Bid lastBidTake() { return bids.get(bids.size()-1); }

	public String getDescription() { return description; }

	public List<Bid> getBids() { return Collections.unmodifiableList(bids); }

	public Calendar getDate() { return (Calendar) date.clone(); }

	public void close() { this.closed = true; }
	
	public boolean isClosed() { return closed; }

	public void setId(int id) { this.id = id; }
	
	public int getId() { return id; }
}
