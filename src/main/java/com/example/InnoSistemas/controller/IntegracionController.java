package com.example.InnoSistemas.controller;

import com.example.InnoSistemas.entity.Integracion;
import com.example.InnoSistemas.service.IntegracionService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class IntegracionController {

    private final IntegracionService integracionService;

    public IntegracionController(IntegracionService integracionService) {
        this.integracionService = integracionService;
    }

    @QueryMapping
    public List<Integracion> integraciones() {
        return integracionService.findAll();
    }

    @QueryMapping
    public Integracion integracion(@Argument int id) {
        return integracionService.findById(id);
    }

    @QueryMapping
    public List<Integracion> integracionesPorEstudiante(@Argument int estudianteId) {
        return integracionService.findByEstudianteId(estudianteId);
    }

    @QueryMapping
    public List<Integracion> integracionesPorEquipo(@Argument int equipoId) {
        return integracionService.findByEquipoId(equipoId);
    }

    @MutationMapping
    public Integracion crearIntegracion(@Argument int estudianteId, @Argument int equipoId, @Argument int rolId) {
        return integracionService.save(estudianteId, equipoId, rolId);
    }

    @MutationMapping
    public Boolean eliminarIntegracion(@Argument int id) {
        integracionService.delete(id);
        return true;
    }
}
