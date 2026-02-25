package com.septeo.ulyses.technical.test.service;

import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.*;

/**
 * Implementation of the SalesService interface.
 * This class provides the implementation for all sales-related operations.
 */
@Service
@Transactional(readOnly = false)
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesRepository salesRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sales> getAllSales(Pageable pageable) {
        return salesRepository.findAll(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Sales> getSalesById(Long id) {
        return salesRepository.findById(id);
    }

    @Override
    public Optional<List<Sales>> getSalesByBrandId(Long brandId) { return salesRepository.findAllByBrandId(brandId); }

    @Override
    public Optional<List<Sales>> getSalesByVehicleId(Long vehicleId) { return salesRepository.findAllByVehicleId(vehicleId); }

    @Override
    public Optional<List<Sales>> getBestSales(LocalDate startDate, LocalDate endDate) {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        List<Sales> sales = salesRepository.findAll(pageable);

        Map<Long, Integer> countMap = new HashMap<>();
        Map<Long, Sales> salesMap = new HashMap<>();

        for (Sales sale : sales) {
            Long vehicleId = sale.getVehicle().getId();
            if (isInDateRange(sale, startDate, endDate)) {
                countMap.put(vehicleId, countMap.getOrDefault(vehicleId, 0) + 1);
                salesMap.put(vehicleId, sale);
            }
        }

        List<Long> topVehicleIds = new ArrayList<>();
        for (Long vehicleId : countMap.keySet()) {
            if (topVehicleIds.size() < 5) {
                topVehicleIds.add(vehicleId);
            }
        }

        List<Sales> result = new ArrayList<>();

        for (Long vehicleId : topVehicleIds) {
            result.add(salesMap.get(vehicleId));
        }

        return Optional.of(result);
    }

    private Boolean isInDateRange(Sales sale, LocalDate startDate, LocalDate endDate) {
        LocalDate date = sale.getSaleDate();

        return date != null && (startDate == null || !date.isBefore(startDate)) &&
                (endDate == null   || !date.isAfter(endDate));
    }

}
