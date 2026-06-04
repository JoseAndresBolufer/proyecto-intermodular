const API_URL = "http://localhost:8080/api";

document.addEventListener("DOMContentLoaded", () => {
    configurarFormularios();
    configurarBotonesEliminar();
    configurarBuscadores();

    cargarDestinos();
    cargarUsuarios();
    cargarReservas();
    cargarListadoDestinos();
    cargarListadoUsuarios();
    cargarListadoGuias();

    configurarFormularioReserva();
    configurarFormularioDestino();
    configurarFormularioUsuario();
    configurarFormularioGuia();
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
    if (
    formulario.hasAttribute("data-form-reserva") ||
    formulario.hasAttribute("data-form-destino") ||
    formulario.hasAttribute("data-form-usuario") ||
    formulario.hasAttribute("data-form-guia")
    ) return;

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

async function cargarListadoDestinos() {
    const contenedorDestinos = document.querySelector("[data-listado-destinos]");

    if (!contenedorDestinos) return;

    const destinos = await obtenerDatos("/destino");

    contenedorDestinos.innerHTML = "";

    if (destinos.length === 0) {
        contenedorDestinos.innerHTML = "<p>No hay destinos para mostrar.</p>";
        return;
    }

    destinos.forEach((destino) => {
        const tarjeta = document.createElement("article");
        tarjeta.className = "tarjeta-destino";

        tarjeta.innerHTML = `
            <h2>${destino.ciudad}</h2>
            <p class="pais-destino">${destino.pais}</p>

            <p>
                <strong>Desde:</strong> ${destino.precio} €
            </p>

            <p>
                <strong>Pasaporte:</strong> ${destino.requierePasaporte ? "Requerido" : "No requerido"}
            </p>

            <button onclick="location.href='detalle-destino.html?id=${destino.id}'">
                + info
            </button>
        `;

        contenedorDestinos.appendChild(tarjeta);
    });
}

async function cargarListadoUsuarios() {
    const tablaUsuarios = document.querySelector("[data-tabla-usuarios]");

    if (!tablaUsuarios) return;

    const usuarios = await obtenerDatos("/usuario");

    tablaUsuarios.innerHTML = "";

    if (usuarios.length === 0) {
        tablaUsuarios.innerHTML = `
            <tr>
                <td colspan="5">No hay usuarios para mostrar.</td>
            </tr>
        `;
        return;
    }

    usuarios.forEach((usuario) => {
        const fila = document.createElement("tr");

        fila.innerHTML = `
            <td>${usuario.nombre} ${usuario.apellidos}</td>
            <td>${usuario.email}</td>
            <td>${usuario.telefono}</td>
            <td>${usuario.pasaporte ? "Sí" : "No"}</td>
            <td>
                <button onclick="location.href='detalle-usuario.html?id=${usuario.id}'">Ver más</button>
                <button onclick="location.href='modificar-usuario.html?id=${usuario.id}'">Modificar</button>
            </td>
        `;

        tablaUsuarios.appendChild(fila);
    });
}

async function cargarListadoGuias() {
    const tablaGuias = document.querySelector("[data-tabla-guias]");

    if (!tablaGuias) return;

    const guias = await obtenerDatos("/guia");
    const destinos = await obtenerDatos("/destino");

    tablaGuias.innerHTML = "";

    if (guias.length === 0) {
        tablaGuias.innerHTML = `
            <tr>
                <td colspan="5">No hay guías para mostrar.</td>
            </tr>
        `;
        return;
    }

    guias.forEach((guia) => {
        const destino = destinos.find((destino) => destino.id === guia.idDestino);
        const fila = document.createElement("tr");

        fila.innerHTML = `
            <td>${guia.nombre} ${guia.apellidos}</td>
            <td>${guia.especialidad}</td>
            <td>No indicado</td>
            <td>${destino ? destino.ciudad : "Sin destino"}</td>
            <td>
                <button onclick="location.href='detalle-guia.html?id=${guia.id}'">Ver más</button>
                <button onclick="location.href='modificar-guia.html?id=${guia.id}'">Modificar</button>
            </td>
        `;

        tablaGuias.appendChild(fila);
    });
}

function configurarFormularioDestino() {
    const formularioDestino = document.querySelector("[data-form-destino]");

    if (!formularioDestino) return;

    formularioDestino.addEventListener("submit", async (evento) => {
        evento.preventDefault();

        if (!formularioDestino.checkValidity()) {
            mostrarMensaje("Revisa los campos del destino antes de continuar.", "error");
            formularioDestino.reportValidity();
            return;
        }

        const datos = Object.fromEntries(new FormData(formularioDestino));

        const destino = {
            ciudad: datos.ciudad,
            pais: datos.pais,
            precio: Number(datos.precio),
            requierePasaporte: datos.requierePasaporte === "true"
        };

        try {
            const respuesta = await fetch(`${API_URL}/destino`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(destino)
            });

            if (!respuesta.ok) {
                throw new Error("No se pudo crear el destino.");
            }

            mostrarMensaje("Destino creado correctamente.", "ok");
            formularioDestino.reset();

        } catch (error) {
            console.error(error);
            mostrarMensaje("No se pudo crear el destino. Revisa los datos.", "error");
        }
    });
}

function configurarFormularioUsuario() {
    const formularioUsuario = document.querySelector("[data-form-usuario]");

    if (!formularioUsuario) return;

    formularioUsuario.addEventListener("submit", async (evento) => {
        evento.preventDefault();

        if (!formularioUsuario.checkValidity()) {
            mostrarMensaje("Revisa los campos del usuario antes de continuar.", "error");
            formularioUsuario.reportValidity();
            return;
        }

        const datos = Object.fromEntries(new FormData(formularioUsuario));

        const usuario = {
            nombre: datos.nombre,
            apellidos: datos.apellidos,
            email: datos.email,
            telefono: datos.telefono,
            fechaNacimiento: datos.fechaNacimiento
        };

        try {
            const respuesta = await fetch(`${API_URL}/usuario`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(usuario)
            });

            if (!respuesta.ok) {
                throw new Error("No se pudo crear el usuario.");
            }

            mostrarMensaje("Usuario creado correctamente.", "ok");
            formularioUsuario.reset();

        } catch (error) {
            console.error(error);
            mostrarMensaje("No se pudo crear el usuario. Revisa los datos.", "error");
        }
    });
}

function configurarFormularioGuia() {
    const formularioGuia = document.querySelector("[data-form-guia]");

    if (!formularioGuia) return;

    formularioGuia.addEventListener("submit", async (evento) => {
        evento.preventDefault();

        if (!formularioGuia.checkValidity()) {
            mostrarMensaje("Revisa los campos del guía antes de continuar.", "error");
            formularioGuia.reportValidity();
            return;
        }

        const datos = Object.fromEntries(new FormData(formularioGuia));

        const guia = {
            nombre: datos.nombre,
            apellidos: datos.apellidos,
            especialidad: datos.especialidad,
            idDestino: Number(datos.idDestino)
        };

        try {
            const respuesta = await fetch(`${API_URL}/guia`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(guia)
            });

            if (!respuesta.ok) {
                throw new Error("No se pudo crear el guía.");
            }

            mostrarMensaje("Guía creado correctamente.", "ok");
            formularioGuia.reset();
            cargarDestinos();

        } catch (error) {
            console.error(error);
            mostrarMensaje("No se pudo crear el guía. Revisa los datos.", "error");
        }
    });
}