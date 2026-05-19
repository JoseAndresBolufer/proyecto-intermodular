document.addEventListener("DOMContentLoaded", () => {
    configurarFormularios();
    configurarBotonesEliminar();
});

function mostrarMensaje(texto, tipo) {
    let cajaMensaje = document.getElementById("mensaje");

    if (cajaMensaje === null) {
        cajaMensaje = document.createElement("div");
        cajaMensaje.id = "mensaje";

        const main = document.querySelector("main");

        if (main !== null) {
            main.prepend(cajaMensaje);
        } else {
            document.body.prepend(cajaMensaje);
        }
    }

    cajaMensaje.textContent = texto;
    cajaMensaje.className = "mensaje " + tipo;
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

            mostrarMensaje("Formulario validado correctamente. Falta conectar con el backend.", "ok");
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

            mostrarMensaje("Eliminación confirmada. Falta conectar con el backend.", "ok");
        });
    });
}