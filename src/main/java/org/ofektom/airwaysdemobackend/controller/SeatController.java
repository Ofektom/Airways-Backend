package org.ofektom.airwaysdemobackend.controller;

import org.ofektom.airwaysdemobackend.dto.SeatListDto;
import org.ofektom.airwaysdemobackend.exception.SeatListNotFoundException;
import org.ofektom.airwaysdemobackend.serviceImpl.SeatServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/seat")
@CrossOrigin(origins = {"http://localhost:5173", "https://airway-ng.netlify.app", "https://airway-front-end.onrender.com"}, allowCredentials = "true")
public class SeatController {
        private final SeatServiceImpl seatServiceImp;

        public SeatController(SeatServiceImpl seatServiceImp) {
            this.seatServiceImp = seatServiceImp;
        }
        @GetMapping("/get-SeatList/{seatId}")
        public ResponseEntity<List<SeatListDto>> getSeatForClass (@PathVariable Long seatId) throws SeatListNotFoundException {
            List<SeatListDto> seatListDtosGotten =  seatServiceImp.getSeatListForSeat(seatId);
            return ResponseEntity.ok(seatListDtosGotten);
        }
}
