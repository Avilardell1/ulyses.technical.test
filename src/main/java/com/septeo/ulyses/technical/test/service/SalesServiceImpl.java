package com.septeo.ulyses.technical.test.service;

import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public Optional<List<Sales>> getBestSales(LocalDate startDate, LocalDate endDate) { return salesRepository.findBestSales(startDate, endDate); }

}
