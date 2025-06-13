package com.example.InnoSistemas.repository;

import com.example.InnoSistemas.entity.Dashboard;
import com.example.InnoSistemas.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, Integer> {
    List<Dashboard> findByEquipo(Equipo equipo);
}
