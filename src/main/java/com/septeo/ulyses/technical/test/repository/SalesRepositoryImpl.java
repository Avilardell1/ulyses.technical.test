package com.septeo.ulyses.technical.test.repository;

import com.septeo.ulyses.technical.test.entity.Brand;
import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.entity.Vehicle;
import jakarta.persistence.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the SalesRepository interface.
 * This class provides the implementation for all sales-related operations.
 */
@Repository
public class SalesRepositoryImpl implements SalesRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Sales> findAll(Pageable pageable) {
        String stringQuery = "SELECT s FROM Sales s";
        Query query = entityManager.createQuery(stringQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        return query.getResultList();
    }

    @Override
    public Optional<Sales> findById(Long id) {
        String stringQuery = "SELECT s FROM Sales s WHERE s.id = :id";
        Query query = entityManager.createQuery(stringQuery);
        query.setParameter("id", id);

        try {
            return Optional.of((Sales) query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Sales>> findAllByBrandId(Long brandId) {
        String stringQuery = "SELECT s FROM Sales s WHERE s.brand.id = :id";
        TypedQuery<Sales> query = entityManager.createQuery(stringQuery, Sales.class);
        query.setParameter("id", brandId);
        List<Sales> result = query.getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }

    @Override
    public Optional<List<Sales>> findAllByVehicleId(Long vehicleId) {
        String stringQuery = "SELECT s FROM Sales s WHERE s.vehicle.id = :id";
        TypedQuery<Sales> query = entityManager.createQuery(stringQuery, Sales.class);
        query.setParameter("id", vehicleId);
        List<Sales> result = query.getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }

    @Override
    public Optional<List<Sales>> findBestSales(LocalDate startDate, LocalDate endDate) {
        TypedQuery<Sales> query = null;
        String stringQuery = "SELECT s FROM Sales s";
        if (startDate != null && endDate != null) {
            stringQuery += " WHERE s.saleDate BETWEEN :startDate AND :endDate ORDER BY s.price DESC LIMIT 5";
            query = entityManager.createQuery(stringQuery, Sales.class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
        } else {
            stringQuery += " ORDER BY s.price DESC LIMIT 5";
            query = entityManager.createQuery(stringQuery, Sales.class);
        }
        List<Sales> result = query.getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }
}
