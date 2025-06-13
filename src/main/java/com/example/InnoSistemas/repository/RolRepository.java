package com.example.innosistemas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.innosistemas.entity.Rol;


@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
}
