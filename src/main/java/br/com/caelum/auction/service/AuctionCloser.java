package br.com.caelum.auction.service;

import java.util.Calendar;
import java.util.List;

import br.com.caelum.auction.domain.Auction;
import br.com.caelum.auction.infra.dao.AuctionDao;

public class AuctionCloser {

	private int total = 0;
    private AuctionDao dao;

    public AuctionCloser(AuctionDao dao) {
        this.dao = dao;
    }

    public void close() {
        List<Auction> allCurrentAuctions = dao.actuals();

		for (Auction auction : allCurrentAuctions) {
			if (startedLastWeek(auction)) {
				auction.close();
				total++;
				dao.update(auction);
			}
		}
	}

	private boolean startedLastWeek(Auction auction) {
		return daysBetween(auction.getDate(), Calendar.getInstance()) >= 7;
	}

	private int daysBetween(Calendar start, Calendar end) {
		Calendar date = (Calendar) start.clone();
		int daysBetween = 0;
		while (date.before(end)) {
			date.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}

		return daysBetween;
	}

	public int getClosedTotal() {
		return total;
	}
}
