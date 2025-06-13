package com.example.innosistemas;


import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.innosistemas.entity.Curso;
import com.example.innosistemas.entity.Equipo;
import com.example.innosistemas.repository.CursoRepository;
import com.example.innosistemas.repository.EquipoRepository;
import com.example.innosistemas.service.EquipoService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EquipoService Unit Tests - AAA Pattern")
class EquipoServiceTest {

    @Mock
    private EquipoRepository equipoRepository; // Mock del repositorio de Equipo

    @Mock
    private CursoRepository cursoRepository; // Mock del repositorio de Curso (dependencia de EquipoService)

    @InjectMocks
    private EquipoService equipoService; // Instancia real del servicio a probar, con mocks inyectados

    private Equipo equipo1;
    private Equipo equipo2;
    private Curso cursoTest;

    @BeforeEach
    void setUp() {
        // ARRANGE: Preparar datos de prueba comunes
        cursoTest = new Curso();
        cursoTest.setId(100);
        cursoTest.setNombre("Curso de Prueba");
        cursoTest.setSemestre("2025-1");

        equipo1 = new Equipo();
        equipo1.setId(1);
        equipo1.setNombre("Equipo Alfa");
        equipo1.setFecha_creacion(LocalDateTime.now());
        equipo1.setCurso(cursoTest); // Asignar el curso preparado

        equipo2 = new Equipo();
        equipo2.setId(2);
        equipo2.setNombre("Equipo Beta");
        equipo2.setFecha_creacion(LocalDateTime.now());
        equipo2.setCurso(cursoTest);
    }

    @Test
    @DisplayName("should find all equipos")
    void findAll_returnsAllEquipos() {
        // ARRANGE
        when(equipoRepository.findAll()).thenReturn(Arrays.asList(equipo1, equipo2));

        // ACT
        List<Equipo> result = equipoService.findAll();

        // ASSERT
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(equipo1));
        assertTrue(result.contains(equipo2));
        verify(equipoRepository, times(1)).findAll(); // Verificar llamada al mock
    }

    @Test
    @DisplayName("should find equipo by ID when found")
    void findById_returnsEquipoWhenFound() {
        // ARRANGE
        when(equipoRepository.findById(1)).thenReturn(Optional.of(equipo1));

        // ACT
        Equipo result = equipoService.findById(1);

        // ASSERT
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Equipo Alfa", result.getNombre());
        verify(equipoRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when equipo by ID not found")
    void findById_throwsExceptionWhenNotFound() {
        // ARRANGE
        when(equipoRepository.findById(99)).thenReturn(Optional.empty());

        // ACT & ASSERT
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            equipoService.findById(99);
        });
        assertEquals("Equipo no encontrado", exception.getMessage());
        verify(equipoRepository, times(1)).findById(99);
    }

    @Test
    @DisplayName("should save a new equipo with a valid cursoId")
    void save_savesNewEquipoWithValidCurso() {
        // ARRANGE
        Equipo newEquipo = new Equipo();
        newEquipo.setNombre("Equipo Gamma");
        
        when(cursoRepository.findById(cursoTest.getId())).thenReturn(Optional.of(cursoTest)); // Mockear que el curso existe
        when(equipoRepository.save(any(Equipo.class))).thenReturn(equipo1); // Mockear que save devuelve un equipo

        // ACT
        Equipo result = equipoService.save(newEquipo, cursoTest.getId());

        // ASSERT
        assertNotNull(result);
        assertEquals(equipo1.getId(), result.getId());
        assertEquals(cursoTest, result.getCurso()); // Verificar que el curso se asignó
        assertNotNull(result.getFecha_creacion()); // Verificar que la fecha se asignó
        verify(cursoRepository, times(1)).findById(cursoTest.getId());
        verify(equipoRepository, times(1)).save(newEquipo); // Verifica que el equipo se guardó
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when saving equipo with non-existent cursoId")
    void save_throwsExceptionWhenCursoNotFound() {
        // ARRANGE
        Equipo newEquipo = new Equipo();
        newEquipo.setNombre("Equipo Fallo");
        int nonExistentCursoId = 999;
        when(cursoRepository.findById(nonExistentCursoId)).thenReturn(Optional.empty());

        // ACT & ASSERT
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            equipoService.save(newEquipo, nonExistentCursoId);
        });
        assertEquals("Curso no encontrado", exception.getMessage());
        verify(cursoRepository, times(1)).findById(nonExistentCursoId);
        verify(equipoRepository, never()).save(any(Equipo.class)); // Asegura que save nunca se llamó
    }

    @Test
    @DisplayName("should update an existing equipo")
    void update_updatesExistingEquipo() {
        // ARRANGE
        Equipo updatedDetails = new Equipo();
        updatedDetails.setNombre("Equipo Alfa Actualizado");

        when(equipoRepository.findById(1)).thenReturn(Optional.of(equipo1)); // Mockear que el equipo existe
        when(equipoRepository.save(any(Equipo.class))).thenReturn(equipo1); // Mockear el save

        // ACT
        Equipo result = equipoService.update(1, updatedDetails);

        // ASSERT
        assertNotNull(result);
        assertEquals("Equipo Alfa Actualizado", result.getNombre()); // Verificar que el nombre se actualizó
        verify(equipoRepository, times(1)).findById(1);
        verify(equipoRepository, times(1)).save(equipo1); // Verifica que se llamó save con el objeto actualizado
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when updating non-existent equipo")
    void update_throwsExceptionWhenNotFound() {
        // ARRANGE
        Equipo nonExistentEquipo = new Equipo();
        when(equipoRepository.findById(99)).thenReturn(Optional.empty());

        // ACT & ASSERT
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            equipoService.update(99, nonExistentEquipo);
        });
        assertEquals("Equipo no encontrado", exception.getMessage());
        verify(equipoRepository, times(1)).findById(99);
        verify(equipoRepository, never()).save(any(Equipo.class));
    }

    @Test
    @DisplayName("should delete an equipo by ID")
    void deleteById_deletesEquipo() {
        // ARRANGE
        doNothing().when(equipoRepository).deleteById(1); // Mockear que deleteById no lanza excepción

        // ACT
        equipoService.deleteById(1);

        // ASSERT
        verify(equipoRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("should find equipos by Curso ID")
    void findByCursoId_returnsEquipos() {
        // ARRANGE
        when(cursoRepository.findById(cursoTest.getId())).thenReturn(Optional.of(cursoTest));
        when(equipoRepository.findByCurso(cursoTest)).thenReturn(Arrays.asList(equipo1, equipo2));

        // ACT
        List<Equipo> result = equipoService.findByCursoId(cursoTest.getId());

        // ASSERT
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(equipo1));
        verify(cursoRepository, times(1)).findById(cursoTest.getId());
        verify(equipoRepository, times(1)).findByCurso(cursoTest);
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when finding equipos by non-existent Curso ID")
    void findByCursoId_throwsExceptionWhenCursoNotFound() {
        // ARRANGE
        int nonExistentCursoId = 999;
        when(cursoRepository.findById(nonExistentCursoId)).thenReturn(Optional.empty());

        // ACT & ASSERT
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            equipoService.findByCursoId(nonExistentCursoId);
        });
        assertEquals("Curso no encontrado", exception.getMessage());
        verify(cursoRepository, times(1)).findById(nonExistentCursoId);
        verify(equipoRepository, never()).findByCurso(any(Curso.class));
    }
}