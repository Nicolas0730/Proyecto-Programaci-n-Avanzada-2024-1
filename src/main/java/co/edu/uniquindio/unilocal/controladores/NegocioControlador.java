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
    @GetMapping("/buscar-negocios-tipo")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> buscarNegociosPorTipo(@Valid @RequestBody TipoNegocio tipoNegocio) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,negocioServicio.buscarNegociosPorTipo(tipoNegocio)));
    }
    @GetMapping("/buscar-negocios-distancia/{idNegocio}/{rangoNegocio}")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> buscarNegociosPorDistancia(@PathVariable String idNegocio,@PathVariable int rangoNegocio) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,negocioServicio.buscarNegociosPorDistancia(idNegocio,rangoNegocio)));
    }
    @GetMapping("/negocios-top-5")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> encontrarTop5() throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,negocioServicio.encontrarTop5()));
    }

}
