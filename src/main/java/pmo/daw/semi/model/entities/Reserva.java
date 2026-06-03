package pmo.daw.semi.model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Reserva {

    private Integer id;
    private Integer idUsuario;
    private Integer idDestino;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(Integer idDestino) {
        this.idDestino = idDestino;
    }

    @Override
    public String toString() {
        return "Reserva [id=" + id + ", idUsuario=" + idUsuario + ", idDestino=" + idDestino + "]";
    }

    public static Reserva mapResultSet(ResultSet rs) throws SQLException {
        return mapResultSet(rs, "");
    }

    public static Reserva mapResultSet(ResultSet rs, String alias) throws SQLException {
        Reserva reserva = new Reserva();

        reserva.setId(rs.getInt(alias + "id"));
        reserva.setIdUsuario(rs.getObject(alias + "id_usuario", Integer.class));
        reserva.setIdDestino(rs.getObject(alias + "id_destino", Integer.class));

        return reserva;
    }
}