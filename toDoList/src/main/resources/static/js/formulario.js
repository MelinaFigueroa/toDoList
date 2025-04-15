// js/formulario.js
import { crearTarea, obtenerTareaPorId, actualizarTarea } from './tareas-api.js';

const form = document.getElementById('formTarea');
const params = new URLSearchParams(window.location.search);
const tareaId = params.get("id");

if (tareaId) {
  // Precargar datos si es edición
  obtenerTareaPorId(tareaId).then(tarea => {
    form.titulo.value = tarea.titulo;
    form.descripcion.value = tarea.descripcion;
    form.status.value = tarea.status;
  });
}

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const tarea = {
    titulo: form.titulo.value,
    descripcion: form.descripcion.value,
    status: form.status.value,
    fecha_creacion: new Date(),        // Puede ser omitido si el backend lo genera
    borrado_logico: false              // Puede omitirse si es default en el backend
  };

  if (tareaId) {
    await actualizarTarea(tareaId, tarea);
    alert("Tarea actualizada correctamente");
  } else {
    await crearTarea(tarea);
    alert("Tarea creada exitosamente");
  }

  window.location.href = "listado.html";
});
