package org.ofektom.airwaysdemobackend.service;


import org.ofektom.airwaysdemobackend.dto.SeatListDto;
import org.ofektom.airwaysdemobackend.model.SeatList;

import java.util.List;

public interface SeatService {
    List<SeatListDto> getSeatListForSeat (Long seatId);
    List<SeatListDto> convertToSeatDTO (List<SeatList> seatLists);
}
