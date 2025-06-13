package com.example.innosistemas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.innosistemas.entity.Dashboard;
import com.example.innosistemas.entity.Equipo;


import java.util.List;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, Integer> {
    List<Dashboard> findByEquipo(Equipo equipo);
}
