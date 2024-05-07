package org.ofektom.airwaysdemobackend.repository;


import org.ofektom.airwaysdemobackend.model.SeatList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatListRepository extends JpaRepository<SeatList, Long> {
    Optional <List<SeatList>> findAllBySeat_Id(Long seatId);
    Optional <List<SeatList>> findAllBySeat_IdAndOccupied(Long seatId, Boolean isOccupied);
}
