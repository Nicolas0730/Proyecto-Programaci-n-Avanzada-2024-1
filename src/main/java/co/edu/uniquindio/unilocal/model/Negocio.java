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
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Negocio implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String nombre;
    private String descripcion;
    private List<String> listaImagenes;
    private List<String> listaTelefonos;
    private Ubicacion ubicacion;
    private String idUsuario;
    private HorarioNegocio horario;
    private TipoNegocio tipoNegocio;
    private List<HistorialNegocio> historialNegocio;
    private EstadoRegistro estadoRegistro;
    private Ciudad ciudad;
    private String direccion;

//    @Builder
//    public Negocio(String nombre,String descripcion,List<String> listaImagenes,List<String> listaTelefonos,
//                   Ubicacion ubicacion,String idUsuario,HorarioNegocio horario,TipoNegocio tipoNegocio,List<HistorialNegocio> historialNegocio,
//                   EstadoRegistro estadoRegistro,Ciudad ciudad,String direccion){
//        //super(nombre,descripcion,listaImagenes,listaTelefonos,ubicacion,idUsuario,horario,tipoNegocio,historialNegocio,estadoRegistro,ciudad,direccion);
//        this.nombre=nombre;
//        this.descripcion=descripcion;
//        this.listaImagenes=listaImagenes;
//        this.listaTelefonos=listaTelefonos;
//        this.ubicacion=ubicacion;
//        this.idUsuario=idUsuario;
//        this.horario=horario;
//        this.tipoNegocio=tipoNegocio;
//        this.historialNegocio=historialNegocio;
//        this.estadoRegistro=estadoRegistro;
//        this.ciudad=ciudad;
//        this.direccion=direccion;
//    }

}
