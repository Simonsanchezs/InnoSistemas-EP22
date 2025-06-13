package com.example.InnoSistemas;


import com.example.InnoSistemas.entity.Equipo;
import com.example.InnoSistemas.entity.Estudiante;
import com.example.InnoSistemas.entity.Notificacion;
import com.example.InnoSistemas.repository.EquipoRepository;
import com.example.InnoSistemas.repository.EstudianteRepository;
import com.example.InnoSistemas.repository.NotificacionRepository;
import com.example.InnoSistemas.service.NotificacionService;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("NotificacionService Unit Tests - AAA Pattern")
class NotificacionServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @Mock
    private EstudianteRepository estudianteRepository;

    @Mock
    private EquipoRepository equipoRepository;

    @InjectMocks
    private NotificacionService notificacionService;

    private Notificacion notificacion1;
    private Estudiante estudianteTest;
    private Equipo equipoTest;

    @BeforeEach
    void setUp() {
        estudianteTest = new Estudiante();
        estudianteTest.setId(101);
        estudianteTest.setNombre("Test Estudiante");

        equipoTest = new Equipo();
        equipoTest.setId(201);
        equipoTest.setNombre("Test Equipo");

        notificacion1 = new Notificacion();
        notificacion1.setId(1);
        notificacion1.setTipo("MENSAJE");
        notificacion1.setEstudiante(estudianteTest);
        notificacion1.setEquipo(equipoTest);
        notificacion1.setFecha_envio(LocalDateTime.now());
        notificacion1.setLeida(false);
    }

    @Test
    @DisplayName("should find all notificaciones")
    void findAll_returnsAllNotificaciones() {
        // ARRANGE
        when(notificacionRepository.findAll()).thenReturn(Arrays.asList(notificacion1));

        // ACT
        List<Notificacion> result = notificacionService.findAll();

        // ASSERT
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(notificacion1));
        verify(notificacionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("should find notificacion by ID when found")
    void findById_returnsNotificacionWhenFound() {
        // ARRANGE
        when(notificacionRepository.findById(1)).thenReturn(Optional.of(notificacion1));

        // ACT
        Notificacion result = notificacionService.findById(1);

        // ASSERT
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(notificacionRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when notificacion by ID not found")
    void findById_throwsExceptionWhenNotFound() {
        // ARRANGE
        when(notificacionRepository.findById(99)).thenReturn(Optional.empty());

        // ACT & ASSERT
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            notificacionService.findById(99);
        });
        assertEquals("Notificación no encontrada", exception.getMessage());
        verify(notificacionRepository, times(1)).findById(99);
    }

    @Test
    @DisplayName("should create a new notificacion with valid IDs")
    void create_createsNewNotificationWithValidIds() {
        // ARRANGE
        Notificacion newNotificacion = new Notificacion(); // Se va a devolver este objeto con un ID real
        newNotificacion.setId(2);
        newNotificacion.setTipo("ALERTA");
        newNotificacion.setEstudiante(estudianteTest);
        newNotificacion.setEquipo(equipoTest);
        newNotificacion.setFecha_envio(LocalDateTime.now());
        newNotificacion.setLeida(false);

        when(estudianteRepository.findById(estudianteTest.getId())).thenReturn(Optional.of(estudianteTest));
        when(equipoRepository.findById(equipoTest.getId())).thenReturn(Optional.of(equipoTest));
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(newNotificacion);

        // ACT
        Notificacion result = notificacionService.create(
            "ALERTA", estudianteTest.getId(), equipoTest.getId()
        );

        // ASSERT
        assertNotNull(result);
        assertNotNull(result.getFecha_envio());
        assertFalse(result.isLeida());
        assertEquals("ALERTA", result.getTipo());
        assertEquals(estudianteTest, result.getEstudiante());
        assertEquals(equipoTest, result.getEquipo());
        verify(estudianteRepository, times(1)).findById(estudianteTest.getId());
        verify(equipoRepository, times(1)).findById(equipoTest.getId());
        verify(notificacionRepository, times(1)).save(any(Notificacion.class));
    }

    @Test
    @DisplayName("should mark notificacion as read when found")
    void marcarComoLeida_marksAsReadWhenFound() {
        // ARRANGE
        Notificacion notificacionOriginal = new Notificacion();
        notificacionOriginal.setId(1);
        notificacionOriginal.setLeida(false); // No leída inicialmente
        notificacionOriginal.setTipo("TEST");
        notificacionOriginal.setEstudiante(estudianteTest);
        notificacionOriginal.setEquipo(equipoTest);
        notificacionOriginal.setFecha_envio(LocalDateTime.now());

        Notificacion notificacionLeida = new Notificacion();
        notificacionLeida.setId(1);
        notificacionLeida.setLeida(true); // Leída después
        notificacionLeida.setTipo("TEST");
        notificacionLeida.setEstudiante(estudianteTest);
        notificacionLeida.setEquipo(equipoTest);
        notificacionLeida.setFecha_envio(notificacionOriginal.getFecha_envio());


        when(notificacionRepository.findById(1)).thenReturn(Optional.of(notificacionOriginal));
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacionLeida);

        // ACT
        Notificacion result = notificacionService.marcarComoLeida(1);

        // ASSERT
        assertNotNull(result);
        assertTrue(result.isLeida());
        verify(notificacionRepository, times(1)).findById(1);
        verify(notificacionRepository, times(1)).save(notificacionOriginal); // Verifica que el objeto original modificado se guardó
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when marking non-existent notificacion as read")
    void marcarComoLeida_throwsExceptionWhenNotFound() {
        // ARRANGE
        when(notificacionRepository.findById(99)).thenReturn(Optional.empty());

        // ACT & ASSERT
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            notificacionService.marcarComoLeida(99);
        });
        assertEquals("Notificación no encontrada", exception.getMessage());
        verify(notificacionRepository, times(1)).findById(99);
        verify(notificacionRepository, never()).save(any(Notificacion.class));
    }

}