package br.com.caelum.auction.infra.mail;

import br.com.caelum.auction.domain.Auction;

/**
 * Auxiliary class to send emails.
 * User: helmedeiros
 * Date: 9/29/13
 * Time: 12:01 PM
 */
public interface MailSender {
    public void send(Auction auction);
}
