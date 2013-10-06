package br.com.caelum.auction.infra.clock;

import java.util.Calendar;

/**
 * Represents the System clock.
 * User: helmedeiros
 * Date: 10/6/13
 * Time: 1:19 PM
 */
public class SystemClock implements Clock {
    @Override
    public Calendar today() {
        return Calendar.getInstance();
    }
}
