package es.resisg.prometeoapp3.clases;

import java.util.Objects;

public class Cuenta {
    //Atributos
    int usuario;
    String contrasena,nombre;
    //constructor
    public Cuenta(int usuario, String contrasena, String nombre) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombre = nombre;
    }

    //getters and setters
    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //Equals and hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cuenta)) return false;
        Cuenta cuenta = (Cuenta) o;
        return getUsuario() == cuenta.getUsuario();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsuario());
    }
}
