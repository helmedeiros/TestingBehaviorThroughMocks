package br.com.caelum.auction.service;

import br.com.caelum.auction.builder.AuctionBuilder;
import br.com.caelum.auction.domain.Auction;
import br.com.caelum.auction.infra.dao.AuctionDao;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test of {@link AuctionCloser}
 * User: helmedeiros
 * Date: 9/26/13
 * Time: 9:05 PM
 */
public class AuctionCloserTest {

    public static final String VALID_AUCTION_NAME = "PLAYSTATION 3";
    public static final int SEVEN_DAYS_AGO = -7;
    public static final int FOURTEEN_DAYS_AGO = -14;
    public static final int ONE_DAY_AGO = -1;
    public static final int TWO_DAYS_AGO = -2;
    public static final boolean IS_NOT_CLOSED = false;
    public static final boolean IS_CLOSED = true;

    @Test public void shouldCloseAuctionsBeganMoreThanWeekAgo() throws Exception {
        AuctionDao auctionDaoMock = mock(AuctionDao.class);

        Calendar twoWeeksAgoDate = giveMeDateFrom(FOURTEEN_DAYS_AGO);
        Calendar oneWeekAgo = giveMeDateFrom(SEVEN_DAYS_AGO);

        final Auction openAuction1 = createAuctionAndAssertItIs(VALID_AUCTION_NAME, twoWeeksAgoDate, IS_NOT_CLOSED);
        final Auction openAuction2 = createAuctionAndAssertItIs(VALID_AUCTION_NAME, oneWeekAgo, IS_NOT_CLOSED);

        List<Auction> openAuctionList = Arrays.asList(openAuction1, openAuction2);
        when(auctionDaoMock.actuals()).thenReturn(openAuctionList);

        final AuctionCloser auctionCloser = new AuctionCloser(auctionDaoMock);

        auctionCloser.close();

        assertThat(auctionCloser.getClosedTotal(), equalTo(2));
        assertThat(openAuction1.isClosed(), equalTo(IS_CLOSED));
        assertThat(openAuction2.isClosed(), equalTo(IS_CLOSED));
    }

    @Test public void shouldNotCloseAuctionsBeganLessThanWeekAgo() throws Exception {

        final Calendar yesterday = giveMeDateFrom(ONE_DAY_AGO);
        final Calendar beforeYesterday = giveMeDateFrom(TWO_DAYS_AGO);

        final Auction openAuctionFromYesterday = createAuctionAndAssertItIs(VALID_AUCTION_NAME, yesterday, IS_NOT_CLOSED);
        final Auction openAuctionFromBeforeYesterday = createAuctionAndAssertItIs(VALID_AUCTION_NAME, beforeYesterday, IS_NOT_CLOSED);

        AuctionDao mockDao = mock(AuctionDao.class);
        when(mockDao.actuals()).thenReturn(Arrays.asList(openAuctionFromYesterday, openAuctionFromBeforeYesterday));

        final AuctionCloser auctionCloser = new AuctionCloser(mockDao);
        auctionCloser.close();

        assertThat(auctionCloser.getClosedTotal(), equalTo(0));
        assertThat(openAuctionFromYesterday.isClosed(), equalTo(IS_NOT_CLOSED));
        assertThat(openAuctionFromBeforeYesterday.isClosed(), equalTo(IS_NOT_CLOSED));

    }

    @Test public void shouldDoNothingWhenNoOpenAuctionExists() throws Exception {
        final AuctionDao mockDAO = mock(AuctionDao.class);
        when(mockDAO.actuals()).thenReturn(new ArrayList<Auction>());

        final AuctionCloser auctionCloser = new AuctionCloser(mockDAO);
        auctionCloser.close();

        assertThat(auctionCloser.getClosedTotal(), equalTo(0));
    }

    private Auction createAuctionAndAssertItIs(final String auctionName, final Calendar onDate, final boolean closed) {
        final Auction openAuctionFromYesterday = new AuctionBuilder().to(auctionName).onDate(onDate).build();
        assertThat(openAuctionFromYesterday.isClosed(), equalTo(closed));
        return openAuctionFromYesterday;
    }

    private Calendar giveMeDateFrom(final int dayAgo) {
        final Calendar expectedDate = Calendar.getInstance(); expectedDate.add(Calendar.DAY_OF_MONTH, dayAgo);
        return expectedDate;
    }
}
