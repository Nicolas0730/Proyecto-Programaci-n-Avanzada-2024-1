package co.edu.uniquindio.unilocal.servicios.implementacion;

import co.edu.uniquindio.unilocal.dto.NegocioDTO.*;
import co.edu.uniquindio.unilocal.exception.ResourceNotFoundException;
import co.edu.uniquindio.unilocal.model.*;
import co.edu.uniquindio.unilocal.repositorio.ComentarioRepo;
import co.edu.uniquindio.unilocal.repositorio.NegocioRepo;
import co.edu.uniquindio.unilocal.repositorio.UsuarioRepo;
import co.edu.uniquindio.unilocal.servicios.interfaces.NegocioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NegocioServicioImpl implements NegocioServicio {

    private final NegocioRepo negocioRepo;
    private final UsuarioRepo usuarioRepo;
    private final ComentarioRepo comentarioRepo;

    /**
     * Método utilizado por un usuario para registrar un negocio
     * este por defecto se crea con estado en espera mientras el
     * administrador valida el mismo
     * @param registroNegocioDTO
     * @return
     * @throws Exception
     */
    @Override
    public String crearNegocio(RegistroNegocioDTO registroNegocioDTO) throws Exception {
        //Se crea un objeto negocio que va a contener todos los datos de registroNegocioDTO
        Negocio negocio = new Negocio();
        if (negocioRepo.existsByNombre(registroNegocioDTO.nombre())){
            throw new Exception("Ya existe un negocio con el nombre. "+registroNegocioDTO.nombre());
        }
        if (negocioRepo.existsByDireccion(registroNegocioDTO.direccion())){
            throw new Exception("La direccion registrada ya se encuentra en uso.");
        }
        negocio.setNombre(registroNegocioDTO.nombre());
        negocio.setDescripcion(registroNegocioDTO.descripcion());
        negocio.setListaImagenes(registroNegocioDTO.listaImagenes());
        negocio.setListaTelefonos(registroNegocioDTO.listaTelefonos());
        negocio.setUbicacion(registroNegocioDTO.ubicacion());

        if(!validarEstadoRegistroUsuario(registroNegocioDTO.idUsuario())){
            throw new Exception("No se pudo crear el negocio, el estado del usuario es inválido");
        }

        negocio.setIdUsuario(registroNegocioDTO.idUsuario());
        for(HorarioNegocio horario : registroNegocioDTO.horarioNegocio()){
            if (!esHorarioValido(horario)) {
                throw new Exception("El horario del negocio no es válido. Debe estar entre las 7:00 am y las 10:00 pm.");
            }
        }

        negocio.setHorario(registroNegocioDTO.horarioNegocio());
        negocio.setTipoNegocio(registroNegocioDTO.tipoNegocio());
        negocio.setHistorialNegocio(
                List.of( new HistorialNegocio( LocalDateTime.now(), EstadoNegocio.EN_ESPERA) )
        );
        negocio.setEstadoRegistro(EstadoRegistro.ACTIVO);
        negocio.setCiudad(registroNegocioDTO.ciudad());
        negocio.setUbicacion(registroNegocioDTO.ubicacion());

        //Se guarda en la base de datos y obtenemos el objeto registrado
        Negocio negocioGuardado = negocioRepo.save(negocio);

        //Retornamos el id (código) del negocio registrado en la BD
        return negocioGuardado.getId();
    }

    /**
     * Método que verifica que no exista ningun negocio con la direccion indicada por parámetro
     * @param direccion que se valida que no vaya a estar en uso por ningun otro negocio
     * @return true si ningun negocio con estado activo y aprobado tienen la dirección indicada por parámetro
     */
//    private boolean validarDireccion(String direccion) {
//        boolean aux = true;
//        List<Negocio> negocioList = negocioRepo.busquedaPorEstadoRegistroyEstadonegocio(EstadoNegocio.APROBADO,EstadoRegistro.ACTIVO);
//        for (Negocio negocio: negocioList){
//            if (negocio.getDireccion().equals(direccion)) {
//                aux = false;
//                break;
//            }
//        }
//        return aux;
//    }

    /**
     * Método auxiliar que verifica que el estado de registro de un usuario
     * sea activo para poder permitir que un negocio sea creado por este
     * @param idUsuario a verificar su estado de registro
     * @return false si el estado del registro es inactivo, true si es activo
     */
    private boolean validarEstadoRegistroUsuario(String idUsuario) throws ResourceNotFoundException {
        boolean aux = true;

        if (!usuarioRepo.findById(idUsuario).isPresent()){
            throw new ResourceNotFoundException("El usuario no ha sido encontrado en la base de datos.");
        }
        Optional<Usuario> optionalUsuario = usuarioRepo.findById(idUsuario);
        Usuario usuario = optionalUsuario.get();

        if (usuario.getEstadoRegistro().equals(EstadoRegistro.INACTIVO)){
            aux=false;
        }
        return aux;
    }

    /**
     * Método que busca un negocio en la BD dado su id
     * @param idNegocio
     * @return DetalleNegocioDTO si se encuentra el negocio
     * @throws Exception
     */
    @Override
    public DetalleNegocioDTO buscarNegocio(String idNegocio, String idUsuario) throws Exception {

        Optional<Negocio> optionalNegocio = validarNegocioExiste(idNegocio);
        Negocio negocio = optionalNegocio.get();

        //Una vez se valida que el negocio existe lo agregamos a las busquedas del usuario
        //que realiza la busqueda
        agregarNegocioRegistroBusquedasUsuario(idUsuario,negocio.getNombre());

        return new DetalleNegocioDTO(negocio.getId(),negocio.getNombre(),negocio.getDescripcion(),negocio.getListaImagenes(),
                negocio.getListaTelefonos(),negocio.getUbicacion(),negocio.getIdUsuario(),negocio.getHorario(),
                negocio.getTipoNegocio(),negocio.getHistorialNegocio(),negocio.getCiudad(),negocio.getDireccion());
    }

    /**
     * Método auxiliar que busca un usuario dado su id y le agrega el nombre de un negocio
     * a los registro de busqueda del usuario
     * @param idUsuario usuario al que se modificará el registroBusqeudas
     * @param nombreNegocio negocio que se agregará a registroBusquedas
     * @throws Exception
     */
    private void agregarNegocioRegistroBusquedasUsuario(String idUsuario,String nombreNegocio) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepo.findById(idUsuario);
        if (usuarioOptional.isEmpty()){
            throw new Exception("Error al buscar el usuario con el id "+idUsuario);
        }
        Usuario usuario = usuarioOptional.get();
        if (usuario.getRegistroBusquedas().isEmpty()){ //Quiere decir que es el primer negocio que agregará
            usuario.setRegistroBusquedas(new ArrayList<>(List.of(nombreNegocio)));
        }else { //La lista de busquedas ya tiene negocios almacenados
        usuario.getRegistroBusquedas().addAll(List.of(nombreNegocio));
        }
        usuarioRepo.save(usuario);
    }

    /**
     * Método usado en la barra de busqueda. se encargar de buscar todos los negocios que
     * tengan un nombre indicado por parámetro y los lista
     * @param nombre que se va a buscar en los negocios
     * @return Lista de los negocios que tienen el nombre por parámetro
     * @throws Exception
     */
    @Override
    public List<ItemNegocioDTO> buscarNegociosPorNombre(String nombre, String idUsuario) throws Exception{
        List<Negocio> listaNegocios = negocioRepo.busquedaNombresSimilares(nombre);
        if (listaNegocios.isEmpty()){
            throw new ResourceNotFoundException("Error al momento de obtener los negocio que contienen el nombre "+nombre);
        }
        agregarNegocioRegistroBusquedasUsuario(idUsuario,nombre);

        List<ItemNegocioDTO> items = new ArrayList<>();
        for (Negocio negocio: listaNegocios){
            items.add(new ItemNegocioDTO(negocio.getId(),negocio.getNombre(),negocio.getListaImagenes(),negocio.getTipoNegocio(),negocio.getUbicacion(),negocio.getEstadoRegistro(),negocio.getDireccion()));
        }
        return  items;
    }

    /**
     * Método usado por un administrador para eliminar un negocio
     * la eliminación al ser lógica solo cambia su estado a Inactivo
     * @param idNegocio
     * @throws Exception
     */
    @Override
    public void eliminarNegocio(String idNegocio) throws Exception {
        Optional<Negocio> optionalNegocio = validarNegocioExiste(idNegocio);

        Negocio negocio = optionalNegocio.get();
        if (negocio.getEstadoRegistro().equals(EstadoRegistro.INACTIVO)){
            throw new Exception("No fue posible eliminar el Negocio.");
        }
        negocio.setEstadoRegistro(EstadoRegistro.INACTIVO); // Si se eliminaria así un negocio? 31/03 ************************
        negocioRepo.save(negocio);
    }

    /**
     * Método auxiliar encargado de validar la existencia de un Negocio en la BD
     * @param idNegocio
     * @return Si existe retorna el Optional<Negocio>, de lo contrario lanza una Excepcion
     * @throws ResourceNotFoundException
     */
    private Optional<Negocio> validarNegocioExiste(String idNegocio) throws ResourceNotFoundException {

        //Buscamos el negocio que se quiere manipular
        Optional<Negocio> optionalNegocio = negocioRepo.findById(idNegocio);

        //Si no se encontró el usuario, lanzamos una excepción
        if(optionalNegocio.isEmpty()){
            throw new ResourceNotFoundException("Negocio no encontrado.");
        }
        return optionalNegocio;
    }


    /**
     * Método usado por un administrador para aprobar un negocio que se encuentra en
     * espera
     * @param revisionDTO
     * @throws Exception
     */
    @Override
    public void aprobarNegocio(RegistroRevisionDTO revisionDTO) throws Exception {

        Optional<Negocio> optionalNegocio = validarNegocioExiste(revisionDTO.idNegocio());
        Negocio negocio = optionalNegocio.get();

        if (negocio.getEstadoRegistro().equals(EstadoRegistro.INACTIVO)||!negocio.getHistorialNegocio().get(negocio.getHistorialNegocio().size()-1).getEstadoNegocio().equals(EstadoNegocio.EN_ESPERA)){
            throw new Exception("No fue posible aprobar el Negocio. Verifica su estado previo.");
        }

        negocio.getHistorialNegocio().add( new HistorialNegocio(
                revisionDTO.motivo(),
                LocalDateTime.now(),
                EstadoNegocio.APROBADO,
                revisionDTO.idModerador()
        ) );
        negocioRepo.save(negocio);
    }

    /**
     * Método usado por un administrador para rechazar un negocio que se encuentra en
     * espera
     * @param revisionDTO
     * @throws Exception
     */
    @Override
    public void rechazarNegocio(RegistroRevisionDTO revisionDTO) throws Exception {

        Optional<Negocio> optionalNegocio = validarNegocioExiste(revisionDTO.idNegocio());

        Negocio negocio = optionalNegocio.get();
        if (negocio.getEstadoRegistro().equals(EstadoRegistro.INACTIVO)||!negocio.getHistorialNegocio().get(negocio.getHistorialNegocio().size()-1).getEstadoNegocio().equals(EstadoNegocio.EN_ESPERA)){
            throw new Exception("No fue posible rechazar el Negocio. Verifica su estado previo.");
        }
        negocio.getHistorialNegocio().add( new HistorialNegocio(
                revisionDTO.motivo(),
                LocalDateTime.now(),
                EstadoNegocio.RECHAZADO,
                revisionDTO.idModerador()
        ) );
        negocioRepo.save(negocio);
    }

    /**
     * Método que actualiza los datos de un negocio
     * Cabe resaltar que no todos los campos son actualizables
     * @param actualizarNegocioDTO
     * @throws Exception
     */
    @Override
    public void actualizarNegocio(ActualizarNegocioDTO actualizarNegocioDTO) throws Exception {
        Optional<Negocio> optionalNegocio = validarNegocioExiste(actualizarNegocioDTO.id());
        Negocio negocio = optionalNegocio.get();

        // Validar horario del negocio

        for(HorarioNegocio horario : actualizarNegocioDTO.horarioNegocio()) {
            if (!esHorarioValido(horario)) {
                throw new Exception("El horario del negocio no es válido. Debe estar entre las 7:00 am y las 10:00 pm.");
            }
        }
        negocio.setHorario(actualizarNegocioDTO.horarioNegocio());
        if (negocioRepo.existsByNombre(actualizarNegocioDTO.nombre())){
            throw new Exception("Ya existe un negocio con el nombre. "+actualizarNegocioDTO.nombre());
        }
        negocio.setNombre(actualizarNegocioDTO.nombre());

        negocio.setDescripcion(actualizarNegocioDTO.descripcion());
        negocio.setListaImagenes(actualizarNegocioDTO.listaImagenes());
        negocio.setListaTelefonos(actualizarNegocioDTO.listaTelefonos());

        negocioRepo.save(negocio);
    }

    /**
     * Método que se encarga de validar el horario de un negocio
     * un horario valido es entre las 7:00 am y 10:00 pm
     * @param horarioNegocio
     * @return true si es valido, false de lo contrario
     */
    private boolean esHorarioValido(HorarioNegocio horarioNegocio) {
        LocalTime horaApertura = horarioNegocio.getHoraApertura().toLocalTime();
        LocalTime horaCierre = horarioNegocio.getHoraCierre().toLocalTime();
        boolean aux=true;

        // Verificar que la hora de apertura esté entre las 7:00 am y las 10:00 pm
        if (horaApertura.isBefore(LocalTime.of(7, 0)) || horaApertura.isAfter(LocalTime.of(22, 0))) {
            aux= false;
        }
        // Verificar que la hora de cierre esté entre las 7:00 am y las 10:00 pm
        if (horaCierre.isBefore(LocalTime.of(7, 0)) || horaCierre.isAfter(LocalTime.of(22, 0))) {
            aux=false;
        }
        // Verificar que la hora de apertura sea antes de la hora de cierre
        if (horaApertura.isAfter(horaCierre)) {
            aux= false;
        }
        // Si pasa todas las validaciones, el horario es válido
        return aux;
    }

//    /**
//     * Método encargado de verificar si existe un negocio en la base de datos
//     * con un nombre enviado por parámetro
//     * @param nombre
//     * @return true si existe, false de lo contrario
//     */
//    private boolean validarNombreNegocio(String nombre) {
//        l
//        return negocioRepo.existsByNombre(nombre);
//    }


    /**
     * Método que busca en la BD un negocio por su nombre y retorna un detalleNegocioDTO
     * el cual contiene todos los atributos del negocio
     * @param nombreNegocio
     * @return
     * @throws ResourceNotFoundException
     */
    @Override
    public DetalleNegocioDTO buscarNegocioPorNombre(String nombreNegocio) throws ResourceNotFoundException {

        Optional<Negocio> optionalNegocio = negocioRepo.findByNombre(nombreNegocio);

        if (optionalNegocio.isEmpty()){
            throw new ResourceNotFoundException("El usuario no fue encontrado");
        }
        Negocio negocio = optionalNegocio.get();
        DetalleNegocioDTO detalleNegocioDTO = new DetalleNegocioDTO(negocio.getId(),negocio.getNombre(),
                negocio.getDescripcion(),negocio.getListaImagenes(),negocio.getListaTelefonos(),
                negocio.getUbicacion(),negocio.getIdUsuario(),negocio.getHorario(),
                negocio.getTipoNegocio(),negocio.getHistorialNegocio(),
                negocio.getCiudad(),negocio.getDireccion());
        return detalleNegocioDTO;
    }

    /**
     * Método que busca en la BD todos los negocios del tipo indicado por parámetro
     * el cual contiene todos los atributos del negocio
     * @param tipoNegocio
     * @return Listado de negocios del tipo indicado
     * @throws ResourceNotFoundException
     */
    @Override
    public List<ItemNegocioDTO> buscarNegociosPorTipo(TipoNegocio tipoNegocio) throws ResourceNotFoundException {

        List<Negocio> listaNegocios = negocioRepo.buscarNegocioPorTipo(tipoNegocio);

        if (listaNegocios.isEmpty()){
            throw new ResourceNotFoundException("Error al momento de filtrar negocios por el tipo "+tipoNegocio);
        }

        List<ItemNegocioDTO> items = new ArrayList<>();

        for(Negocio negocio : listaNegocios){
            items.add(new ItemNegocioDTO(negocio.getId(),negocio.getNombre(),negocio.getListaImagenes(),negocio.getTipoNegocio(),negocio.getUbicacion(),negocio.getEstadoRegistro(),negocio.getDireccion()));
        }
        return items;
    }

    /**
     * Método que dada el id de un usuario, se obtiene el usuario y su ubicación
     * busca en la BD negocios con estado activo. se valida con el metodo distanciaentredospuntos
     * y devuelve una lista de <DetalleNegocioDTO> el cual contiene todos los atributos del negocio
     * @param idUsuario el cual tiene la ubicacion desde donde se va a calcular los otros negocios cercanos
     * @param distanciaAlrededor
     * @return Lista de negocios en el rango indicado
     */
    @Override
    public List<ItemNegocioDTO> buscarNegociosPorDistancia(String idUsuario,int distanciaAlrededor) throws Exception {
        List<ItemNegocioDTO> negociosEnRango = new ArrayList<>();
        List<Negocio> listNegocios = negocioRepo.ListarNegocioPorEstadoRegistro(EstadoRegistro.ACTIVO);

        Optional<Usuario> optionalUsuario = usuarioRepo.findById(idUsuario);
        if (optionalUsuario.isEmpty()){
            throw new Exception("ERROR AL OBTENER USUARIO");
        }
        Usuario usuario = optionalUsuario.get();

        double latitud = usuario.getUbicacion().getLatitud();
        double longitud = usuario.getUbicacion().getLongitud();

        for (Negocio nego: listNegocios){

            double distancia = distanciaEntreDosPuntos(latitud, longitud, nego.getUbicacion().getLatitud(), nego.getUbicacion().getLongitud());

            // Si la distancia es menor o igual a la distancia máxima permitida, agregar el negocio a la lista de resultados
            if (distancia <= distanciaAlrededor) {
                negociosEnRango.add(new ItemNegocioDTO(nego.getId(),nego.getNombre(),nego.getListaImagenes(),nego.getTipoNegocio(),nego.getUbicacion(),nego.getEstadoRegistro(),nego.getDireccion()));
            }
        }
        return negociosEnRango;
    }

    /**
     * Método para calcular la distancia entre dos puntos utilizando la fórmula de la
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
     * Método usado al momento de realizar una busqueda de negocios pero filtrando
     * por los estados que un negocio puede tener
     * @param estadoNegocio  APROBADO/EN_ESPERA/RECHAZADO
     * @throws Exception
     */
    @Override
    public List<ItemNegocioDTO> filtrarPorEstado(EstadoNegocio estadoNegocio) throws Exception {
                                        //Le enviamos el estado de registro activo ya que no interesa obtener negocios inactivos
        List<Negocio> listaNegocios = negocioRepo.busquedaPorEstadoRegistroyEstadonegocio(estadoNegocio,EstadoRegistro.ACTIVO);
        if (listaNegocios.isEmpty()){
            throw new ResourceNotFoundException("Error al momento de obtener los negocio con el estado "+estadoNegocio);
        }
        List<ItemNegocioDTO> items = new ArrayList<>();
        for (Negocio negocio: listaNegocios){
            items.add(new ItemNegocioDTO(negocio.getId(),negocio.getNombre(),negocio.getListaImagenes(),negocio.getTipoNegocio(),negocio.getUbicacion(),negocio.getEstadoRegistro(),negocio.getDireccion()));
        }
        return  items;

    }

    /**
     * Método que se encarga de devolver todos los negocios asociados a un usuario
     * Se realiza una consulta a la base de datos donde se obtienen todos los negocios
     * que tiene idUsuario = al que llega por parámetro
     * @param idUsuario al que se le buscarán sus negocios
     * @return La lista de negocios relacionada al usuario
     * @throws ResourceNotFoundException
     */
    @Override
    public List<ItemNegocioDTO> listarNegociosDeUsuario(String idUsuario) throws ResourceNotFoundException {

        List<Negocio> listaNegocios = negocioRepo.listarNegocioUsuario(idUsuario); //Hacer consulta que traiga todos los negocios del usuario indicado por parámetro

        if (listaNegocios.isEmpty()){
            throw new ResourceNotFoundException("Error al momento de obtener los negocios relacionados al usuario "+idUsuario);
        }

        List<ItemNegocioDTO> items = new ArrayList<>();

        for(Negocio negocio : listaNegocios){
            items.add(new ItemNegocioDTO(negocio.getId(),negocio.getNombre(),negocio.getListaImagenes(),negocio.getTipoNegocio(),negocio.getUbicacion(),negocio.getEstadoRegistro(),negocio.getDireccion()));
        }
        return items;
    }

    /**
     * Método que devuelve el puntaje promedio de calificación de un negocio
     * @param idNegocio del negocio
     * @return puntaje del negocio
     * @throws Exception
     */
    public double calcularPuntajeNegocio(String idNegocio) throws Exception {
        double acumulado=0;
        List<Comentario> listaComentarios = comentarioRepo.listarComentario(idNegocio);
        if (listaComentarios.isEmpty()){
            throw new ResourceNotFoundException("Error al momento de obtener los comentarios del negocio "+idNegocio);
        }
        for (Comentario comentario: listaComentarios) {
            acumulado += comentario.getCalificacion(); //Acumula los puntajes de todos los comentarios
        }
        return (double) acumulado/listaComentarios.size();  //Devolvemos el promedio de las calificaciones para el negocio ingresado
    }

    /**
     * Método que encuentra el top 5 de los negocios que tienen mejor calificación
     * @return lista de 5 negocios
     * @throws Exception
     */
    @Override
    public List<ItemNegocioDTO> encontrarTop5() throws Exception {
        List<Negocio> negocioList = negocioRepo.busquedaPorEstadoRegistroyEstadonegocio(EstadoNegocio.APROBADO,EstadoRegistro.ACTIVO);
        Map<Negocio, Double> promediosCalificaciones = new HashMap<>();

        if (negocioList.isEmpty()){
            throw new Exception("ERROR AL OBTENER EL LISTADO DE NEGOCIOS ACTIVOS");
        }

        for (Negocio negocio : negocioList){
            double promedio = calcularPuntajeNegocio(negocio.getId());
            promediosCalificaciones.put(negocio, promedio);
        }

        // Ordenar los negocios por promedio de calificaciones de mayor a menor
        List<Map.Entry<Negocio, Double>> listaOrdenada = new ArrayList<>(promediosCalificaciones.entrySet());
        listaOrdenada.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // Obtener los top 5 negocios con mejores calificaciones
        List<Negocio> top5Negocios = new ArrayList<>();
        for (int i = 0; i < Math.min(5, listaOrdenada.size()); i++) {
            top5Negocios.add(listaOrdenada.get(i).getKey());
        }

        //Se convierte la lista de negocio a ItemNegocioDTO
        List<ItemNegocioDTO> itemNegocioDTOList = new ArrayList<>();
        for (Negocio negocio: top5Negocios){
            itemNegocioDTOList.add(new ItemNegocioDTO(negocio.getId(),negocio.getNombre(),
                    negocio.getListaImagenes(),negocio.getTipoNegocio(),negocio.getUbicacion(),negocio.getEstadoRegistro(),negocio.getDireccion()));
        }

        return itemNegocioDTOList;

    }
}