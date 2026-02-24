package com.septeo.ulyses.technical.test.service;

import com.septeo.ulyses.technical.test.entity.Sales;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Sales operations.
 */
public interface SalesService {

    /**
     * Get all sales.
     *
     * @return a list of all sales
     */
    List<Sales> getAllSales(Pageable page);

    /**
     * Get a sales by its ID.
     *
     * @param id the ID of the sales to find
     * @return an Optional containing the sales if found, or empty if not found
     */
    Optional<Sales> getSalesById(Long id);

    /**
     * Get all sales by given brandId.
     *
     * @param brandId the ID of the brand to find
     * @return an Optional containing the list of sales if found, or empty list if not found
     */
    Optional<List<Sales>> getSalesByBrandId(Long brandId);

    /**
     * Get all sales by given vehicleId
     *
     * @param vehicleId the ID of the vehicle to find
     * @return an Optional containing the list of sales if found, or empty list if not found
     */
    Optional<List<Sales>> getSalesByVehicleId(Long vehicleId);

    /**
     * Get top 5 of best sales
     *
     * @param startDate the start date of the sales to filter
     * @param endDate the end date of the sales to filter
     * @return an Optional containing the list of sales if found, or empty list if not found
     */
    Optional<List<Sales>> getBestSales(LocalDate startDate, LocalDate endDate);
}
