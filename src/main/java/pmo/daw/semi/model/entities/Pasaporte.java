package pmo.daw.semi.model.entities;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Pasaporte {
    private Integer id;
    private String numero;
    private String paisExpedicion;
    private LocalDate fechaCaducidad;
    private Integer idUsuario;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    @JsonIgnore
    public String getNumeroSafe() { return numero == null ? "" : numero; }
    public void setNumeroSafe(String numero) { this.numero = numero == null ? "" : numero; }

    public String getPaisExpedicion() { return paisExpedicion; }
    public void setPaisExpedicion(String paisExpedicion) { this.paisExpedicion = paisExpedicion; }
    @JsonIgnore
    public String getPaisExpedicionSafe() { return paisExpedicion == null ? "" : paisExpedicion; }
    public void setPaisExpedicionSafe(String paisExpedicion) { this.paisExpedicion = paisExpedicion == null ? "" : paisExpedicion; }

    public LocalDate getFechaCaducidad() { return fechaCaducidad; }
    public void setFechaCaducidad(LocalDate fechaCaducidad) { this.fechaCaducidad = fechaCaducidad; }
    @JsonIgnore
    public Date getFechaCaducidadDate() { return fechaCaducidad == null ? null : java.sql.Date.valueOf(fechaCaducidad); }
    public void setFechaCaducidadDate(Date fechaCaducidad) { this.fechaCaducidad = fechaCaducidad == null ? null : fechaCaducidad.toLocalDate(); }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
	@Override
	public String toString() {
		return "Pasaporte [id=" + id + ", numero=" + numero + ", paisExpedicion=" + paisExpedicion + ", fechaCaducidad="
				+ fechaCaducidad + ", idUsuario=" + idUsuario + "]";
	}

    /**
     * Mapea un ResultSet a un objeto Pasaporte.
     * @param rs ResultSet con la fila de usuario
     * @return Pasaporte mapeado
     * @throws SQLException si ocurre un error de SQL
     */
	public static Pasaporte mapResultSet(ResultSet rs) throws SQLException {
		return mapResultSet(rs, "");
	}

	public static Pasaporte mapResultSet(ResultSet rs, String alias) throws SQLException {
		Pasaporte pasaporte = new Pasaporte();
		pasaporte.setId(rs.getInt(alias + "id"));
		pasaporte.setFechaCaducidadDate(rs.getDate(alias + "fecha_caducidad"));
		pasaporte.setNumeroSafe(rs.getString(alias + "numero")); // Manejo seguro de null
		pasaporte.setPaisExpedicionSafe(rs.getString(alias + "pais_expedicion"));
		pasaporte.setIdUsuario(rs.getObject(alias + "id_usuario", Integer.class)); // soporta null
		return pasaporte;
	}
}