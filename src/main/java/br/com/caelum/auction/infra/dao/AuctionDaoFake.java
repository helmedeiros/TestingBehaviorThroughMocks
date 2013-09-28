package br.com.caelum.auction.infra.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.auction.domain.Auction;

public class AuctionDaoFake implements AuctionRepository {

	private static List<Auction> auctions = new ArrayList<Auction>();
	
	public void save(Auction auction) {
		auctions.add(auction);
	}

	public List<Auction> closeds() {
		
		List<Auction> filtered = new ArrayList<Auction>();
		for(Auction auction : auctions) {
			if(auction.isClosed()) filtered.add(auction);
		}

		return filtered;
	}

    public List<Auction> actuals() {
		
		List<Auction> filtered = new ArrayList<Auction>();
		for(Auction auction : auctions) {
			if(!auction.isClosed()) filtered.add(auction);
		}

		return filtered;
	}
	
	public void update(Auction auction) { /* do nothing! */ }
}
