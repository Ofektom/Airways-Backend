package org.ofektom.airwaysdemobackend.repository;

import org.ofektom.airwaysdemobackend.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
}
