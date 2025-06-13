package com.example.innosistemas;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.innosistemas.entity.Equipo;
import com.example.innosistemas.entity.Estudiante;
import com.example.innosistemas.entity.Integracion;
import com.example.innosistemas.entity.Rol;
import com.example.innosistemas.repository.EquipoRepository;
import com.example.innosistemas.repository.EstudianteRepository;
import com.example.innosistemas.repository.IntegracionRepository;
import com.example.innosistemas.repository.RolRepository;
import com.example.innosistemas.service.IntegracionService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("IntegracionService Unit Tests - AAA Pattern")
class IntegracionServiceTest {

    @Mock
    private IntegracionRepository integracionRepository;

    @Mock
    private EstudianteRepository estudianteRepository;

    @Mock
    private EquipoRepository equipoRepository;

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private IntegracionService integracionService;

    private Integracion integracion1;
    private Estudiante estudianteTest;
    private Equipo equipoTest;
    private Rol rolTest;

    @BeforeEach
    void setUp() {
        estudianteTest = new Estudiante();
        estudianteTest.setId(101);
        estudianteTest.setNombre("Test Estudiante");

        equipoTest = new Equipo();
        equipoTest.setId(201);
        equipoTest.setNombre("Test Equipo");

        rolTest = new Rol();
        rolTest.setId(301);
        rolTest.setNombre("Test Rol");

        integracion1 = new Integracion();
        integracion1.setId(1);
        integracion1.setEstudiante(estudianteTest);
        integracion1.setEquipo(equipoTest);
        integracion1.setRol(rolTest);
        integracion1.setFecha_asignacion(LocalDateTime.now());
    }

    @Test
    @DisplayName("should find all integraciones")
    void findAll_returnsAllIntegraciones() {
        // ARRANGE
        when(integracionRepository.findAll()).thenReturn(Arrays.asList(integracion1));

        // ACT
        List<Integracion> result = integracionService.findAll();

        // ASSERT
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(integracion1));
        verify(integracionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("should find integracion by ID when found")
    void findById_returnsIntegracionWhenFound() {
        // ARRANGE
        when(integracionRepository.findById(1)).thenReturn(Optional.of(integracion1));

        // ACT
        Integracion result = integracionService.findById(1);

        // ASSERT
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(integracionRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when integracion by ID not found")
    void findById_throwsExceptionWhenNotFound() {
        // ARRANGE
        when(integracionRepository.findById(99)).thenReturn(Optional.empty());

        // ACT & ASSERT
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            integracionService.findById(99);
        });
        assertEquals("Integración no encontrada", exception.getMessage());
        verify(integracionRepository, times(1)).findById(99);
    }

    

    @Test
    @DisplayName("should throw EntityNotFoundException when saving integracion with non-existent student ID")
    void save_throwsExceptionWhenEstudianteNotFound() {
        // ARRANGE
        when(estudianteRepository.findById(estudianteTest.getId())).thenReturn(Optional.empty());

        // ACT & ASSERT
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            integracionService.save(estudianteTest.getId(), equipoTest.getId(), rolTest.getId());
        });
        assertEquals("Estudiante no encontrado", exception.getMessage());
        verify(estudianteRepository, times(1)).findById(estudianteTest.getId());
        verify(equipoRepository, never()).findById(anyInt());
        verify(integracionRepository, never()).save(any(Integracion.class));
    }

    // ... (Tests similares para Equipo y Rol no encontrados al guardar)



    @Test
    @DisplayName("should find integraciones by estudiante ID")
    void findByEstudianteId_returnsIntegraciones() {
        // ARRANGE
        when(estudianteRepository.findById(estudianteTest.getId())).thenReturn(Optional.of(estudianteTest));
        when(integracionRepository.findByEstudiante(estudianteTest)).thenReturn(Arrays.asList(integracion1));

        // ACT
        List<Integracion> result = integracionService.findByEstudianteId(estudianteTest.getId());

        // ASSERT
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(integracion1));
        verify(estudianteRepository, times(1)).findById(estudianteTest.getId());
        verify(integracionRepository, times(1)).findByEstudiante(estudianteTest);
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when finding integraciones by non-existent estudiante ID")
    void findByEstudianteId_throwsExceptionWhenEstudianteNotFound() {
        // ARRANGE
        when(estudianteRepository.findById(estudianteTest.getId())).thenReturn(Optional.empty());

        // ACT & ASSERT
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            integracionService.findByEstudianteId(estudianteTest.getId());
        });
        assertEquals("Estudiante no encontrado", exception.getMessage());
        verify(estudianteRepository, times(1)).findById(estudianteTest.getId());
        verify(integracionRepository, never()).findByEstudiante(any(Estudiante.class));
    }
    
    @Test
    @DisplayName("should find integraciones by equipo ID")
    void findByEquipoId_returnsIntegraciones() {
        // ARRANGE
        when(equipoRepository.findById(equipoTest.getId())).thenReturn(Optional.of(equipoTest));
        when(integracionRepository.findByEquipo(equipoTest)).thenReturn(Arrays.asList(integracion1));

        // ACT
        List<Integracion> result = integracionService.findByEquipoId(equipoTest.getId());

        // ASSERT
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(integracion1));
        verify(equipoRepository, times(1)).findById(equipoTest.getId());
        verify(integracionRepository, times(1)).findByEquipo(equipoTest);
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when finding integraciones by non-existent equipo ID")
    void findByEquipoId_throwsExceptionWhenEquipoNotFound() {
        // ARRANGE
        when(equipoRepository.findById(equipoTest.getId())).thenReturn(Optional.empty());

        // ACT & ASSERT
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            integracionService.findByEquipoId(equipoTest.getId());
        });
        assertEquals("Equipo no encontrado", exception.getMessage());
        verify(equipoRepository, times(1)).findById(equipoTest.getId());
        verify(integracionRepository, never()).findByEquipo(any(Equipo.class));
    }
}