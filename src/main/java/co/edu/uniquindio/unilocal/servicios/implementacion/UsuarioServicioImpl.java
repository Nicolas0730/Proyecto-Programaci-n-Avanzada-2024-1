package co.edu.uniquindio.unilocal.servicios.implementacion;

import co.edu.uniquindio.unilocal.dto.NegocioDTO.ItemNegocioDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.RegistroNegocioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.ActualizarUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.DetalleUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.ItemUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.RegistroUsuarioDTO;
import co.edu.uniquindio.unilocal.exception.ResourceNotFoundException;
import co.edu.uniquindio.unilocal.model.*;
import co.edu.uniquindio.unilocal.repositorio.NegocioRepo;
import co.edu.uniquindio.unilocal.repositorio.UsuarioRepo;
import co.edu.uniquindio.unilocal.servicios.interfaces.UsuarioServicio;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class UsuarioServicioImpl implements UsuarioServicio {

    //variable para poder invocar sus métodos de acceso a la bd.
    private final UsuarioRepo usuarioRepo;
    private final NegocioRepo negocioRepo;

    /**
     * Método que almacena un nuevo usuario en la base de datos
     * @param registroUsuarioDTO
     * @return idDelUsuarioRegistrado
     * @throws Exception
     */
    @Override
    public String registrarUsuario(RegistroUsuarioDTO registroUsuarioDTO) throws Exception {
        //Se crea el objeto usuario
        Usuario usuario = new Usuario();

        //Se le asigna al usuario la información que trae registroDTO
        usuario.setNombre( registroUsuarioDTO.nombre() );

        if (existeNickname(registroUsuarioDTO.nickname()) ){
            throw new Exception("El nickname ya se encuentra registrado");
        }else
            usuario.setNickname( registroUsuarioDTO.nickname() );


        usuario.setCiudad( registroUsuarioDTO.ciudadResidencia() );
        usuario.setUrlFotoPerfil( registroUsuarioDTO.urlFotoPerfil() );

        if( existeEmail(registroUsuarioDTO.correo()) ){
            throw new Exception("El correo ya se encuentra registrado");
        }else {
            usuario.setCorreo( registroUsuarioDTO.correo() );
        }

        if (!validarPatronContrasenia(registroUsuarioDTO.contrasenia())){
            throw new Exception("La contraseña no cumple con las características indicadas");
        }else {
            usuario.setContrasenia(registroUsuarioDTO.contrasenia());
        }

        //Encriptación
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncriptada = passwordEncoder.encode( registroUsuarioDTO.contrasenia() );
        usuario.setContrasenia( passwordEncriptada );

        usuario.setEstadoRegistro(EstadoRegistro.ACTIVO);
        usuario.setDireccion(registroUsuarioDTO.direccion());
        usuario.setRegistroBusquedas(registroUsuarioDTO.registroBusquedas());//va a ser null al momento del registro
        usuario.setNegociosFavoritos(registroUsuarioDTO.negociosFavoritos()); //va a ser null al momento del registro

        //Se guarda en la base de datos y obtenemos el objeto registrado
        Usuario usuarioGuardado = usuarioRepo.save(usuario);

        //Retornamos el id (código) del cliente registrado
        return usuarioGuardado.getId();
    }

    /**
     * Método auxiliar que se encarga de verificcar que una contraseña cumpla una
     * expresión regular previamente definida para que esta sea segura
     * @param contrasenia
     * @return
     */
    private boolean validarPatronContrasenia(String contrasenia) {
        // Patrón para validar la contraseña
        String patron = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        // Compilamos el patrón
        Pattern pattern = Pattern.compile(patron);

        // Creamos un matcher con la contraseña dada
        Matcher matcher = pattern.matcher(contrasenia);
        return matcher.matches();
    }

    /**
     * Método para verificar si existe un usuario registrado en la BD con ese nickname
     * @param nickname a verificar si ya está en uso
     * @return true si existe, false de lo contrario
     */
    private boolean existeNickname(String nickname) {
        return usuarioRepo.existsByNickname(nickname);
    }

    /**
     * Método para verificar si existe un usuario registrado en la BD con ese correo
     * @param correo
     * @return
     */
    private boolean existeEmail(String correo) {
        return usuarioRepo.existsByCorreo(correo);
    }

    /**
     * Método que actualiza datos de un usuario ya registrado en la BD
     * @param actualizarUsuarioDTO contiene los campos que se pueden actualizar en un usuario
     * @throws Exception
     */
    @Override
    public void actualizarUsuario(ActualizarUsuarioDTO actualizarUsuarioDTO) throws Exception {

        Optional<Usuario> optionalUsuario = validarUsuarioExiste(actualizarUsuarioDTO.idUsuario());

        Usuario usuario = optionalUsuario.get();
        usuario.setNombre( actualizarUsuarioDTO.nombre() );
        usuario.setUrlFotoPerfil( actualizarUsuarioDTO.fotoPerfil() );
        usuario.setCiudad( actualizarUsuarioDTO.ciudadReidencia() );
        usuario.setDireccion(actualizarUsuarioDTO.direccion());
        if( existeEmail(actualizarUsuarioDTO.correo()) ){
            throw new Exception("El correo ya se encuentra registrado");
        }else {
            usuario.setCorreo( actualizarUsuarioDTO.correo() );
        }
        //Como el objeto cliente ya tiene un id, el save() no crea un nuevo registro sino que actualiza el que ya existe
        usuarioRepo.save(usuario);

    }

    /**
     * Método que elimina logicamente un usuario dado el id del mismo
     * el usuario debe existir para que este pueda eliminarse
     * @param idUsuario usuario a eliminar
     * @throws Exception
     */
    @Override
    public void eliminarUsuario(String idUsuario) throws Exception{

        Optional<Usuario> optionalUsuario = validarUsuarioExiste(idUsuario);

        //Obtenemos el usuario que se quiere eliminar y le asignamos el estado inactivo
        Usuario usuario = optionalUsuario.get();
        if (usuario.getEstadoRegistro().equals(EstadoRegistro.INACTIVO)){
            throw new Exception("La cuenta ya se encuentra eliminada");
        }
        usuario.setEstadoRegistro(EstadoRegistro.INACTIVO);
        //Como el objeto usuario ya se encuentra en la BD, actualiza el que ya existe con el nuevo estado
        usuarioRepo.save(usuario);
    }

    /**
     * Método que obtiene un usuario de la BD dado su id
     * @param idUsuario
     * @return
     * @throws Exception
     */
    @Override
    public DetalleUsuarioDTO obtenerUsuario(String idUsuario) throws Exception {

        Optional<Usuario> optionalUsuario = validarUsuarioExiste(idUsuario);

        //Obtenemos el cliente
        Usuario usuario = optionalUsuario.get();

        if (usuario.getEstadoRegistro().equals(EstadoRegistro.INACTIVO)){
            throw new Exception("El usuario solicitado tiene un estado de registro inválido");
        }

        //Retornamos el usuario en formato DTO
        return new DetalleUsuarioDTO( usuario.getId(), usuario.getNombre(), usuario.getUrlFotoPerfil(),
                usuario.getNickname(), usuario.getCorreo(), usuario.getCiudad(),usuario.getNegociosFavoritos());
    }

    /**
     * Método auxiliar encargado de validar la existencia de un Usuario en la BD
     * @param idUsuario
     * @return Si existe retorna el Optional<usuario>, de lo contrario lanza una Excepcion
     * @throws Exception
     */
    private Optional<Usuario> validarUsuarioExiste(String idUsuario) throws ResourceNotFoundException{

        //Buscamos el cliente que se quiere manipular
        Optional<Usuario> optionalUsuario = usuarioRepo.findById(idUsuario);

        //Si no se encontró el usuario, lanzamos una excepción
        if(optionalUsuario.isEmpty()){
            throw new ResourceNotFoundException("Usuario no encontrado.");
        }
        return optionalUsuario;
    }

    /**
     * Método que devuelve todos los usuarios de la BD con estado ACTIVO
     * @return Lista de usuarios activos
     */
//    @Override
//    public List<ItemUsuarioDTO> listarUsuarios() {
//
//        //Obtenemos todos los clientes de la base de datos
//        List<Usuario> usuarioList = usuarioRepo.findAll();  //HAY QUE MEJORAR LA CONSULTA PARA QUE NO TRAIGA TODOS LOS USUARIOS SINO UNICAMENTE LOS QUE TIENEN ESTADO ACTIVO 30/03
//
//        //Creamos una lista de DTOs de clientes
//        List<ItemUsuarioDTO> items = new ArrayList<>();
//
//        //Recorremos la lista de clientes y por cada uno creamos un DTO y lo agregamos a la lista
//        for (Usuario usuario : usuarioList) {
//            items.add(new ItemUsuarioDTO(usuario.getId(), usuario.getNombre(),
//                    usuario.getCorreo(), usuario.getUrlFotoPerfil(), usuario.getNickname(), usuario.getNegociosFavoritos()));
//        }
//        return items;
//    }

    @Override
    public String recuperarContrasenia() {
        return null;
    }

    /**
     * Método que se encarga de eliminar logicamente un Usuario cambiando su estado a inactivo
     * @param idUsuario usuario que se desea eliminar
     * @return Id usuario eliminado
     * @throws ResourceNotFoundException
     */
    @Override
    public String eliminarCuentaUsuario(String idUsuario) throws ResourceNotFoundException {

        Optional<Usuario> optionalUsuario = validarUsuarioExiste(idUsuario);

        //Obtenemos el cliente
        Usuario usuario = optionalUsuario.get();
        usuario.setEstadoRegistro(EstadoRegistro.INACTIVO);
        usuarioRepo.save(usuario);
        return usuario.getId();
    }

    @Override
    public String agregarNegocioFavorito(String idUsuario,String idNegocio) throws Exception {
        Optional<Usuario> optionalUsuario = validarUsuarioExiste(idUsuario);
        Usuario usuario = optionalUsuario.get();
        if (idNegocio==null||idNegocio.isEmpty()){
            throw new Exception("Ocurrió un error al momento de agregar el negocio "+idNegocio+" a la lista de favoritos del usuario "+idUsuario);
        }
        usuario.getNegociosFavoritos().add(idNegocio);
        usuario.getRegistroBusquedas().add(obtenerNombreNegocioById(idNegocio));
        usuarioRepo.save(usuario);

        return idNegocio;
    }

    /**
     * Método que obtiene el nombre de un negocio dado su id
     * este método será usado en agregar negocio favoritos para obtener
     * el nombre del negocio y agregarlo a la lista de registro de busquedas del usuario
     * @param idNegocio
     * @return
     * @throws Exception
     */
    private String obtenerNombreNegocioById(String idNegocio) throws Exception {

        Optional<Negocio> negocioOptional = negocioRepo.findById(idNegocio);
        if (negocioOptional.isEmpty()){
            throw new Exception("Error al obtener el negocio con el id "+idNegocio);
        }
        Negocio negocio = negocioOptional.get();
        return negocio.getNombre();
    }

    @Override
    public String eliminarNegocioFavorito(String idUsuario,String idNegocio) throws ResourceNotFoundException {

        if (!negocioRepo.existsById(idNegocio)){
            throw new ResourceNotFoundException("El negocio que desea eliminar de la lista de favoritos no se encuentra registrado en la base de datos.");
        }
        Optional<Usuario> optionalUsuario = validarUsuarioExiste(idUsuario);
        Usuario usuario = optionalUsuario.get();
        usuario.getNegociosFavoritos().remove(idNegocio);
        return idNegocio;
    }

    @Override
    public String solicitarRuta(Ubicacion ubicacionOrigen, Ubicacion ubicacionDestino) {
        return null;
    }


    /**
     * Método que recomienda lugares (negocios) en base a las busquedas que un usuario
     * ha realizado. Estas busquedas se encuentran en la lista de registro de busquedas
     * @param idUsuario
     * @return Lista de negocios a recomendar
     * @throws Exception
     */
    @Override
    public List<ItemNegocioDTO> recomendarLugares(String idUsuario) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepo.findById(idUsuario);
        if (usuarioOptional.isEmpty()){
            throw new Exception("Error al buscar el usuario con el id "+idUsuario);
        }
        Usuario usuario = usuarioOptional.get();
        //Obtenemos las busquedas del usuario
        List<String> listaLugares = usuario.getRegistroBusquedas();
        return obtenerLugaresRecomendados(listaLugares);
    }

    /**
     * Método que obtiene los lugares a recomendar de un usuario
     * recibe la lista de lugares que el usuario ha buscado y en base a estos
     * busca en la base de datos un negocio que su nombre sea similar al que
     * se encuentra en listaLugares. una vez obtenida la lista de negocios
     * se crea un List<itemNegocioDTO> y se transforma para retornarse
     * @param listaLugares lista con nombres de lugares que el usuario ha buscado
     * @return lista de itemNegocio de lugares que pueden interesarle al usuario
     */
    private List<ItemNegocioDTO> obtenerLugaresRecomendados(List<String> listaLugares) throws ResourceNotFoundException {

        List<Negocio> listaNegocios = new ArrayList<>();
        for (String lugar: listaLugares) {
            if (!listaNegocios.contains(negocioRepo.busquedaNombresSimilares(lugar).get(0))) {
                //El negocio no está en la lista, por lo tanto se agrega
                listaNegocios.add(negocioRepo.busquedaNombresSimilares(lugar).get(0));}
            }

            List<ItemNegocioDTO> itemNegocioDTOList = new ArrayList<>();
            for (Negocio negocio : listaNegocios) {
                itemNegocioDTOList.add(new ItemNegocioDTO(negocio.getId(), negocio.getNombre(), negocio.getListaImagenes(), negocio.getTipoNegocio(), negocio.getDireccion()));
            }
            return itemNegocioDTOList;
        }

    /**
     * Este metodo iria aqui o en NegocioRepo?
     * Método que lista todos los negocios que el usuario tiene como favoritos en una lista
     * @param negociosFavoritos Lista con los ids de los negocios que voy a buscar en la BD
     * @return Lista de ItemNegocio
     * @throws Exception
     */
    @Override
    public List<ItemNegocioDTO> listarNegociosFavoritos(List<String> negociosFavoritos) throws Exception{
        List<Negocio> listaNegocios = negocioRepo.ListarFavoritos(negociosFavoritos);
        if (listaNegocios.isEmpty()){
            throw new ResourceNotFoundException("Error al momento de obtener los negocios favoritos ");
        }
        List<ItemNegocioDTO> items = new ArrayList<>();
        for (Negocio negocio: listaNegocios){
            items.add(new ItemNegocioDTO(negocio.getId(),negocio.getNombre(),negocio.getListaImagenes(),negocio.getTipoNegocio(),negocio.getDireccion()));
        }
        return  items;
    }



    }
