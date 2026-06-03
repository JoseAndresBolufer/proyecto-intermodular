package pmo.daw.semi.model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Guia {

    private Integer id;
    private String nombre;
    private String apellidos;
    private String especialidad;
    private Integer idDestino;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Integer getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(Integer idDestino) {
        this.idDestino = idDestino;
    }

    @Override
    public String toString() {
        return "Guia [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos
                + ", especialidad=" + especialidad + ", idDestino=" + idDestino + "]";
    }

    public static Guia mapResultSet(ResultSet rs) throws SQLException {
        return mapResultSet(rs, "");
    }

    public static Guia mapResultSet(ResultSet rs, String alias) throws SQLException {
        Guia guia = new Guia();

        guia.setId(rs.getInt(alias + "id"));
        guia.setNombre(rs.getString(alias + "nombre"));
        guia.setApellidos(rs.getString(alias + "apellidos"));
        guia.setEspecialidad(rs.getString(alias + "especialidad"));
        guia.setIdDestino(rs.getObject(alias + "id_destino", Integer.class));

        return guia;
    }
}