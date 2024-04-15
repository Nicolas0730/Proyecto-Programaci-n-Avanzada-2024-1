package co.edu.uniquindio.unilocal.controladores;

import co.edu.uniquindio.unilocal.dto.CambiarPasswordDTO;
import co.edu.uniquindio.unilocal.dto.ImagenDTO;
import co.edu.uniquindio.unilocal.dto.MensajeDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.ActualizarNegocioDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.DetalleNegocioDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.ItemNegocioDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.RegistroNegocioDTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.RegistroComentarioDTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.ResponderComentarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.ActualizarUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.DetalleUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.ItemUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.RegistroUsuarioDTO;
import co.edu.uniquindio.unilocal.exception.ResourceNotFoundException;
import co.edu.uniquindio.unilocal.model.Ubicacion;
import co.edu.uniquindio.unilocal.servicios.interfaces.ComentarioServicio;
import co.edu.uniquindio.unilocal.servicios.interfaces.ImagenesServicio;
import co.edu.uniquindio.unilocal.servicios.interfaces.NegocioServicio;
import co.edu.uniquindio.unilocal.servicios.interfaces.UsuarioServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;
    private final NegocioServicio negocioServicio;
    private final ComentarioServicio comentarioServicio;

    @PostMapping("/registrar-negocio")
    public ResponseEntity<MensajeDTO<String>> crearNegocio(@Valid @RequestBody RegistroNegocioDTO registroNegocioDTO) throws Exception{
        negocioServicio.crearNegocio(registroNegocioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"El negocio fue creado correctamente."));
    }

    @GetMapping("/buscar-negocios-por-nombre/{idUsuario}/{nombre}")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> buscarNegociosPorNombre(@PathVariable String nombre,@PathVariable String idUsuario) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,negocioServicio.buscarNegociosPorNombre(nombre,idUsuario)));
    }

    @GetMapping("/listar-negocios-usuario/{idUsuario}")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> listarNegociosDeUsuario(@PathVariable String idUsuario) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,negocioServicio.listarNegociosDeUsuario(idUsuario)));
    }

    @PutMapping("/editar-negocio")
    public ResponseEntity<MensajeDTO<String>> actualizarNegocio(@Valid @RequestBody ActualizarNegocioDTO actualizarNegocioDTO) throws Exception{
        negocioServicio.actualizarNegocio(actualizarNegocioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Negocio actualizado con éxito."));
    }

    @DeleteMapping("/eliminar-negocio/{idNegocio}")
    public ResponseEntity<MensajeDTO<String>> eliminarNegocio(@PathVariable String idNegocio) throws Exception{
        negocioServicio.eliminarNegocio(idNegocio);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Negocio eliminado correctamente."));
    }

    @GetMapping("/buscar-negocio/{idUsuario}/{idNegocio}")
    public ResponseEntity<MensajeDTO<DetalleNegocioDTO>> buscarNegocio(@PathVariable String idNegocio, @PathVariable String idUsuario) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,negocioServicio.buscarNegocio(idNegocio,idUsuario)));
    }

    @PutMapping("/editar-usuario")
    public ResponseEntity<MensajeDTO<String>> actualizarUsuario(@Valid @RequestBody ActualizarUsuarioDTO actualizarUsuarioDTO) throws Exception{
        usuarioServicio.actualizarUsuario(actualizarUsuarioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Usuario actualizado correctamente."));
    }

    @GetMapping("/obtener-usuario/{idCuenta}")
    public ResponseEntity<MensajeDTO<DetalleUsuarioDTO>> obtenerUsuario(@PathVariable String idCuenta) throws Exception{

        return ResponseEntity.ok().body( new MensajeDTO<>(false,
                usuarioServicio.obtenerUsuario(idCuenta) ) );
    }

    @PutMapping("/recuperar-contrasenia-usuario/{idUsuario}")
    public ResponseEntity<MensajeDTO<CambiarPasswordDTO>> recuperarContrasenia(@PathVariable String idUsuario) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false,usuarioServicio.recuperarContrasenia(idUsuario)));
    }

    @DeleteMapping("/eliminar-usuario/{idUsuario}")
    public ResponseEntity<MensajeDTO<String>> eliminarCuentaUsuario(@PathVariable String idUsuario) throws Exception { //Que retorne el id de la cuenta eliminada
                usuarioServicio.eliminarUsuario(idUsuario);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Cliente eliminado correctamente "+idUsuario));
    }

    @PutMapping("/agregar-negocio-favoritos/{idNegocio}/{idUsuario}")
    public ResponseEntity<MensajeDTO<String>> agregarNegocioFavorito(@PathVariable String idUsuario,@PathVariable String idNegocio) throws Exception{
        usuarioServicio.agregarNegocioFavorito(idUsuario,idNegocio);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Negocio agregado a la lista de favoritos correctamente"));
    }

    @DeleteMapping("/eliminar-negocio-favorito/{idNegocio}/{idUsuario}")
    public ResponseEntity<MensajeDTO<String>> eliminarNegocioFavorito(@PathVariable String idUsuario,@PathVariable String idNegocio) throws ResourceNotFoundException{
        usuarioServicio.eliminarNegocioFavorito(idUsuario,idNegocio);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Negocio "+idNegocio+" eliminado de la lista de favoritos"));
    }

    @GetMapping("/solicitar-Ruta/{idUsuario}")
    public ResponseEntity<MensajeDTO<String>> solicitarRuta(@PathVariable String idUsuario,@Valid @RequestBody Ubicacion ubicacionDestino) throws ResourceNotFoundException {
        usuarioServicio.solicitarRuta(idUsuario,ubicacionDestino);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Ruta encontrada correctamente"));
    }

    @GetMapping("/recomendar-lugares/{idUsuario}")
    //Recomendar lugares en función de las búsquedas que realiza.
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> recomendarLugares(@PathVariable String idUsuario) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, usuarioServicio.recomendarLugares(idUsuario)));
    }

    @GetMapping("/listar-negocios-favoritos/{idUsuario}")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> listarNegociosFavoritos(@PathVariable String idUsuario) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,usuarioServicio.listarNegociosFavoritos(idUsuario)));
    }

    @PutMapping("/actualizar-ubicacion/{idUsuario}")
    public ResponseEntity<MensajeDTO<String>> actualizarUbicacion(@PathVariable String idUsuario,double longitud, double latitud) throws Exception{
        usuarioServicio.actualizarUbicacion(idUsuario,longitud,latitud);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Ubicación actualizada correctamente."));
    }

    @PostMapping("/registrar-comentario")
    public ResponseEntity<MensajeDTO<String>> registrarComentario(@Valid @RequestBody RegistroComentarioDTO registroComentarioDTO) throws Exception{
        comentarioServicio.registrarComentario(registroComentarioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Comentario registrado correctamente."));
    }

    @PutMapping("/responder-comentario")
    public ResponseEntity<MensajeDTO<String>> responderComentario(@Valid @RequestBody ResponderComentarioDTO responderComentarioDTO) throws Exception{
        comentarioServicio.responderComentario(responderComentarioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Comentario respondido correctamente."));
    }


}
