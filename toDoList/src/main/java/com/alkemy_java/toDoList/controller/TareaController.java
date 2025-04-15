package com.alkemy_java.toDoList.controller;

import com.alkemy_java.toDoList.model.Tarea;
import com.alkemy_java.toDoList.service.TareaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @GetMapping("/")
    public String mostrarIndex() {
        return "index"; // Esto busca 'templates/index.html'
    }

    // Obtener todas las tareas
    @GetMapping
    public ResponseEntity<List<Tarea>> obtenerTodasLasTareas() {
        return ResponseEntity.ok(tareaService.obtenerTodasLasTareas());
    }

    // Obtener una tarea por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerTareaPorId(@PathVariable Long id) {
        return tareaService.obtenerTareaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva tarea
    @PostMapping
    public ResponseEntity<Tarea> crearTarea(@Valid @RequestBody Tarea tarea) {
        Tarea nuevaTarea = tareaService.guardarTarea(tarea);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTarea);
    }

    // Actualizar una tarea existente
    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable Long id, @Valid @RequestBody Tarea tarea) {
        Tarea tareaActualizada = tareaService.actualizarTarea(id, tarea);
        return tareaActualizada != null ?
                ResponseEntity.ok(tareaActualizada) :
                ResponseEntity.notFound().build();
    }

    // Eliminar una tarea (borrado lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
        boolean eliminado = tareaService.eliminarTarea(id);
        return eliminado ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

    // Buscar tareas por título y estado
    @GetMapping("/buscar")
    public ResponseEntity<List<Tarea>> buscarTareas(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Tarea.TareaStatus status) {
        if (titulo != null && status != null) {
            return ResponseEntity.ok(tareaService.buscarTareasPorTituloYEstado(titulo, status));
        }
        return ResponseEntity.ok(tareaService.obtenerTodasLasTareas());
    }
}