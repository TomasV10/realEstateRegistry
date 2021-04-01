package com.practise.realEstateRegistry.taxrate;

import com.practise.realEstateRegistry.property.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TaxRatesRepository extends JpaRepository<TaxRate, Long> {
    @Query(value = "SELECT * from tax_rates t where t.property_type = :#{#propertyType.name()} AND t.valid_from <= :when AND" +
            "(t.valid_to is null OR t.valid_to > :when) limit 1", nativeQuery = true)
    Optional<TaxRate> findByPropertyTypeForDate(@Param("propertyType") PropertyType propertyType,
                                                @Param("when") LocalDate when);


}
