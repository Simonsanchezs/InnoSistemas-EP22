package com.example.innosistemas.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.innosistemas.entity.Dashboard;
import com.example.innosistemas.service.DashboardService;



import java.util.List;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @QueryMapping
    public List<Dashboard> dashboards() {
        return dashboardService.findAll();
    }

    @QueryMapping
    public Dashboard dashboard(@Argument int id) {
        return dashboardService.findById(id);
    }

    @QueryMapping
    public List<Dashboard> dashboardsPorEquipo(@Argument int equipoId) {
        return dashboardService.findByEquipoId(equipoId);
    }

    @MutationMapping
    public Dashboard crearDashboard(@Argument Dashboard dashboardInput, @Argument int equipoId) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTareas_completadas(dashboardInput.getTareas_completadas());
        dashboard.setEntregas(dashboardInput.getEntregas());
        dashboard.setEstado(dashboardInput.getEstado());
        return dashboardService.save(dashboard, equipoId);
    }

    @MutationMapping
    public Dashboard actualizarDashboard(@Argument int id, @Argument Dashboard dashboardInput) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTareas_completadas(dashboardInput.getTareas_completadas());
        dashboard.setEntregas(dashboardInput.getEntregas());
        dashboard.setEstado(dashboardInput.getEstado());
        return dashboardService.update(id, dashboard);
    }

    @MutationMapping
    public Boolean eliminarDashboard(@Argument int id) {
        dashboardService.delete(id);
        return true;
    }
}
