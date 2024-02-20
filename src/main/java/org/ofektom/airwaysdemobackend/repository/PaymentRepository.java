package org.ofektom.airwaysdemobackend.repository;

import org.ofektom.airwaysdemobackend.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
