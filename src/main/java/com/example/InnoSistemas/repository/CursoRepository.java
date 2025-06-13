package com.example.innosistemas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.innosistemas.entity.Curso;

import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {

    // Obtener curso con estudiantes (carga eager personalizada)
    @Query("SELECT c FROM Curso c JOIN FETCH c.estudianteList WHERE c.id = :id")
    Optional<Curso> findByIdWithEstudiantes(@Param("id") Integer id);

    // Operación directa en la tabla intermedia
    @Modifying
    @Query(nativeQuery = true,
            value = "INSERT INTO estudiante_curso (curso_id, estudiante_id) VALUES (:cursoId, :estudianteId)")
    void addEstudianteToCurso(@Param("cursoId") Integer cursoId,
                              @Param("estudianteId") Integer estudianteId);

    // Operación directa en la tabla intermedia
    @Modifying
    @Query(nativeQuery = true,
            value = "DELETE FROM estudiante_curso WHERE curso_id = :cursoId AND estudiante_id = :estudianteId")
    void removeEstudianteFromCurso(@Param("cursoId") Integer cursoId,
                                   @Param("estudianteId") Integer estudianteId);
}

