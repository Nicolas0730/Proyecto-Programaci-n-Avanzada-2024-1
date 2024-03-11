package co.edu.uniquindio.unilocal.model.documentos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
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
    private Integer id;
    private String descripcion;
    private Integer califacion; //Califican del 1 al 5
    private Usuario idUsuario;
    private Negocio idNegocio;
    private String respuesta;
    private List<String> imagenes; // para la funcionalidad propuesta

}
