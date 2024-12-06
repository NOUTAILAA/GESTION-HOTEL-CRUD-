package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Chambre;

@Repository
public interface ChambreRepository extends JpaRepository<Chambre, Long> {}
