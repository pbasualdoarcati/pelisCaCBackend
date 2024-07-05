package com.peliscacbackend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pelicula {

    private Integer idPelicula; /* privado es el tipo de encapsulamiento */
    private String titulo;
    private String duracion;
    private String imagen;
    private String synopsis;
    private Integer idActor;
    private Integer idDirector;
    private Integer idGenero;

    // Default constructor
    public Pelicula() {
    }

    // metodo CONSTRUCTOR
    public Pelicula(Integer idPelicula, String titulo, String duracion, String imagen, String synopsis, Integer idActor,
            Integer idDirector, Integer idGenero) {

        this.idPelicula = idPelicula;
        this.titulo = titulo;
        this.duracion = duracion;
        this.imagen = imagen;
        this.synopsis = synopsis;
        this.idActor = idActor;
        this.idDirector = idDirector;
        this.idGenero = idGenero;
    }

    public Integer getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Integer idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Integer getIdActor() {
        return idActor;
    }

    public void setIdActor(Integer idActor) {
        this.idActor = idActor;
    }

    public Integer getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(Integer idDirector) {
        this.idDirector = idDirector;
    }

    public Integer getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Integer idGenero) {
        this.idGenero = idGenero;
    }

}
