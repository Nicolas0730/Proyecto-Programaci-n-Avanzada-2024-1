package co.edu.uniquindio.unilocal.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Document("Comentario")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Comentario implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String descripcion;
    private int califacion; //Califican del 1 al 5
    private String idUsuario;
    private String idNegocio;
    private String respuesta;
    private List<String> imagenes; // para la funcionalidad propuesta
    private LocalDateTime fechaComentario;

}
