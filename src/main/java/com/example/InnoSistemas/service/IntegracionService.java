package com.example.innosistemas.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.innosistemas.entity.Equipo;
import com.example.innosistemas.entity.Estudiante;
import com.example.innosistemas.entity.Integracion;
import com.example.innosistemas.entity.Rol;
import com.example.innosistemas.repository.EquipoRepository;
import com.example.innosistemas.repository.EstudianteRepository;
import com.example.innosistemas.repository.IntegracionRepository;
import com.example.innosistemas.repository.RolRepository;


import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class IntegracionService {

    @Autowired
    private IntegracionRepository integracionRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private RolRepository rolRepository;

    public List<Integracion> findAll() {
        return integracionRepository.findAll();
    }

    public Integracion findById(Integer id) {
        return integracionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Integración no encontrada"));
    }

    public Integracion save(Integer estudianteId, Integer equipoId, Integer rolId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado"));
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));

        Integracion integracion = new Integracion();
        integracion.setEstudiante(estudiante);
        integracion.setEquipo(equipo);
        integracion.setRol(rol);
        integracion.setFecha_asignacion(LocalDateTime.now());

        return integracionRepository.save(integracion);
    }

    public void delete(Integer id) {
        integracionRepository.deleteById(id);
    }

    public List<Integracion> findByEstudianteId(Integer estudianteId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));
        return integracionRepository.findByEstudiante(estudiante);
    }

    public List<Integracion> findByEquipoId(Integer equipoId) {
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado"));
        return integracionRepository.findByEquipo(equipo);
    }
}