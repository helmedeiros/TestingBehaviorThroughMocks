package br.com.caelum.auction.service;

import br.com.caelum.auction.builder.AuctionBuilder;
import br.com.caelum.auction.domain.Auction;
import br.com.caelum.auction.domain.Payment;
import br.com.caelum.auction.domain.User;
import br.com.caelum.auction.infra.dao.AuctionRepository;
import br.com.caelum.auction.infra.dao.PaymentRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test of {@link PaymentManager}
 * User: helmedeiros
 * Date: 10/5/13
 * Time: 6:11 PM
 */
public class PaymentManagerTest {

    public static final String ANY_VALID_AUCTION_NAME = "PLAYSTATION 3";
    private static final User VALID_USER = new User(1, "John");
    private static final User ANOTHER_VALID_USER = new User(2, "Bill");
    private static final double LOWER_BID_AMOUNT = 1;
    public static final double HIGHER_BID_AMOUNT = 1000.0;
    private Auctioneer auctioneer;

    @Before
    public void setUp() throws Exception {
        auctioneer = new Auctioneer();
    }

    @Test public void shouldChargeValueBeEqualToTheHigherBidOfAnAuction() throws Exception {
        final Auction closedAuction1 =
                new AuctionBuilder().to(ANY_VALID_AUCTION_NAME)
                        .Bid(VALID_USER, LOWER_BID_AMOUNT)
                        .Bid(ANOTHER_VALID_USER, HIGHER_BID_AMOUNT)
                        .build();

        final PaymentRepository paymentRepositoryMock = mock(PaymentRepository.class);

        final AuctionRepository auctionRepositoryMock = mock(AuctionRepository.class);
        when(auctionRepositoryMock.closeds()).thenReturn(Arrays.asList(closedAuction1));

        PaymentManager paymentManager = new PaymentManager(auctionRepositoryMock, auctioneer, paymentRepositoryMock);
        paymentManager.manage();

        final ArgumentCaptor<Payment> captor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepositoryMock).save(captor.capture());

        final Payment payment = captor.getValue();
        assertThat(payment.getAmount(), equalTo(HIGHER_BID_AMOUNT));
    }
}
