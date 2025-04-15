package com.alkemy_java.toDoList.service;

import com.alkemy_java.toDoList.model.Tarea;
import com.alkemy_java.toDoList.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    // Obtener todas las tareas (no borradas lógicamente)
    public List<Tarea> obtenerTodasLasTareas() {
        return tareaRepository.findByBorradoLogicoFalse();
    }

    // Obtener una tarea por su ID
    public Optional<Tarea> obtenerTareaPorId(Long id) {
        return tareaRepository.findById(id).filter(tarea -> !tarea.getBorradoLogico());
    }

    // Guardar una nueva tarea
    @Transactional
    public Tarea guardarTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    // Actualizar una tarea existente
    @Transactional
    public Tarea actualizarTarea(Long id, Tarea tareaActualizada) {
        return tareaRepository.findById(id)
                .filter(tarea -> !tarea.getBorradoLogico())
                .map(tarea -> {
                    tarea.setTitulo(tareaActualizada.getTitulo());
                    tarea.setDescripcion(tareaActualizada.getDescripcion());
                    tarea.setStatus(tareaActualizada.getStatus());
                    return tareaRepository.save(tarea);
                })
                .orElse(null);
    }
    @Transactional
    // Eliminar una tarea (borrado lógico)
    public boolean eliminarTarea(Long id) {
        return tareaRepository.findById(id)
                .filter(tarea -> !tarea.getBorradoLogico())
                .map(tarea -> {
                    tarea.setBorradoLogico(true);
                    tareaRepository.save(tarea);
                    return true;
                })
                .orElse(false);
    }

    // Buscar tareas por título y estado
    public List<Tarea> buscarTareasPorTituloYEstado(String titulo, Tarea.TareaStatus status) {
        return tareaRepository.findByTituloContainingAndStatusAndBorradoLogicoFalse(titulo, status);
    }
}