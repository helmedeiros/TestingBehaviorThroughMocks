package br.com.caelum.auction.service;

import br.com.caelum.auction.domain.Auction;
import br.com.caelum.auction.domain.Payment;
import br.com.caelum.auction.infra.clock.Clock;
import br.com.caelum.auction.infra.clock.SystemClock;
import br.com.caelum.auction.infra.dao.AuctionRepository;
import br.com.caelum.auction.infra.dao.PaymentRepository;

import java.util.Calendar;
import java.util.List;

/**
 * Responsable for manage payments for all {@link Auction}.
 * User: helmedeiros
 * Date: 10/5/13
 * Time: 5:52 PM
 */
public class PaymentManager {
    private AuctionRepository auctionRepository;
    private Auctioneer auctioneer;
    private PaymentRepository paymentRepository;
    private Clock clock;

    public PaymentManager(AuctionRepository auctionRepository, Auctioneer auctioneer, PaymentRepository paymentRepository, Clock clock) {
        this.auctionRepository = auctionRepository;
        this.auctioneer = auctioneer;
        this.paymentRepository = paymentRepository;
        this.clock = clock;
    }

    public PaymentManager(AuctionRepository auctionRepository, Auctioneer auctioneer, PaymentRepository paymentRepository) {
        this(auctionRepository, auctioneer, paymentRepository, new SystemClock());
    }

    public void manage(){
        final List<Auction> closedAuctions = auctionRepository.closeds();

        for (Auction closedAuction : closedAuctions) {
            auctioneer.evaluate(closedAuction);

            Payment payment = new Payment(auctioneer.getGreaterBid(), nextUtilDate());
            paymentRepository.save(payment);
        }

    }

    private Calendar nextUtilDate() {
        final Calendar date = clock.today();
        final int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);

        if(dayOfWeek == Calendar.SATURDAY) date.add(Calendar.DAY_OF_MONTH, 2);
        else if(dayOfWeek == Calendar.SUNDAY) date.add(Calendar.DAY_OF_MONTH, 1);

        return date;
    }


}
