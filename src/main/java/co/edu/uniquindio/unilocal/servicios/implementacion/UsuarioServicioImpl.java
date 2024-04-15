package co.edu.uniquindio.unilocal.servicios.implementacion;

import co.edu.uniquindio.unilocal.dto.CambiarPasswordDTO;
import co.edu.uniquindio.unilocal.dto.EmailDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.ItemNegocioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.ActualizarUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.DetalleUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.RegistroUsuarioDTO;
import co.edu.uniquindio.unilocal.exception.ResourceNotFoundException;
import co.edu.uniquindio.unilocal.model.*;
import co.edu.uniquindio.unilocal.repositorio.NegocioRepo;
import co.edu.uniquindio.unilocal.repositorio.UsuarioRepo;
import co.edu.uniquindio.unilocal.servicios.interfaces.EmailServicio;
import co.edu.uniquindio.unilocal.servicios.interfaces.UsuarioServicio;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
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
    private final EmailServicio emailServicio;

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

        if( existeCorreo(registroUsuarioDTO.correo()) ){
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
        usuario.setUbicacion(registroUsuarioDTO.ubicacion());

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
     * Método usado al momento de registrar un nuevo usuario en la Bd
     * sirve para verificar si existe un usuario registrado en la BD con ese nickname
     * @param nickname a verificar si ya está en uso
     * @return true si existe un usuario con el nickname en uso, false de lo contrario
     */
    private boolean existeNickname(String nickname) {
        return usuarioRepo.existsByNickname(nickname);
    }

    /**
     * Método usado al momento de actualizar la información de un usuario, su función
     * es verificar si ya existe un usuario registrado en la BD con ese correo a excepciión
     * de él mismo
     * @param correo
     * @return false si el correo está en uso por un usuario que no sea el usuario con el id
     * por parámetro, true si nadie está haciendo uso del correo o si quien lo usa es el mismo
     * usuario indicado por parámetro
     */
    private boolean ConsultarDisponibilidadEmail(String id, String correo) {

        boolean valido = false;

        // Verificar si el correo electrónico está en uso por otros usuarios
        Usuario usuario = usuarioRepo.findByCorreo(correo).orElse(null);
        if (usuario == null) {
            // El correo electrónico no está en uso por otros usuarios
            valido = true;
        } else {
            // El correo electrónico está en uso por otro usuario
            if (id != null && id.equals(usuario.getId())) {
                // El correo electrónico está en uso por el usuario con el ID proporcionado
                valido = true;
            }
        }
        return valido;

    }

    /**
     * Método usado al momento de registrar un usuario el cual verifica que el correo que el usuario
     * que se está registrando ingrese un correo que nadie mas use
     * @param correo
     * @return true si existe un usuario en la BD con el correo en uso, false si nadie lo usa
     */
    private boolean existeCorreo(String correo) {
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
        usuario.setUbicacion(actualizarUsuarioDTO.ubicacion());
        usuario.setEstadoRegistro(actualizarUsuarioDTO.estadoRegistro());
        if(!ConsultarDisponibilidadEmail(actualizarUsuarioDTO.idUsuario(),actualizarUsuarioDTO.correo()) ){
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
                usuario.getNickname(), usuario.getCorreo(), usuario.getCiudad(),usuario.getUbicacion(),usuario.getNegociosFavoritos());
    }

    /**
     * Método auxiliar encargado de validar la existencia de un Usuario en la BD
     * @param idUsuario
     * @return Si existe retorna el Optional<usuario>, de lo contrario lanza una Excepcion
     * @throws Exception
     */
    private Optional<Usuario> validarUsuarioExiste(String idUsuario) throws ResourceNotFoundException{

        //Buscamos el usuario que se quiere manipular
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

    /**
     * Método que recupera la contraseña de un usuario dado su id
     * @param idUsuario
     * @return
     * @throws Exception
     */
    @Override
    public CambiarPasswordDTO recuperarContrasenia(String idUsuario) throws Exception {
        Optional<Usuario> optionalUsuario = validarUsuarioExiste(idUsuario);
        String nuevaContra=generarNuevaContrasenia();

        //Obtenemos el usuario que se quiere recuperar la contraseña y verifica su estado
        Usuario usuario = optionalUsuario.get();
        if (usuario.getEstadoRegistro().equals(EstadoRegistro.INACTIVO)){
            throw new Exception("CUENTA CON ESTADO INVÁLIDO");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        usuario.setContrasenia(passwordEncoder.encode(nuevaContra));
        usuarioRepo.save(usuario);
        CambiarPasswordDTO cambiarPasswordDTO = new CambiarPasswordDTO(idUsuario,nuevaContra,usuario.getCorreo());
        enviarCorreoRecuperacion(cambiarPasswordDTO);
        return cambiarPasswordDTO;
    }

    /**
     * Método encargado de enviar un correo para la recuperación de contraseña de un usuario
     * @param cambiarPasswordDTO Contiene los datos necesarios para enviar un correo
     * @throws Exception
     */
    private void enviarCorreoRecuperacion(CambiarPasswordDTO cambiarPasswordDTO) throws Exception {

        emailServicio.enviarCorreo(new EmailDTO("RECUPERACIÓN DE CONTRASEÑA", "Su nueva contraseña es: "+cambiarPasswordDTO.passwordNueva(), cambiarPasswordDTO.email()));

    }

    /**
     * Método que genera un String que servirá como contraseña, este contiene al menos 8
     * caracteres, incluyendo al menos una letra mayúscula, una letra minúscula, un número
     * y un carácter especial
     * @return contraseña generada aleatoriamente
     */
    private String generarNuevaContrasenia() {
        String CARACTERES_PERMITIDOS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@$!%*?&";
        SecureRandom RANDOM = new SecureRandom();
        StringBuilder contrasenia = new StringBuilder();

        // Agregar al menos una letra mayúscula
        contrasenia.append((char) (RANDOM.nextInt(26) + 'A'));

        // Agregar al menos una letra minúscula
        contrasenia.append((char) (RANDOM.nextInt(26) + 'a'));

        // Agregar al menos un número
        contrasenia.append((char) (RANDOM.nextInt(10) + '0'));

        // Agregar al menos un carácter especial
        contrasenia.append(CARACTERES_PERMITIDOS.charAt(RANDOM.nextInt(CARACTERES_PERMITIDOS.length())));

        // Completar la contraseña hasta alcanzar la longitud mínima
        while (contrasenia.length() < 8) {
            contrasenia.append(CARACTERES_PERMITIDOS.charAt(RANDOM.nextInt(CARACTERES_PERMITIDOS.length())));
        }

        // Mezclar la contraseña para que los caracteres estén en orden aleatorio
        char[] contraseniaMezclada = contrasenia.toString().toCharArray();
        for (int i = 0; i < contrasenia.length(); i++) {
            int indiceAleatorio = RANDOM.nextInt(contrasenia.length());
            char temp = contraseniaMezclada[i];
            contraseniaMezclada[i] = contraseniaMezclada[indiceAleatorio];
            contraseniaMezclada[indiceAleatorio] = temp;
        }

        return new String(contraseniaMezclada);
    }

    /**
     * Método que dado el id de un usuario, agrega a su lista de negocios favoritos
     * el id de un negocio
     * @param idUsuario
     * @param idNegocio
     * @return id del negocio agregado a la lista de negocios favoritos del usuario
     * @throws Exception
     */
    @Override
    public String agregarNegocioFavorito(String idUsuario,String idNegocio) throws Exception {
        //Verifica que el usuario que se va a agregar a favoritos exista en la BD
        if (idNegocio == null || !negocioRepo.existsById(idNegocio)) {
            throw new ResourceNotFoundException("El negocio que desea agregar de la lista de favoritos no se encuentra registrado en la base de datos.");
        }

        // Verifica si el usuario existe
        Usuario usuario = usuarioRepo.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe."));

        // Inicializa la lista de favoritos si es null
        if (usuario.getNegociosFavoritos() == null) {
            usuario.setNegociosFavoritos(new ArrayList<>());
        }

        // Agrega el negocio a la lista de favoritos
        usuario.getNegociosFavoritos().add(idNegocio);

        // Inicializa la lista de registros de búsqueda si es null
        if (usuario.getRegistroBusquedas() == null) {
            usuario.setRegistroBusquedas(new ArrayList<>());
        }

        // Agrega el nombre del negocio a la lista de registros de búsqueda
        usuario.getRegistroBusquedas().add(obtenerNombreNegocioById(idNegocio));

        // Guarda los cambios en la base de datos
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

    /**
     * Método que dado el id de un usuario, elimina de su lista de negocios favoritos
     * el id de un negocio indicado
     * @param idUsuario
     * @param idNegocio
     * @return id del negocio eliminado de la lista de negocios favoritos del usuario
     * @throws Exception
     */
    @Override
    public String eliminarNegocioFavorito(String idUsuario,String idNegocio) throws ResourceNotFoundException {
        //Verifica que el usuario que se va a eliminar de favoritos exista en la BD
        if (!negocioRepo.existsById(idNegocio)){
            throw new ResourceNotFoundException("El negocio que desea eliminar de la lista de favoritos no se encuentra registrado en la base de datos.");
        }
        Optional<Usuario> optionalUsuario = validarUsuarioExiste(idUsuario);
        Usuario usuario = optionalUsuario.get();
        usuario.getNegociosFavoritos().remove(idNegocio);
        usuarioRepo.save(usuario);
        return idNegocio;
    }

    /**
     * Este método calculará POR AHORA la distancia en línea recta entre dos puntos
     * geográficos utilizando la fórmula de la distancia haversine. Esto no es una ruta real,
     * sino la distancia entre los dos puntos en una línea directa.
     ****Cuando se tenga la api de los mapas se reescribirá el método****
     * Cada vez que se vaya a solicitar una ruta el usuario debe actualizar su atributo ubicacion
     * para que se pueda calcular
     * @param idUsuario
     * @param ubicacionDestino
     * @return
     */
    @Override
    public double solicitarRuta(String idUsuario, Ubicacion ubicacionDestino) throws ResourceNotFoundException {
        Optional<Usuario> optionalUsuario = validarUsuarioExiste(idUsuario);
        Usuario usuario = optionalUsuario.get();
        double distancia = distanciaEntreDosPuntos(usuario.getUbicacion().getLatitud(),
                usuario.getUbicacion().getLongitud(),ubicacionDestino.getLatitud(),
                ubicacionDestino.getLongitud());
        return distancia;
    }

    /*** Método auxiliar para calcular la distancia entre dos puntos utilizando la fórmula de la
     * distancia haversine
     * Método hecho con chatGPT
     * @param latitudUsuario
     * @param longitudUsuario
     * @param latitudNegocio
     * @param longitudNegocio
     * @return
             */
    private double distanciaEntreDosPuntos(double latitudUsuario, double longitudUsuario, double latitudNegocio, double longitudNegocio) {
        // Radio de la Tierra en kilómetros
        final int RADIO_TIERRA = 6371;

        // Convertir las latitudes y longitudes de grados a radianes
        double latitudUsuarioRad = Math.toRadians(latitudUsuario);
        double longitudUsuarioRad = Math.toRadians(longitudUsuario);
        double latitudNegocioRad = Math.toRadians(latitudNegocio);
        double longitudNegocioRad = Math.toRadians(longitudNegocio);

        // Calcular la diferencia de latitudes y longitudes
        double diferenciaLatitud = latitudNegocioRad - latitudUsuarioRad;
        double diferenciaLongitud = longitudNegocioRad - longitudUsuarioRad;

        // Calcular la distancia utilizando la fórmula de la distancia haversine
        double a = Math.pow(Math.sin(diferenciaLatitud / 2), 2)
                + Math.cos(latitudUsuarioRad) * Math.cos(latitudNegocioRad)
                * Math.pow(Math.sin(diferenciaLongitud / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distancia = RADIO_TIERRA * c;

        return distancia;
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
     * Método que lista todos los negocios que el usuario tiene como favoritos en una lista
     * @param idUsuario Lista con los ids de los negocios que voy a buscar en la BD
     * @return Lista de ItemNegocio
     * @throws Exception
     */
    @Override
    public List<ItemNegocioDTO> listarNegociosFavoritos(String idUsuario) throws Exception{
        Optional<Usuario> optionalUsuario = validarUsuarioExiste(idUsuario);
        Usuario usuario = optionalUsuario.get();
        List<Negocio> listaNegocios = negocioRepo.ListarFavoritos(usuario.getNegociosFavoritos());
        if (listaNegocios.isEmpty()){
            throw new ResourceNotFoundException("Error al momento de obtener los negocios favoritos ");
        }
        List<ItemNegocioDTO> items = new ArrayList<>();
        for (Negocio negocio: listaNegocios){
            items.add(new ItemNegocioDTO(negocio.getId(),negocio.getNombre(),negocio.getListaImagenes(),negocio.getTipoNegocio(),negocio.getDireccion()));
        }
        return  items;
    }

    /**
     * Ya que para nuestro proyecto el usuario tiene una ubicacion como atributo, este debe
     * poder actualizarla con un botón cada vez que desee calcular una ruta entre él y un negocio
     * @param idUsuario
     * @param longitud
     * @param latitud
     * @throws Exception
     */
    @Override
    public void actualizarUbicacion(String idUsuario,double longitud, double latitud) throws Exception{
        Optional<Usuario> optionalUsuario = validarUsuarioExiste(idUsuario);
        Usuario usuario = optionalUsuario.get();
        usuario.setUbicacion(new Ubicacion(latitud,longitud));
        usuarioRepo.save(usuario);
    }

    }
