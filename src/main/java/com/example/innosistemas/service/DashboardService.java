package com.example.innosistemas.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.innosistemas.entity.Dashboard;
import com.example.innosistemas.entity.Equipo;
import com.example.innosistemas.repository.DashboardRepository;
import com.example.innosistemas.repository.EquipoRepository;


import java.util.List;

@Service
@Transactional
public class DashboardService {

    private final DashboardRepository dashboardRepository;
    private final EquipoRepository equipoRepository;

    @Autowired
    public DashboardService(DashboardRepository dashboardRepository, EquipoRepository equipoRepository) {
        this.dashboardRepository = dashboardRepository;
        this.equipoRepository = equipoRepository;
    }

    public List<Dashboard> findAll() {
        return dashboardRepository.findAll();
    }

    public Dashboard findById(Integer id) {
        return dashboardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dashboard no encontrado"));
    }

    public Dashboard save(Dashboard dashboard, Integer equipoId) {
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado"));
        dashboard.setEquipo(equipo);
        return dashboardRepository.save(dashboard);
    }

    public Dashboard update(Integer id, Dashboard dashboardDetails) {
        Dashboard dashboard = findById(id);
        dashboard.setTareas_completadas(dashboardDetails.getTareas_completadas());
        dashboard.setEntregas(dashboardDetails.getEntregas());
        dashboard.setEstado(dashboardDetails.getEstado());
        return dashboardRepository.save(dashboard);
    }

    public void delete(Integer id) {
        dashboardRepository.deleteById(id);
    }

    public List<Dashboard> findByEquipoId(Integer equipoId) {
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado"));
        return dashboardRepository.findByEquipo(equipo);
    }
}