package co.edu.uniquindio.unilocal.controladores;

import co.edu.uniquindio.unilocal.dto.MensajeDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.*;
import co.edu.uniquindio.unilocal.model.EstadoNegocio;
import co.edu.uniquindio.unilocal.model.TipoNegocio;
import co.edu.uniquindio.unilocal.servicios.interfaces.NegocioServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/negocio")
@RequiredArgsConstructor
public class NegocioControlador {

    private final NegocioServicio negocioServicio;

    @PostMapping("/registrar-negocio")
    public ResponseEntity<MensajeDTO<String>> crearNegocio(@Valid @RequestBody RegistroNegocioDTO registroNegocioDTO) throws Exception{
        negocioServicio.crearNegocio(registroNegocioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"El negocio fue creado correctamente."));
    }

    @GetMapping("/buscar-negocio/{idNegocio}")
    public ResponseEntity<MensajeDTO<DetalleNegocioDTO>> buscarNegocio(@PathVariable String idNegocio,@PathVariable String idUsuario) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,negocioServicio.buscarNegocio(idNegocio,idUsuario)));
    }
    @DeleteMapping("/eliminar-negocio/{idNegocio}")
    public ResponseEntity<MensajeDTO<String>> eliminarNegocio(@PathVariable String idNegocio) throws Exception{
        negocioServicio.eliminarNegocio(idNegocio);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Negocio eliminado correctamente."));
    }

    //-------------- Estos metodos se ubicarian en Moderador servicio?
    @PutMapping("/aprobar-negocio")
    public ResponseEntity<MensajeDTO<String>> aprobarNegocio(@Valid @RequestBody RegistroRevisionDTO negocioDTO) throws Exception{
        negocioServicio.aprobarNegocio(negocioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Negocio aprobado con éxito."));
    }
    @PutMapping("/rechazar-negocio-propio")
    public ResponseEntity<MensajeDTO<String>> rechazarNegocio(@Valid @RequestBody RegistroRevisionDTO negocioDTO) throws Exception{
        negocioServicio.rechazarNegocio(negocioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Negocio rechazado con éxito."));
    }
    @PutMapping("/editar-negocio")
    public ResponseEntity<MensajeDTO<String>> actualizarNegocio(@Valid @RequestBody ActualizarNegocioDTO actualizarNegocioDTO) throws Exception{
        negocioServicio.actualizarNegocio(actualizarNegocioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Negocio actualizado con éxito."));
    }
    //-------------------------

    @GetMapping("/buscar-negocio/{nombreNegocio}")
    public ResponseEntity<MensajeDTO<DetalleNegocioDTO>> buscarNegocioPorNombre(@PathVariable String nombreNegocio)throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,negocioServicio.buscarNegocioPorNombre(nombreNegocio)));
    }

    @GetMapping("/buscar-negocios-tipo/{tipoNegocio}") //PUEDO PONER tipoNegocio en los {}??
                                                    //Se usaria RequestBody?
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> buscarNegociosPorTipo(@Valid @RequestBody TipoNegocio tipoNegocio) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,negocioServicio.buscarNegociosPorTipo(tipoNegocio)));
    }
    @GetMapping("/buscar-negocios-distancia/{rangoNegocio}")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> buscarNegociosPorDistancia(@PathVariable String idNegocio,@PathVariable int rangoNegocio) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,negocioServicio.buscarNegociosPorDistancia(idNegocio,rangoNegocio)));
    }
    @GetMapping("/filtrar-negocios")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> filtrarPorEstado(@Valid @RequestBody EstadoNegocio estadoNegocio) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,negocioServicio.filtrarPorEstado(estadoNegocio)));
    }
    @GetMapping("/listar-negocios-usuario/{idUsuario}")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> listarNegociosDeUsuario(@PathVariable String idUsuario) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,negocioServicio.listarNegociosDeUsuario(idUsuario)));
    }

}
