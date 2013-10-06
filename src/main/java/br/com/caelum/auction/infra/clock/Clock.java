package br.com.caelum.auction.infra.clock;

import java.util.Calendar;

/**
 * Represents a clock.
 * User: helmedeiros
 * Date: 10/6/13
 * Time: 1:15 PM
 */
public interface Clock {
    Calendar today();
}
