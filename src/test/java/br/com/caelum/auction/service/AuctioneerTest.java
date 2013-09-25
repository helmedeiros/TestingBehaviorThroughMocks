package br.com.caelum.auction.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;

import java.util.List;

import br.com.caelum.auction.builder.AuctionBuilder;
import br.com.caelum.auction.domain.Bid;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.auction.domain.Auction;
import br.com.caelum.auction.domain.User;

public class AuctioneerTest {
	
	private Auctioneer auctioneer;
	private User maria;
	private User jose;
	private User joao;

	@Before public void createAuctioneer() {
		this.auctioneer = new Auctioneer();
		this.joao = new User("Joao");
		this.jose = new User("Jose");
		this.maria = new User("Maria");
	}
	
	@Test(expected=RuntimeException.class)
	public void shouldNotEvaluateAnAuctionWithoutBids() {
		Auction auction = new AuctionBuilder().to("Playstation 3 Novo").build();
		
		auctioneer.evaluate(auction);
		
	}
	
    @Test public void shouldTakeBidsInAscendingOrder() {
        // part 1: scenario
         
        Auction auction = new Auction("Playstation 3 Novo");
         
        auction.take(new Bid(joao, 250.0));
        auction.take(new Bid(jose, 300.0));
        auction.take(new Bid(maria, 400.0));
         
        // part 2: action
        auctioneer.evaluate(auction);
         
        // part 3: validation
        assertThat(auctioneer.getGreaterBid(), equalTo(400.0));
        assertThat(auctioneer.getLowerBid(), equalTo(250.0));
    }
 
    @Test public void shouldUnderstandAAuctionWithOnlyOneBid() {
    	User joao = new User("Jo�o");
        Auction auction = new Auction("Playstation 3 Novo");
         
        auction.take(new Bid(joao, 1000.0));
         
        auctioneer.evaluate(auction);
         
        assertEquals(1000.0, auctioneer.getGreaterBid(), 0.00001);
        assertEquals(1000.0, auctioneer.getLowerBid(), 0.00001);
    }
     
    @Test public void shouldFindTheThreeTopBids() {
        
        Auction auction = new AuctionBuilder().to("Playstation 3 Novo")
        		.Bid(joao, 100.0)
        		.Bid(maria, 200.0)
        		.Bid(joao, 300.0)
        		.Bid(maria, 400.0)
        		.build();
         
        auctioneer.evaluate(auction);
         
        List<Bid> topBids = auctioneer.getTopBids();
        assertEquals(3, topBids.size());
        
        assertThat(topBids, hasItems(
        		new Bid(maria, 400),
        		new Bid(joao, 300),
        		new Bid(maria, 200)
        ));
        
    }
     
}
