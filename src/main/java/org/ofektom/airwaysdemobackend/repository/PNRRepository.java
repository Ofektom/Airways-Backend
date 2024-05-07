package org.ofektom.airwaysdemobackend.repository;


import org.ofektom.airwaysdemobackend.model.PNR;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PNRRepository extends JpaRepository<PNR, Long> {

}
