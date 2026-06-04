package pmo.daw.semi.model.entities;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Destino {

    private Integer id;
    private String ciudad;
    private String pais;
    private BigDecimal precio;
    private Boolean requierePasaporte;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Boolean getRequierePasaporte() {
        return requierePasaporte;
    }

    public void setRequierePasaporte(Boolean requierePasaporte) {
        this.requierePasaporte = requierePasaporte;
    }

    @Override
    public String toString() {
        return "Destino [id=" + id + ", ciudad=" + ciudad + ", pais=" + pais + ", precio=" + precio
                + ", requierePasaporte=" + requierePasaporte + "]";
    }

    public static Destino mapResultSet(ResultSet rs) throws SQLException {
        return mapResultSet(rs, "");
    }

    public static Destino mapResultSet(ResultSet rs, String alias) throws SQLException {
        Destino destino = new Destino();

        destino.setId(rs.getInt(alias + "id"));
        destino.setCiudad(rs.getString(alias + "ciudad"));
        destino.setPais(rs.getString(alias + "pais"));
        destino.setPrecio(rs.getBigDecimal(alias + "precio"));
        destino.setRequierePasaporte(rs.getObject(alias + "requiere_pasaporte", Boolean.class));

        return destino;
    }
}