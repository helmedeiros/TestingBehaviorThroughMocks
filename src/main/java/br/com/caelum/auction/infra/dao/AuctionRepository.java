package br.com.caelum.auction.infra.dao;

import br.com.caelum.auction.domain.Auction;

import java.util.List;

/**
 * .
 * User: helmedeiros
 * Date: 9/28/13
 * Time: 5:51 PM
 */
public interface AuctionRepository {
    void save(Auction auction);

    List<Auction> closeds();

    List<Auction> actuals();

    void update(Auction auction);
}
