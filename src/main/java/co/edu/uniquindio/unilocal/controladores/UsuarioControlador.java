package co.edu.uniquindio.unilocal.controladores;

import co.edu.uniquindio.unilocal.dto.CambiarPasswordDTO;
import co.edu.uniquindio.unilocal.dto.MensajeDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.ActualizarUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.DetalleUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.ItemUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.RegistroUsuarioDTO;
import co.edu.uniquindio.unilocal.exception.ResourceNotFoundException;
import co.edu.uniquindio.unilocal.model.Ubicacion;
import co.edu.uniquindio.unilocal.servicios.interfaces.UsuarioServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

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

//    @GetMapping("/obtener-lista-usuarios")
//    public ResponseEntity<MensajeDTO<List<ItemUsuarioDTO>>> listarUsuarios() throws Exception{
//
//        return ResponseEntity.ok().body( new MensajeDTO<>(false,
//                usuarioServicio.listarUsuarios()));
//    }

    @PutMapping("/recuperar-contrasenia-usuario")
    public ResponseEntity<MensajeDTO<CambiarPasswordDTO>> recuperarContrasenia(@PathVariable String idUsuario) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false,usuarioServicio.recuperarContrasenia(idUsuario)));
    }


    @DeleteMapping("/eliminar-usuario/{idUsuario}")
    public ResponseEntity<MensajeDTO<String>> eliminarCuentaUsuario(@PathVariable String idUsuario) throws ResourceNotFoundException{ //Que retorne el id de la cuenta eliminada
                usuarioServicio.eliminarCuentaUsuario(idUsuario);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Cliente eliminado correctamente"));
    }

    @PutMapping("/agregar-negocio-favoritos/{idNegocio}")
    public ResponseEntity<MensajeDTO<String>> agregarNegocioFavorito(@PathVariable String idUsuario,@PathVariable String idNegocio) throws Exception{
        usuarioServicio.agregarNegocioFavorito(idUsuario,idNegocio);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Negocio agregado a la lista de favoritos correctamente"));
    }

    @DeleteMapping("/eliminar-negocio-favorito/{idNegocio}")
    public ResponseEntity<MensajeDTO<String>> eliminarNegocioFavorito(@PathVariable String idUsuario,@PathVariable String idNegocio) throws ResourceNotFoundException{
        usuarioServicio.eliminarNegocioFavorito(idUsuario,idNegocio);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Negocio "+idNegocio+" eliminado de la lista de favoritos"));
    }

    @GetMapping("/solicitar-Ruta")
    public ResponseEntity<MensajeDTO<String>> solicitarRuta(@PathVariable String idUsuario,@Valid @RequestBody Ubicacion ubicacionDestino) throws ResourceNotFoundException {
        usuarioServicio.solicitarRuta(idUsuario,ubicacionDestino);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Ruta encontrada correctamente"));
    }

    @GetMapping("/recomendar-lugares")
    //Recomendar lugares en función de las búsquedas que realiza.
    public void recomendarLugares(String idUsuario) throws Exception {
        usuarioServicio.recomendarLugares(idUsuario);
    }

}
