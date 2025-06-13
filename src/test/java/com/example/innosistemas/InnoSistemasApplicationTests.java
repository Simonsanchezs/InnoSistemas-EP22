package com.example.innosistemas;

import com.example.innosistemas.repository.CursoRepository;
import com.example.innosistemas.repository.EstudianteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
class InnoSistemasApplicationTests {

    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;

    @Autowired
    InnoSistemasApplicationTests(EstudianteRepository estudianteRepository, CursoRepository cursoRepository) {
        this.estudianteRepository = estudianteRepository;
        this.cursoRepository = cursoRepository;
    }

    @Test
    void contextLoads() {
        
        assertNotNull(estudianteRepository);
        assertNotNull(cursoRepository);

        System.out.println("Contexto de Spring cargado exitosamente con H2!");
    }
}