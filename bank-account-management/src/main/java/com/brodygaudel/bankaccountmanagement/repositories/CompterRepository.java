package com.brodygaudel.bankaccountmanagement.repositories;

import com.brodygaudel.bankaccountmanagement.entities.Compter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CompterRepository extends JpaRepository<Compter, Long> {
    @Query("SELECT MAX(c.id) FROM Compter c")
    Optional<Long> findMaxId();
}
