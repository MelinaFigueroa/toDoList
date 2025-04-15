package com.alkemy_java.toDoList.repository;

import com.alkemy_java.toDoList.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {

    // Buscar por título y status (solo las no borradas lógicamente)
    List<Tarea> findByTituloContainingAndStatusAndBorradoLogicoFalse(String titulo, Tarea.TareaStatus status);

    // Buscar todas las tareas no borradas lógicamente
    List<Tarea> findByBorradoLogicoFalse();
}