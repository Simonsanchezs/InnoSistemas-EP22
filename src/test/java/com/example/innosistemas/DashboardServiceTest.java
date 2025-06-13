package com.example.innosistemas;


import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.innosistemas.entity.Dashboard;
import com.example.innosistemas.entity.Equipo;
import com.example.innosistemas.repository.DashboardRepository;
import com.example.innosistemas.repository.EquipoRepository;
import com.example.innosistemas.service.DashboardService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DashboardService Unit Tests - AAA Pattern")
class DashboardServiceTest {

    @Mock
    private DashboardRepository dashboardRepository;

    @Mock
    private EquipoRepository equipoRepository;

    @InjectMocks
    private DashboardService dashboardService;

    private Dashboard dashboard1;
    private Dashboard dashboard2;
    private Equipo equipoTest;

    @BeforeEach
    void setUp() {
        equipoTest = new Equipo();
        equipoTest.setId(101);
        equipoTest.setNombre("Equipo de Prueba");

        dashboard1 = new Dashboard();
        dashboard1.setId(1);
        dashboard1.setTareas_completadas(5);
        dashboard1.setEntregas(3);
        dashboard1.setEstado("En Progreso");
        dashboard1.setEquipo(equipoTest);

        dashboard2 = new Dashboard();
        dashboard2.setId(2);
        dashboard2.setTareas_completadas(10);
        dashboard2.setEntregas(8);
        dashboard2.setEstado("Completado");
        dashboard2.setEquipo(equipoTest);
    }

    @Test
    @DisplayName("should find all dashboards")
    void findAll_returnsAllDashboards() {
        // ARRANGE
        when(dashboardRepository.findAll()).thenReturn(Arrays.asList(dashboard1, dashboard2));

        // ACT
        List<Dashboard> result = dashboardService.findAll();

        // ASSERT
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(dashboard1));
        assertTrue(result.contains(dashboard2));
        verify(dashboardRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("should find dashboard by ID when found")
    void findById_returnsDashboardWhenFound() {
        // ARRANGE
        when(dashboardRepository.findById(1)).thenReturn(Optional.of(dashboard1));

        // ACT
        Dashboard result = dashboardService.findById(1);

        // ASSERT
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(dashboardRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when dashboard by ID not found")
    void findById_throwsExceptionWhenNotFound() {
        // ARRANGE
        when(dashboardRepository.findById(99)).thenReturn(Optional.empty());

        // ACT & ASSERT
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            dashboardService.findById(99);
        });
        assertEquals("Dashboard no encontrado", exception.getMessage());
        verify(dashboardRepository, times(1)).findById(99);
    }

    @Test
    @DisplayName("should save a new dashboard with a valid equipoId")
    void save_savesNewDashboardWithValidEquipo() {
        // ARRANGE
        Dashboard newDashboard = new Dashboard();
        newDashboard.setTareas_completadas(7);
        newDashboard.setEntregas(5);
        newDashboard.setEstado("Pendiente");
        
        when(equipoRepository.findById(equipoTest.getId())).thenReturn(Optional.of(equipoTest));
        when(dashboardRepository.save(any(Dashboard.class))).thenReturn(newDashboard);

        // ACT
        Dashboard result = dashboardService.save(newDashboard, equipoTest.getId());

        // ASSERT
        assertNotNull(result);
        assertEquals(newDashboard.getTareas_completadas(), result.getTareas_completadas());
        assertEquals(newDashboard.getEstado(), result.getEstado());
        assertEquals(equipoTest, result.getEquipo()); // Verificar que el equipo se asignó
        verify(equipoRepository, times(1)).findById(equipoTest.getId());
        verify(dashboardRepository, times(1)).save(newDashboard);
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when saving dashboard with non-existent equipoId")
    void save_throwsExceptionWhenEquipoNotFound() {
        // ARRANGE
        Dashboard newDashboard = new Dashboard();
        int nonExistentEquipoId = 999;
        when(equipoRepository.findById(nonExistentEquipoId)).thenReturn(Optional.empty());

        // ACT & ASSERT
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            dashboardService.save(newDashboard, nonExistentEquipoId);
        });
        assertEquals("Equipo no encontrado", exception.getMessage());
        verify(equipoRepository, times(1)).findById(nonExistentEquipoId);
        verify(dashboardRepository, never()).save(any(Dashboard.class));
    }

    @Test
    @DisplayName("should update an existing dashboard")
    void update_updatesExistingDashboard() {
        // ARRANGE
        Dashboard updatedDetails = new Dashboard();
        updatedDetails.setTareas_completadas(6);
        updatedDetails.setEntregas(4);
        updatedDetails.setEstado("Revisado");

        when(dashboardRepository.findById(1)).thenReturn(Optional.of(dashboard1));
        when(dashboardRepository.save(any(Dashboard.class))).thenReturn(updatedDetails);

        // ACT
        Dashboard result = dashboardService.update(1, updatedDetails);

        // ASSERT
        assertNotNull(result);
        assertEquals("Revisado", result.getEstado());
        verify(dashboardRepository, times(1)).findById(1);
        verify(dashboardRepository, times(1)).save(dashboard1); // Verifica que se llamó save con el objeto actualizado
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when updating non-existent dashboard")
    void update_throwsExceptionWhenNotFound() {
        // ARRANGE
        Dashboard nonExistentDashboard = new Dashboard();
        when(dashboardRepository.findById(99)).thenReturn(Optional.empty());

        // ACT & ASSERT
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            dashboardService.update(99, nonExistentDashboard);
        });
        assertEquals("Dashboard no encontrado", exception.getMessage());
        verify(dashboardRepository, times(1)).findById(99);
        verify(dashboardRepository, never()).save(any(Dashboard.class));
    }

    @Test
@DisplayName("should delete a dashboard by ID")
void delete_deletesDashboard() {
    // ARRANGE
    // No mockees findById si el servicio no lo llama
    doNothing().when(dashboardRepository).deleteById(1); // Mockear que deleteById no lanza excepción

    // ACT
    dashboardService.delete(1);

    // ASSERT
    // No verifiques findById si el servicio no lo llama
    verify(dashboardRepository, times(1)).deleteById(1); // Verifica que deleteById fue llamado
    }
    

    @Test
    @DisplayName("should find dashboards by Equipo ID")
    void findByEquipoId_returnsDashboards() {
        // ARRANGE
        when(equipoRepository.findById(equipoTest.getId())).thenReturn(Optional.of(equipoTest));
        when(dashboardRepository.findByEquipo(equipoTest)).thenReturn(Arrays.asList(dashboard1));

        // ACT
        List<Dashboard> result = dashboardService.findByEquipoId(equipoTest.getId());

        // ASSERT
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(dashboard1));
        verify(equipoRepository, times(1)).findById(equipoTest.getId());
        verify(dashboardRepository, times(1)).findByEquipo(equipoTest);
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when finding dashboards by non-existent Equipo ID")
    void findByEquipoId_throwsExceptionWhenEquipoNotFound() {
        // ARRANGE
        when(equipoRepository.findById(equipoTest.getId())).thenReturn(Optional.empty());

        // ACT & ASSERT
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            dashboardService.findByEquipoId(equipoTest.getId());
        });
        assertEquals("Equipo no encontrado", exception.getMessage());
        verify(equipoRepository, times(1)).findById(equipoTest.getId());
        verify(dashboardRepository, never()).findByEquipo(any(Equipo.class));
    }
}