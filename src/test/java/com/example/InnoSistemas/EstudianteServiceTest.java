package com.example.InnoSistemas;


import com.example.InnoSistemas.entity.Curso;
import com.example.InnoSistemas.entity.Estudiante;
import com.example.InnoSistemas.repository.EstudianteRepository;
import com.example.InnoSistemas.service.EstudianteService;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EstudianteService Unit Tests - AAA Pattern")
class EstudianteServiceTest {

    @Mock
    private EstudianteRepository estudianteRepository;

    @InjectMocks
    private EstudianteService estudianteService;

    private Estudiante estudiante1;
    private Estudiante estudiante2;
    private Curso cursoTest;

    @BeforeEach
    void setUp() {
        estudiante1 = new Estudiante();
        estudiante1.setId(1);
        estudiante1.setNombre("Ana");
        estudiante1.setApellidos("García");
        estudiante1.setEmail("ana@example.com");
        estudiante1.setPassword("pass1");

        estudiante2 = new Estudiante();
        estudiante2.setId(2);
        estudiante2.setNombre("Luis");
        estudiante2.setApellidos("Pérez");
        estudiante2.setEmail("luis@example.com");
        estudiante2.setPassword("pass2");

        cursoTest = new Curso();
        cursoTest.setId(100);
        cursoTest.setNombre("Curso de Prueba");
        cursoTest.setSemestre("2025-1");
    }

    @Test
    @DisplayName("should find all estudiantes")
    void findAll_returnsAllEstudiantes() {
        // ARRANGE
        when(estudianteRepository.findAll()).thenReturn(Arrays.asList(estudiante1, estudiante2));

        // ACT
        List<Estudiante> result = estudianteService.findAll();

        // ASSERT
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(estudiante1));
        assertTrue(result.contains(estudiante2));
        verify(estudianteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("should find estudiante by ID when found")
    void findById_returnsEstudianteWhenFound() {
        // ARRANGE
        when(estudianteRepository.findById(1)).thenReturn(Optional.of(estudiante1));

        // ACT
        Estudiante result = estudianteService.findById(1);

        // ASSERT
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Ana", result.getNombre());
        verify(estudianteRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("should throw RuntimeException when estudiante by ID not found")
    void findById_throwsExceptionWhenNotFound() {
        // ARRANGE
        when(estudianteRepository.findById(99)).thenReturn(Optional.empty());

        // ACT & ASSERT
        Exception exception = assertThrows(RuntimeException.class, () -> {
            estudianteService.findById(99);
        });
        assertEquals("Estudiante no encontrado", exception.getMessage());
        verify(estudianteRepository, times(1)).findById(99);
    }

    @Test
    @DisplayName("should save a new estudiante")
    void save_savesNewEstudiante() {
        // ARRANGE
        Estudiante newEstudiante = new Estudiante();
        newEstudiante.setNombre("Carlos");
        newEstudiante.setApellidos("Díaz");
        newEstudiante.setEmail("carlos@example.com");
        newEstudiante.setPassword("newpass");
        
        when(estudianteRepository.save(any(Estudiante.class))).thenReturn(newEstudiante);

        // ACT
        Estudiante result = estudianteService.save(newEstudiante);

        // ASSERT
        assertNotNull(result);
        assertEquals("Carlos", result.getNombre());
        verify(estudianteRepository, times(1)).save(newEstudiante);
    }

    @Test
    @DisplayName("should update an existing estudiante")
    void update_updatesExistingEstudiante() {
        // ARRANGE
        Estudiante updatedDetails = new Estudiante();
        updatedDetails.setId(1);
        updatedDetails.setNombre("Ana Actualizada");
        updatedDetails.setApellidos("García Actualizada");
        updatedDetails.setEmail("ana_updated@example.com");
        updatedDetails.setPassword("newpass_ana");

        when(estudianteRepository.save(any(Estudiante.class))).thenReturn(updatedDetails); // Mockear save

        // ACT
        Estudiante result = estudianteService.update(updatedDetails);

        // ASSERT
        assertNotNull(result);
        assertEquals("Ana Actualizada", result.getNombre());
        assertEquals("ana_updated@example.com", result.getEmail());
        verify(estudianteRepository, times(1)).save(updatedDetails);
    }

    @Test
    @DisplayName("should delete an estudiante by ID")
    void deleteById_deletesEstudiante() {
        // ARRANGE
        doNothing().when(estudianteRepository).deleteById(1);

        // ACT
        estudianteService.deleteById(1);

        // ASSERT
        verify(estudianteRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("should return courses by estudiante ID when student found")
    void getCursosByEstudianteId_returnsCourses() {
        // ARRANGE
        estudiante1.setCursoList(Arrays.asList(cursoTest)); // Asignar cursos al estudiante de prueba
        when(estudianteRepository.findByIdWithCursos(1)).thenReturn(Optional.of(estudiante1));

        // ACT
        List<Curso> result = estudianteService.getCursosByEstudianteId(1);

        // ASSERT
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(cursoTest.getNombre(), result.get(0).getNombre());
        verify(estudianteRepository, times(1)).findByIdWithCursos(1);
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when getting courses for non-existent estudiante ID")
    void getCursosByEstudianteId_throwsExceptionWhenNotFound() {
        // ARRANGE
        when(estudianteRepository.findByIdWithCursos(99)).thenReturn(Optional.empty());

        // ACT & ASSERT
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            estudianteService.getCursosByEstudianteId(99);
        });
        assertEquals("Estudiante no encontrado", exception.getMessage());
        verify(estudianteRepository, times(1)).findByIdWithCursos(99);
    }
}