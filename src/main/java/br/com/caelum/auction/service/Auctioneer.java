package br.com.caelum.auction.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.caelum.auction.domain.Bid;
import br.com.caelum.auction.domain.Auction;

public class Auctioneer {

	private double greaterBid = Double.NEGATIVE_INFINITY;
	private double lowerBid = Double.POSITIVE_INFINITY;
	private List<Bid> topBids;

	public void evaluate(Auction auction) {
		
		if(auction.getBids().size() == 0) {
			throw new RuntimeException("It isn't possible to evaluate an auction without bids!");
		}
		
		for(Bid bid : auction.getBids()) {
			if(bid.getAmount() > greaterBid) greaterBid = bid.getAmount();
			if (bid.getAmount() < lowerBid) lowerBid = bid.getAmount();
		}
		
		topThreeBids(auction);
	}

	private void topThreeBids(Auction auction) {
		topBids = new ArrayList<Bid>(auction.getBids());
		Collections.sort(topBids, new Comparator<Bid>() {

			public int compare(Bid o1, Bid o2) {
				if(o1.getAmount() < o2.getAmount()) return 1;
				if(o1.getAmount() > o2.getAmount()) return -1;
				return 0;
			}
		});
		topBids = topBids.subList(0, topBids.size() > 3 ? 3 : topBids.size());
	}

	public List<Bid> getTopBids() {
		return topBids;
	}
	
	public double getGreaterBid() {
		return greaterBid;
	}
	
	public double getLowerBid() {
		return lowerBid;
	}
}
