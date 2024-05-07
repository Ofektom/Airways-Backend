package org.ofektom.airwaysdemobackend.repository;


import org.ofektom.airwaysdemobackend.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
