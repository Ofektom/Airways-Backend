package org.ofektom.airwaysdemobackend.repository;

import org.ofektom.airwaysdemobackend.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
