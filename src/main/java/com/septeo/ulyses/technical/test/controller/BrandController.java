package com.septeo.ulyses.technical.test.controller;

import com.septeo.ulyses.technical.test.entity.Brand;
import com.septeo.ulyses.technical.test.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for Brand operations.
 */
@RestController
@RequestMapping("/api/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    @Cacheable(value = "brands")
    public ResponseEntity<List<Brand>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    @GetMapping("/{id}")
    @Cacheable(value = "brandById")
    public ResponseEntity<Brand> getBrandById(@PathVariable Long id) {
        return brandService.getBrandById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @CacheEvict(value = {"brand"}, allEntries = true)
    public ResponseEntity<Brand> createBrand(@RequestBody Brand brand) {
        Brand savedBrand = brandService.saveBrand(brand);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBrand);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = {"brand", "brandById"}, allEntries = true)
    public ResponseEntity<Brand> updateBrand(@PathVariable Long id, @RequestBody Brand brand) {
        return brandService.getBrandById(id)
                .map(existingBrand -> {
                    brand.setId(id);
                    return ResponseEntity.ok(brandService.saveBrand(brand));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = {"brand", "brandById"}, allEntries = true)
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        if (brandService.getBrandById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
}
