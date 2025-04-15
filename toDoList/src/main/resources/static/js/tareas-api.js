// js/tareas-api.js
const API_URL = "http://localhost:8080/api/tareas";

export async function obtenerTareas() {
  const res = await fetch(API_URL);
  return await res.json();
}

export async function obtenerTareaPorId(id) {
  const res = await fetch(`${API_URL}/${id}`);
  return await res.json();
}

export async function crearTarea(tarea) {
  const res = await fetch(API_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(tarea)
  });
  return await res.json();
}

export async function actualizarTarea(id, tarea) {
  const res = await fetch(`${API_URL}/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(tarea)
  });
  return await res.json();
}

export async function eliminarTarea(id) {
  const res = await fetch(`${API_URL}/${id}`, {
    method: "DELETE"
  });
  return res.ok;
}
