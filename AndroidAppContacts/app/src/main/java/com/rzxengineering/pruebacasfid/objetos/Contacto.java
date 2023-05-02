package com.rzxengineering.pruebacasfid.objetos;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

public class Contacto implements Serializable {
    private boolean errorServidor;
    private String genero;
    private String titulo;
    private String nombre;
    private String apellidos;
    private String calle;
    private String provincia;
    private String ciudad;
    private String pais;
    private String codigoPostal;
    private ArrayList<String> coordenadas;
    private String zonaHoraria;
    private String descripcionZonaHoraria;
    private String email;
    private String userName;
    private String nacimiento;
    private String edad;
    private String telefonoFijo;
    private String telefonoMovil;
    private String urlImagen;
    public Contacto(boolean errorServidor){
        this.errorServidor=errorServidor;
    }

    public Contacto(String genero, String titulo, String nombre, String apellidos, String calle,
                    String provincia, String ciudad, String pais, String codigoPostal,
                    ArrayList<String> coordenadas, String zonaHoraria, String descripcionZonaHoraria,
                    String email, String userName, String nacimiento, String edad,
                    String telefonoFijo, String telefonoMovil, String urlImagen) {
        this.errorServidor = false;
        this.genero = genero;
        this.titulo = titulo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.calle = calle;
        this.provincia = provincia;
        this.ciudad = ciudad;
        this.pais = pais;
        this.codigoPostal = codigoPostal;
        this.coordenadas = coordenadas;
        this.zonaHoraria = zonaHoraria;
        this.descripcionZonaHoraria = descripcionZonaHoraria;
        this.email = email;
        this.userName = userName;
        this.nacimiento = nacimiento;
        this.edad = edad;
        this.telefonoFijo = telefonoFijo;
        this.telefonoMovil = telefonoMovil;
        this.urlImagen = urlImagen;
    }

    public boolean getErrorServidor() {
        return errorServidor;
    }

    public void setErrorServidor(boolean errorServidor) {
        this.errorServidor = errorServidor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
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

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public ArrayList<String> getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(ArrayList<String> coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getZonaHoraria() {
        return zonaHoraria;
    }

    public void setZonaHoraria(String zonaHoraria) {
        this.zonaHoraria = zonaHoraria;
    }

    public String getDescripcionZonaHoraria() {
        return descripcionZonaHoraria;
    }

    public void setDescripcionZonaHoraria(String descripcionZonaHoraria) {
        this.descripcionZonaHoraria = descripcionZonaHoraria;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    @Override
    public String toString() {
        return "Contacto{" +
                "genero='" + genero + '\'' +
                ", titulo='" + titulo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", calle='" + calle + '\'' +
                ", provincia='" + provincia + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", pais='" + pais + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                ", coordenadas=" + coordenadas +
                ", zonaHoraria='" + zonaHoraria + '\'' +
                ", descripcionZonaHoraria='" + descripcionZonaHoraria + '\'' +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", nacimiento='" + nacimiento + '\'' +
                ", edad='" + edad + '\'' +
                ", telefonoFijo='" + telefonoFijo + '\'' +
                ", telefonoMovil='" + telefonoMovil + '\'' +
                ", urlImagen='" + urlImagen + '\'' +
                '}';
    }
    public String toJSON() {
        return "{" +
                "\"gender\": \"" + genero + '\"' +
                ", \"title\": \"" + titulo + '\"' +
                ", \"firstName\": \"" + nombre + '\"' +
                ", \"lastName\": \"" + apellidos + '\"' +
                ", \"street\": \"" + calle + '\"' +
                ", \"province\": \"" + provincia + '\"' +
                ", \"city\": \"" + ciudad + '\"' +
                ", \"country\": \"" + pais + '\"' +
                ", \"postalCode\": \"" + codigoPostal + '\"' +
                ", \"longitudeCoor\": \"" + coordenadas.get(1) + '\"'+
                ", \"latitudeCoor\": \"" + coordenadas.get(0) + '\"'+
                ", \"timeZone\": \"" + zonaHoraria + '\"' +
                ", \"timeDesc\": \"" + descripcionZonaHoraria + '\"' +
                ", \"email\": \"" + email + '\"' +
                ", \"userName\": \"" + userName + '\"' +
                ", \"birthDay\": \"" + nacimiento + '\"' +
                ", \"age\": \"" + edad + '\"' +
                ", \"landLinePhone\": \"" + telefonoFijo + '\"' +
                ", \"phoneNumber\": \"" + telefonoMovil + '\"' +
                ", \"urlImage\": \"" + urlImagen + '\"' +
                '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Contacto){
            Contacto contacto= (Contacto) obj;
            return genero.equals(contacto.getGenero())&&
                    titulo.equals(contacto.getTitulo())&&
                    nombre.equals(contacto.getNombre())&&
                    apellidos.equals(contacto.getApellidos())&&
                    calle.equals(contacto.getCalle())&&
                    provincia.equals(contacto.getProvincia())&&
                    ciudad.equals(contacto.getCiudad())&&
                    pais.equals(contacto.getPais())&&
                    codigoPostal.equals(contacto.getCodigoPostal())&&
                    coordenadas.get(0).equals(contacto.getCoordenadas().get(0))&&
                    coordenadas.get(1).equals(contacto.getCoordenadas().get(1))&&
                    zonaHoraria.equals(contacto.getZonaHoraria())&&
                    descripcionZonaHoraria.equals(contacto.getDescripcionZonaHoraria())&&
                    email.equals(contacto.getEmail())&&
                    userName.equals(contacto.getUserName())&&
                    nacimiento.equals(contacto.getNacimiento())&&
                    edad.equals(contacto.getEdad())&&
                    telefonoFijo.equals(contacto.getTelefonoFijo())&&
                    telefonoMovil.equals(contacto.getTelefonoMovil())&&
                    urlImagen.equals(contacto.getUrlImagen());
        }
        return false;
    }
}
