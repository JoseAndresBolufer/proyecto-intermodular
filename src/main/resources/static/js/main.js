document.addEventListener("DOMContentLoaded", () => {
    configurarFormularios();
    configurarBotonesEliminar();
    configurarBuscadores();
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
  
  function configurarFormularios() {
    const formularios = document.querySelectorAll("form");
  
    formularios.forEach((formulario) => {
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