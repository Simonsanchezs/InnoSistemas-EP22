package com.example.InnoSistemas.service;

import com.example.InnoSistemas.entity.Equipo;
import com.example.InnoSistemas.entity.Estudiante;
import com.example.InnoSistemas.entity.Notificacion;
import com.example.InnoSistemas.repository.EquipoRepository;
import com.example.InnoSistemas.repository.EstudianteRepository;
import com.example.InnoSistemas.repository.NotificacionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    public List<Notificacion> findAll() {
        return notificacionRepository.findAll();
    }

    public Notificacion findById(Integer id) {
        return notificacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notificación no encontrada"));
    }

    public Notificacion create(String tipo, Integer estudianteId, Integer equipoId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado"));

        Notificacion notificacion = new Notificacion();
        notificacion.setTipo(tipo);
        notificacion.setEstudiante(estudiante);
        notificacion.setEquipo(equipo);
        notificacion.setFecha_envio(LocalDateTime.now());
        notificacion.setLeida(false);

        return notificacionRepository.save(notificacion);
    }

    public Notificacion marcarComoLeida(Integer id) {
        Notificacion notificacion = findById(id);
        notificacion.setLeida(true);
        return notificacionRepository.save(notificacion);
    }

    public void delete(Integer id) {
        notificacionRepository.deleteById(id);
    }

    public List<Notificacion> findByEstudianteId(Integer estudianteId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));
        return notificacionRepository.findByEstudiante(estudiante);
    }

    public List<Notificacion> findByEquipoId(Integer equipoId) {
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado"));
        return notificacionRepository.findByEquipo(equipo);
    }
}
