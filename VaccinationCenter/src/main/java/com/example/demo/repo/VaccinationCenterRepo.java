package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.VaccinationCenter;

public interface VaccinationCenterRepo extends JpaRepository<VaccinationCenter,Integer>{

}
