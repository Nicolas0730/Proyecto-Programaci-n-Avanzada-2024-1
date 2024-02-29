package co.edu.uniquindio.unilocal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("Negocio")
public class Negocio {

    @Id
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
    private EstadoNegocio estadoNegocio;
    private Ciudad ciudad;
    private String direccion;



}
