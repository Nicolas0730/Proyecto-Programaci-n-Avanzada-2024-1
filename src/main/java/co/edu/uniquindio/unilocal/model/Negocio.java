package co.edu.uniquindio.unilocal.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document("Negocio")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Negocio implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private Integer id;
    private String nombre;
    private String descripcion;
    private List<String> listaImagenes;
    private List<String> listaTelefonos;
    private Ubicacion ubicacion;
    private Usuario idUsuario;
    private HorarioNegocio horario;
    private TipoNegocio tipoNegocio;
    private List<HistorialNegocio> historialNegocio;
    private EstadoRegistro estadoRegistro;
    private Ciudad ciudad;
    private String direccion;



}
