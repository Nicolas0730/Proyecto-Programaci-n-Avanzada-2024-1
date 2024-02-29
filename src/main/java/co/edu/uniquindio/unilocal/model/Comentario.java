package co.edu.uniquindio.unilocal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("Comentario")
public class Comentario {

    @Id
    private Integer id;
    private String descripcion;
    private Integer califacion; //Califican del 1 al 5
    private Usuario idUsuario;
    private Negocio idNegocio;
    private String respuesta;
    private List<String> imagenes; // para la funcionalidad propuesta

}
