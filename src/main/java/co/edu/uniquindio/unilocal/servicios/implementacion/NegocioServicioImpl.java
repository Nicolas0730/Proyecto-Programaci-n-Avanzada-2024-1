
package co.edu.uniquindio.unilocal.servicios.implementacion;

import co.edu.uniquindio.unilocal.dto.NegocioDTO.*;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.ItemComentariODTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.ItemUsuarioDTO;
import co.edu.uniquindio.unilocal.exception.ResourceNotFoundException;
import co.edu.uniquindio.unilocal.model.*;
import co.edu.uniquindio.unilocal.repositorio.NegocioRepo;
import co.edu.uniquindio.unilocal.repositorio.UsuarioRepo;
import co.edu.uniquindio.unilocal.servicios.interfaces.NegocioServicio;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NegocioServicioImpl implements NegocioServicio {

    private final NegocioRepo negocioRepo;
    private final UsuarioRepo usuarioRepo;

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
        negocio.setNombre(registroNegocioDTO.nombre());
        negocio.setDescripcion(registroNegocioDTO.descripcion());
        negocio.setListaImagenes(registroNegocioDTO.listaImagenes());
        negocio.setListaTelefonos(registroNegocioDTO.listaTelefonos());
        negocio.setUbicacion(registroNegocioDTO.ubicacion());

        if(!validarEstadoRegistroUsuario(registroNegocioDTO.idUsuario())){
            throw new Exception("No se pudo crear el negocio, el estado del usuario es inválido");
        }

        negocio.setIdUsuario(registroNegocioDTO.idUsuario());
        if (!esHorarioValido(registroNegocioDTO.horarioNegocio())) {
            throw new Exception("El horario del negocio no es válido. Debe estar entre las 7:00 am y las 10:00 pm.");
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
     * @return
     * @throws Exception
     */
    @Override
    public DetalleNegocioDTO buscarNegocio(String idNegocio) throws Exception {

        Optional<Negocio> optionalNegocio = validarNegocioExiste(idNegocio);

        Negocio negocio = optionalNegocio.get();

        return new DetalleNegocioDTO(negocio.getId(),negocio.getNombre(),negocio.getDescripcion(),negocio.getListaImagenes(),
                negocio.getListaTelefonos(),negocio.getUbicacion(),negocio.getIdUsuario(),negocio.getHorario(),
                negocio.getTipoNegocio(),negocio.getHistorialNegocio(),negocio.getCiudad(),negocio.getDireccion());
    }

    /**
     * Método que busca todos los negocios que tengan un nombre indicado por parámetro
     * @param nombre que se va a buscar en los negocios
     * @return Lista de los negocios que tienen el nombre por parámetro
     * @throws Exception
     */
    @Override
    public List<ItemNegocioDTO> busquedaPorNombre(String nombre) throws Exception{
        List<Negocio> listaNegocios = negocioRepo.busquedaNombresSimilares(nombre);
        if (listaNegocios.isEmpty()){
            throw new ResourceNotFoundException("Error al momento de obtener los negocio que contienen el nombre "+nombre);
        }
        List<ItemNegocioDTO> items = new ArrayList<>();
        for (Negocio negocio: listaNegocios){
            items.add(new ItemNegocioDTO(negocio.getId(),negocio.getNombre(),negocio.getListaImagenes(),negocio.getTipoNegocio(),negocio.getDireccion()));
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
     *      * espera
     * @param revisionDTO
     * @throws Exception
     */
    @Override
    public void aprobarNegocio(RegistroRevisionDTO revisionDTO) throws Exception {

        Optional<Negocio> optionalNegocio = validarNegocioExiste(revisionDTO.idNegocio());
        Negocio negocio = optionalNegocio.get();

        if (negocio.getEstadoRegistro().equals(EstadoNegocio.RECHAZADO)){
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
        if (negocio.getEstadoRegistro().equals(EstadoNegocio.RECHAZADO)){
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
        if (!esHorarioValido(actualizarNegocioDTO.horarioNegocio())) {
            throw new Exception("El horario del negocio no es válido. Debe estar entre las 7:00 am y las 10:00 pm.");
        }
        negocio.setNombre(actualizarNegocioDTO.nombre());
        negocio.setDescripcion(actualizarNegocioDTO.descripcion());
        negocio.setListaImagenes(actualizarNegocioDTO.listaImagenes());
        negocio.setListaTelefonos(actualizarNegocioDTO.listaTelefonos());
        negocio.setHorario(actualizarNegocioDTO.horarioNegocio());

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

    /**
     * Método encargado de verificar si existe un negocio en la base de datos
     * con un nombre enviado por parámetro
     * @param nombre
     * @return true si existe, false de lo contrario
     */
    private boolean validarNombreNegocio(String nombre) {
        return negocioRepo.existsByNombre(nombre);
    }


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

        List<Negocio> listaNegocios = negocioRepo.buscarNegociosPorTipo(tipoNegocio);

        if (listaNegocios.isEmpty()){
            throw new ResourceNotFoundException("Error al momento de filtrar negocios por el tipo "+tipoNegocio);
        }

        List<ItemNegocioDTO> items = new ArrayList<>();

        for(Negocio negocio : listaNegocios){
            items.add(new ItemNegocioDTO(negocio.getId(),negocio.getNombre(),negocio.getListaImagenes(),negocio.getTipoNegocio(),negocio.getDireccion()));
        }
        return items;
    }

    /**
     * Método que busca en la BD negocios en un rango de distancia indicado por parámetro
     * <DetalleNegocioDTO> el cual contiene todos los atributos del negocio
     * @param distanciaAlrededor
     * @return Lista de negocios en el rango indicado
     */
    @Override
    public List<DetalleNegocioDTO> buscarNegociosPorDistancia(int distanciaAlrededor) {
        //Falta construir la consulta
        return null;
    }

    /**
     * Método usado al momento de realizar una busqueda de negocios pero filtrando
     * por los estados que un negocio puede tener
     * @param estadoNegocio  APROBADO/EN_ESPERA/RECHAZADO
     * @throws Exception
     */
    @Override
    public List<ItemNegocioDTO> filtrarPorEstado(EstadoNegocio estadoNegocio) throws Exception {
        List<Negocio> listaNegocios = negocioRepo.ListarNegocioEstado(estadoNegocio);
        if (listaNegocios.isEmpty()){
            throw new ResourceNotFoundException("Error al momento de obtener los negocio con el estado "+estadoNegocio);
        }
        List<ItemNegocioDTO> items = new ArrayList<>();
        for (Negocio negocio: listaNegocios){
            items.add(new ItemNegocioDTO(negocio.getId(),negocio.getNombre(),negocio.getListaImagenes(),negocio.getTipoNegocio(),negocio.getDireccion()));
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
            items.add(new ItemNegocioDTO(negocio.getId(),negocio.getNombre(),negocio.getListaImagenes(),negocio.getTipoNegocio(),negocio.getDireccion()));
        }
        return items;
    }
}

