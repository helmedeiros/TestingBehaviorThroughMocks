package br.com.caelum.auction.infra.dao;

import br.com.caelum.auction.domain.Payment;

/**
 * Represents an repository for {@link Payment}
 * User: helmedeiros
 * Date: 10/5/13
 * Time: 6:00 PM
 */
public interface PaymentRepository {
    void save(Payment payment);
}
