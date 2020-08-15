package com.example.binchecker.repository;

import com.example.binchecker.model.Bin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BinRepository extends JpaRepository<Bin, Long> {
    Bin findByBin(String substring);
}
