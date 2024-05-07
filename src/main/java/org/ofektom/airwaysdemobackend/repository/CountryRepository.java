package org.ofektom.airwaysdemobackend.repository;


import org.ofektom.airwaysdemobackend.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByIsoCode(String isoCode);
}
