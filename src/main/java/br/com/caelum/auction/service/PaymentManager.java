package br.com.caelum.auction.service;

import br.com.caelum.auction.domain.Auction;
import br.com.caelum.auction.domain.Payment;
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

    public PaymentManager(AuctionRepository auctionRepository, Auctioneer auctioneer, PaymentRepository paymentRepository) {
        this.auctionRepository = auctionRepository;
        this.auctioneer = auctioneer;
        this.paymentRepository = paymentRepository;
    }

    public void manage(){
        final List<Auction> closedAuctions = auctionRepository.closeds();

        for (Auction closedAuction : closedAuctions) {
            auctioneer.evaluate(closedAuction);

            Payment payment = new Payment(auctioneer.getGreaterBid(), Calendar.getInstance());
            paymentRepository.save(payment);
        }

    }


}
