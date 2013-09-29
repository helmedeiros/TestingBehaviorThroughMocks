package br.com.caelum.auction.service;

import br.com.caelum.auction.domain.Auction;
import br.com.caelum.auction.infra.dao.AuctionRepository;

import java.util.Calendar;
import java.util.List;

public class AuctionCloser {

	private int total = 0;
    private final AuctionRepository dao;
    private final MailSender postman;

    public AuctionCloser(final AuctionRepository dao, final MailSender postman) {
        this.dao = dao;
        this.postman = postman;
    }

    public void close() {
        List<Auction> allCurrentAuctions = dao.actuals();

		for (Auction auction : allCurrentAuctions) {
			if (startedLastWeek(auction)) {
				auction.close();
				total++;
				dao.update(auction);
                postman.send(auction);
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
