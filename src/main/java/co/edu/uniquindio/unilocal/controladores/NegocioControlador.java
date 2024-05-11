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

    @GetMapping("/buscar-negocio/{nombreNegocio}")
    public ResponseEntity<MensajeDTO<DetalleNegocioDTO>> buscarNegocioPorNombre(@PathVariable String nombreNegocio)throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,negocioServicio.buscarNegocioPorNombre(nombreNegocio)));
    }

    @GetMapping("/buscar-negocios-distancia/{idUsuario}/{rangoNegocio}")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> buscarNegociosPorDistancia(@PathVariable String idUsuario,@PathVariable int rangoNegocio) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,negocioServicio.buscarNegociosPorDistancia(idUsuario,rangoNegocio)));
    }
    @GetMapping("/negocios-top-5")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> encontrarTop5() throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,negocioServicio.encontrarTop5()));
    }

    @PostMapping("/registrar-negocio")
    public ResponseEntity<MensajeDTO<String>> crearNegocio(@Valid @RequestBody RegistroNegocioDTO registroNegocioDTO) throws Exception{
        negocioServicio.crearNegocio(registroNegocioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"El negocio fue creado correctamente."));
    }

    @DeleteMapping("/eliminar-negocio/{idNegocio}")
    public ResponseEntity<MensajeDTO<String>> eliminarNegocio(@PathVariable String idNegocio) throws Exception{
        negocioServicio.eliminarNegocio(idNegocio);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Negocio eliminado correctamente."));
    }

    @PutMapping("/editar-negocio")
    public ResponseEntity<MensajeDTO<String>> actualizarNegocio(@Valid @RequestBody ActualizarNegocioDTO actualizarNegocioDTO) throws Exception{
        negocioServicio.actualizarNegocio(actualizarNegocioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Negocio actualizado con éxito."));
    }

}
