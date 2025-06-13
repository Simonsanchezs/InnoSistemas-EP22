package com.example.innosistemas.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.innosistemas.entity.Curso;
import com.example.innosistemas.entity.Estudiante;
import com.example.innosistemas.service.EstudianteService;

import java.util.List;

@Controller
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @QueryMapping
    public List<Estudiante> estudiantes() {
        return estudianteService.findAll();
    }

    @QueryMapping
    public Estudiante estudiante(@Argument int id) {
        return estudianteService.findById(id);
    }

    @QueryMapping
    public List<Curso> cursosPorEstudiante(@Argument int estudianteId) {
        return estudianteService.getCursosByEstudianteId(estudianteId);
    }

    @MutationMapping
    public Estudiante crearEstudiante(@Argument Estudiante input) {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(input.getNombre());
        estudiante.setEmail(input.getEmail());
        // otros campos según tu modelo
        return estudianteService.save(estudiante);
    }

    @MutationMapping
    public Estudiante actualizarEstudiante(@Argument Estudiante input) {
        Estudiante estudiante = new Estudiante();
        estudiante.setId(input.getId());
        estudiante.setNombre(input.getNombre());
        estudiante.setEmail(input.getEmail());
        // otros campos según tu modelo
        return estudianteService.update(estudiante);
    }

    @MutationMapping
    public Boolean eliminarEstudiante(@Argument int id) {
        estudianteService.deleteById(id);
        return true;
    }
}
