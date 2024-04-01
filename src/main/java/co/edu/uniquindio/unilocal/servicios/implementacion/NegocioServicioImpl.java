package co.edu.uniquindio.unilocal.servicios.implementacion;

import co.edu.uniquindio.unilocal.dto.NegocioDTO.DetalleNegocioDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.NegocioDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.RegistroNegocioDTO;
import co.edu.uniquindio.unilocal.exception.ResourceNotFoundException;
import co.edu.uniquindio.unilocal.model.EstadoRegistro;
import co.edu.uniquindio.unilocal.model.Negocio;
import co.edu.uniquindio.unilocal.model.Usuario;
import co.edu.uniquindio.unilocal.repositorio.NegocioRepo;
import co.edu.uniquindio.unilocal.servicios.interfaces.NegocioServicio;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class NegocioServicioImpl implements NegocioServicio {

    //@Autowired
    private final NegocioRepo negocioRepo;

    public NegocioServicioImpl(NegocioRepo negocioRepo) {
        this.negocioRepo = negocioRepo;
    }


    @Override
    public String crearNegocio(RegistroNegocioDTO registroNegocioDTO) throws Exception {
        //Se crea un objeto negocio que va a contener todos los datos de registroNegocioDTO
        Negocio negocio = new Negocio();
        negocio.setNombre(registroNegocioDTO.nombre());
        negocio.setDescripcion(registroNegocioDTO.descripcion());
        negocio.setListaImagenes(registroNegocioDTO.listaImagenes());
        negocio.setListaTelefonos(registroNegocioDTO.listaTelefonos());
        negocio.setUbicacion(registroNegocioDTO.ubicacion());
        negocio.setIdUsuario(registroNegocioDTO.idUsuario());
        negocio.setHorario(registroNegocioDTO.horarioNegocio());
        negocio.setTipoNegocio(registroNegocioDTO.tipoNegocio());
        negocio.setHistorialNegocio(null); //Puedo Setear un null? o debo llenar en el List la creación del negocio
        negocio.setEstadoRegistro(EstadoRegistro.EN_ESPERA);
        negocio.setCiudad(registroNegocioDTO.ciudad());
        negocio.setUbicacion(registroNegocioDTO.ubicacion());

        //Se guarda en la base de datos y obtenemos el objeto registrado
        Negocio negocioGuardado = negocioRepo.save(negocio);

        //Retornamos el id (código) del negocio registrado en la BD
        return negocioGuardado.getId();
    }

    @Override
    public DetalleNegocioDTO buscarNegocios(String idNegocio) throws Exception {

        Optional<Negocio> optionalNegocio = validarNegocioExiste(idNegocio);

        Negocio negocio = optionalNegocio.get();

        return new DetalleNegocioDTO(negocio.getId(),negocio.getNombre(),negocio.getDescripcion(),negocio.getListaImagenes(),
                negocio.getListaTelefonos(),negocio.getUbicacion(),negocio.getIdUsuario(),negocio.getHorario(),
                negocio.getTipoNegocio(),negocio.getHistorialNegocio(),negocio.getCiudad(),negocio.getDireccion());
    }

    @Override
    public void eliminarNegocio(String idNegocio) throws Exception {
        Optional<Negocio> optionalNegocio = validarNegocioExiste(idNegocio);

        Negocio negocio = optionalNegocio.get();
        if (negocio.getEstadoRegistro().equals(EstadoRegistro.RECHAZADO)){
            throw new Exception("No fue posible eliminar el Negocio.");
        }
        negocio.setEstadoRegistro(EstadoRegistro.RECHAZADO); // Si se eliminaria así un negocio? 31/03 ************************
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


    @Override
    public void aprobarNegocio(DetalleNegocioDTO detalleNegocioDTO) throws Exception {

        Optional<Negocio> optionalNegocio = validarNegocioExiste(detalleNegocioDTO.id());
        Negocio negocio = optionalNegocio.get();

        if (negocio.getEstadoRegistro().equals(EstadoRegistro.RECHAZADO)){
            throw new Exception("No fue posible aprobar el Negocio. Verifica su estado previo.");
        }
        negocio.setEstadoRegistro(EstadoRegistro.APROBADO);
        negocioRepo.save(negocio);
    }

    @Override
    public void rechazarNegocio(DetalleNegocioDTO detalleNegocioDTO) throws Exception {

        Optional<Negocio> optionalNegocio = validarNegocioExiste(detalleNegocioDTO.id());

        Negocio negocio = optionalNegocio.get();
        if (negocio.getEstadoRegistro().equals(EstadoRegistro.RECHAZADO)){
            throw new Exception("No fue posible rechazar el Negocio. Verifica su estado previo.");
        }
        negocio.setEstadoRegistro(EstadoRegistro.RECHAZADO);
        negocioRepo.save(negocio);
    }

    @Override
    public void filtrarPorEstado(EstadoRegistro estadoRegistro) throws Exception {
        /**
         * Crear consulta que filtre dado el estado que llegue por parametro
         */
        
    }
}
