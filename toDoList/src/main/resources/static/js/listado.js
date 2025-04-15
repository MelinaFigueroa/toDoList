// Obtener todas las tareas desde el backend
async function obtenerTareas() {
  const response = await fetch("http://localhost:8080/api/tareas");
  if (!response.ok) {
    console.error("Error al cargar tareas");
    return [];
  }
  return await response.json();
}

// Buscar tareas por título
async function buscarTareas(titulo) {
  const response = await fetch(`http://localhost:8080/api/tareas/buscar?titulo=${encodeURIComponent(titulo)}`);
  if (!response.ok) {
    console.error("Error al buscar tareas");
    return [];
  }
  return await response.json();
}

// Eliminar una tarea por ID
async function eliminarTarea(id) {
  const response = await fetch(`http://localhost:8080/api/tareas/${id}`, {
    method: "DELETE"
  });
  if (!response.ok) {
    console.error("Error al eliminar tarea");
  }
}

// Obtener referencia al tbody de la tabla
const lista = document.getElementById("tablaTareas");

// Renderizar una lista de tareas
function renderizarTareas(tareas) {
  lista.innerHTML = '';

  tareas.forEach(t => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td class="px-4 py-2">${t.titulo}</td>
      <td class="px-4 py-2">${t.status}</td>
      <td class="px-4 py-2">
        <a href="formulario.html?id=${t.id}" class="text-blue-600 hover:underline mr-4">
          <i class="fas fa-edit"></i> Editar
        </a>
        <button data-id="${t.id}" class="text-red-600 hover:underline btnEliminar">
          <i class="fas fa-trash-alt"></i> Eliminar
        </button>
      </td>
    `;
    lista.appendChild(tr);
  });

  // Botones de eliminar
  document.querySelectorAll('.btnEliminar').forEach(btn => {
    btn.addEventListener('click', async () => {
      const confirmar = confirm("¿Eliminar esta tarea?");
      if (confirmar) {
        const id = btn.dataset.id;
        await eliminarTarea(id);
        cargarTareas(); // refresca la lista
      }
    });
  });
}

// Cargar todas las tareas al iniciar
async function cargarTareas() {
  const tareas = await obtenerTareas();
  console.log("Tareas obtenidas:", tareas);
  renderizarTareas(tareas);
}

// Buscar tareas al escribir en el input
const buscador = document.getElementById("busquedaTitulo");
buscador.addEventListener("keyup", async () => {
  const valor = buscador.value.trim();

  if (valor === "") {
    cargarTareas(); // si está vacío, mostrar todas
  } else {
    const tareasFiltradas = await buscarTareas(valor);
    renderizarTareas(tareasFiltradas);
  }
});

// Ejecutar al cargar
cargarTareas();
