package br.com.caelum.auction.service;

import br.com.caelum.auction.builder.AuctionBuilder;
import br.com.caelum.auction.domain.Auction;
import br.com.caelum.auction.infra.dao.AuctionDao;
import org.junit.Test;

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

    @Test public void shouldCloseAuctionsBeganMoreThanWeekAgo() throws Exception {
        AuctionDao auctionDaoMock = mock(AuctionDao.class);

        Calendar twoWeeksAgoDate = Calendar.getInstance();
        twoWeeksAgoDate.add(Calendar.DAY_OF_MONTH, -14);

        Calendar oneWeekAgo = Calendar.getInstance();
        oneWeekAgo.add(Calendar.DAY_OF_MONTH, -7);

        Calendar thisWeek = Calendar.getInstance();
        oneWeekAgo.add(Calendar.DAY_OF_MONTH, -1);

        final Auction openAuction1 = new AuctionBuilder().to("PLAYSTATION 3").onDate(twoWeeksAgoDate).build();
        final Auction openAuction2 = new AuctionBuilder().to("PLAYSTATION 3").onDate(oneWeekAgo).build();
        final Auction openAuction3 = new AuctionBuilder().to("PLAYSTATION 3").onDate(thisWeek).build();

        List<Auction> openAuctionList = Arrays.asList(openAuction1, openAuction2);
        when(auctionDaoMock.actuals()).thenReturn(openAuctionList);

        final AuctionCloser auctionCloser = new AuctionCloser(auctionDaoMock);

        assertThat(openAuction1.isClosed(), equalTo(false));
        assertThat(openAuction2.isClosed(), equalTo(false));
        assertThat(openAuction3.isClosed(), equalTo(false));

        auctionCloser.close();

        assertThat(auctionCloser.getClosedTotal(), equalTo(2));
        assertThat(openAuction1.isClosed(), equalTo(true));
        assertThat(openAuction2.isClosed(), equalTo(true));
        assertThat(openAuction3.isClosed(), equalTo(false));
    }


}
