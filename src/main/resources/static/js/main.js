const API_URL = "http://localhost:8080/api";

document.addEventListener("DOMContentLoaded", () => {
    configurarFormularios();
    configurarBotonesEliminar();
    configurarBuscadores();
    cargarDestinos();
    cargarUsuarios();
    cargarReservas();
    configurarFormularioReserva(); 
});
  
  function mostrarMensaje(texto, tipo = "info") {
    let cajaMensaje = document.getElementById("mensaje");
  
    if (!cajaMensaje) {
      cajaMensaje = document.createElement("div");
      cajaMensaje.id = "mensaje";
  
      const main = document.querySelector("main");
      main ? main.prepend(cajaMensaje) : document.body.prepend(cajaMensaje);
    }
  
    cajaMensaje.textContent = texto;
    cajaMensaje.className = `mensaje ${tipo}`;
  }

  async function obtenerDatos(ruta) {
    try {
        const respuesta = await fetch(`${API_URL}${ruta}`);

        if (!respuesta.ok) {
            throw new Error("Error al obtener datos del servidor.");
        }

        return await respuesta.json();

    } catch (error) {
        console.error(error);
        mostrarMensaje("No se pudieron cargar los datos del servidor.", "error");
        return [];
    }
}
  
  function configurarFormularios() {
    const formularios = document.querySelectorAll("form");
  
    formularios.forEach((formulario) => {
    if (formulario.hasAttribute("data-form-reserva")) return;

      formulario.addEventListener("submit", (evento) => {
          evento.preventDefault();

          if (!formulario.checkValidity()) {
              mostrarMensaje("Revisa los campos del formulario antes de continuar.", "error");
              formulario.reportValidity();
              return;
          }

          const datos = Object.fromEntries(new FormData(formulario));
          console.log("Datos del formulario:", datos);

          mostrarMensaje("Registro guardado correctamente.", "ok");
          formulario.reset();
      });
    });
  }
  
  function configurarBotonesEliminar() {
    const botonesEliminar = document.querySelectorAll(".btn-eliminar, [data-eliminar]");
  
    botonesEliminar.forEach((boton) => {
      boton.addEventListener("click", (evento) => {
        const confirmado = confirm("¿Seguro que quieres eliminar este registro?");
  
        if (!confirmado) {
          evento.preventDefault();
          mostrarMensaje("Eliminación cancelada.", "info");
          return;
        }
  
        const fila = boton.closest("tr");
  
        if (fila) {
          fila.remove();
        }
  
        mostrarMensaje("Registro eliminado correctamente.", "ok");
      });
    });
  }
  
  function configurarBuscadores() {
    const buscadores = document.querySelectorAll("[data-buscar]");
  
    buscadores.forEach((buscador) => {
      buscador.addEventListener("input", () => {
        const texto = buscador.value.toLowerCase();
        const tablaSelector = buscador.dataset.buscar;
        const tabla = document.querySelector(tablaSelector);
  
        if (!tabla) return;
  
        const filas = tabla.querySelectorAll("tbody tr");
  
        filas.forEach((fila) => {
          const contenido = fila.textContent.toLowerCase();
          fila.style.display = contenido.includes(texto) ? "" : "none";
        });
      });
    });
  }

  async function cargarDestinos() {
    const selectDestino = document.querySelector("[data-select-destinos]");

    if (!selectDestino) return;

    const destinos = await obtenerDatos("/destino");

    selectDestino.innerHTML = '<option value="">Selecciona un destino</option>';

    destinos.forEach((destino) => {
        const option = document.createElement("option");
        option.value = destino.id;
        option.textContent = `${destino.ciudad} - ${destino.pais}`;
        selectDestino.appendChild(option);
    });
}

function configurarFormularioReserva() {
    const formularioReserva = document.querySelector("[data-form-reserva]");

    if (!formularioReserva) return;

    formularioReserva.addEventListener("submit", async (evento) => {
        evento.preventDefault();

        if (!formularioReserva.checkValidity()) {
            mostrarMensaje("Selecciona usuario y destino antes de continuar.", "error");
            formularioReserva.reportValidity();
            return;
        }

        const datos = Object.fromEntries(new FormData(formularioReserva));

        const reserva = {
            idUsuario: Number(datos.idUsuario),
            idDestino: Number(datos.idDestino)
        };

        try {
            const respuesta = await fetch(`${API_URL}/reserva`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(reserva)
            });

            if (!respuesta.ok) {
                throw new Error("No se pudo crear la reserva.");
            }

            mostrarMensaje("Reserva creada correctamente.", "ok");
            formularioReserva.reset();
            cargarReservas();

        } catch (error) {
            console.error(error);
            mostrarMensaje("No se pudo crear la reserva. Revisa los datos.", "error");
        }
    });
}


async function cargarUsuarios() {
    const selectUsuario = document.querySelector("[data-select-usuarios]");

    if (!selectUsuario) return;

    const usuarios = await obtenerDatos("/usuario");

    selectUsuario.innerHTML = '<option value="">Selecciona un usuario</option>';

    usuarios.forEach((usuario) => {
        const option = document.createElement("option");
        option.value = usuario.id;
        option.textContent = `${usuario.nombre} ${usuario.apellidos}`;
        selectUsuario.appendChild(option);
    });
}

async function cargarReservas() {
    const tablaReservas = document.querySelector("[data-tabla-reservas]");

    if (!tablaReservas) return;

    const reservas = await obtenerDatos("/reserva");
    const usuarios = await obtenerDatos("/usuario");
    const destinos = await obtenerDatos("/destino");

    tablaReservas.innerHTML = "";

    if (reservas.length === 0) {
        tablaReservas.innerHTML = `
            <tr>
                <td colspan="4">No hay inscripciones para mostrar.</td>
            </tr>
        `;
        return;
    }

    reservas.forEach((reserva) => {
        const usuario = usuarios.find((usuario) => usuario.id === reserva.idUsuario);
        const destino = destinos.find((destino) => destino.id === reserva.idDestino);

        const fila = document.createElement("tr");

        fila.innerHTML = `
            <td>${usuario ? usuario.nombre + " " + usuario.apellidos : "Usuario no encontrado"}</td>
            <td>${destino ? destino.ciudad + " - " + destino.pais : "Destino no encontrado"}</td>
            <td>${destino && destino.requierePasaporte ? "Sí" : "No"}</td>
            <td>Inscrito</td>
        `;

        tablaReservas.appendChild(fila);
    });
}