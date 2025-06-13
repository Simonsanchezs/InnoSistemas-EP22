package com.example.innosistemas.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.innosistemas.entity.Curso;
import com.example.innosistemas.entity.Equipo;
import com.example.innosistemas.repository.CursoRepository;
import com.example.innosistemas.repository.EquipoRepository;


import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class EquipoService {

    private final EquipoRepository equipoRepository;
    private final CursoRepository cursoRepository;

    @Autowired
    public EquipoService(EquipoRepository equipoRepository, CursoRepository cursoRepository) {
        this.equipoRepository = equipoRepository;
        this.cursoRepository = cursoRepository;
    }

    public List<Equipo> findAll() {
        return equipoRepository.findAll();
    }

    public Equipo findById(Integer id) {
        return equipoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado"));
    }

    public Equipo save(Equipo equipo, Integer cursoId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado"));
        equipo.setCurso(curso);
        equipo.setFecha_creacion(LocalDateTime.now());
        return equipoRepository.save(equipo);
    }

    public Equipo update(Integer id, Equipo equipoDetails) {
        Equipo equipo = findById(id);
        equipo.setNombre(equipoDetails.getNombre());
        return equipoRepository.save(equipo);
    }

    public void deleteById(Integer id) {
        equipoRepository.deleteById(id);
    }

    public List<Equipo> findByCursoId(Integer cursoId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado"));
        return equipoRepository.findByCurso(curso);
    }
}
