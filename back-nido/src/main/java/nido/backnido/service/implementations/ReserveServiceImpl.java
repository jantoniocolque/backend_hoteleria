package nido.backnido.service.implementations;

import nido.backnido.entity.Reserve;
import nido.backnido.entity.dto.ReserveDTO;
import nido.backnido.exception.CustomBaseException;
import nido.backnido.repository.ProductRepository;
import nido.backnido.repository.ReserveRepository;
import nido.backnido.repository.ScoreRepository;
import nido.backnido.repository.UserRepository;
import nido.backnido.service.ImageService;
import nido.backnido.service.ReserveService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReserveServiceImpl implements ReserveService {

    @Autowired
    private ReserveRepository reserveRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ScoreRepository scoreRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageService imageService;

    ModelMapper modelMapper = new ModelMapper();


    @Override
    public List<ReserveDTO> getAll() {
        // Response de entidades
        List<Reserve> entityResponse = reserveRepository.findAll();
        // Acá voy guardando los dto para devolverlos al front
        List<ReserveDTO> dtoResponse = new ArrayList<>();


        for (Reserve reserve : entityResponse) {
            // Variables que se re-definen y son necesarias para cada ciclo
            ReserveDTO reservedto = modelMapper.map(reserve, ReserveDTO.class);
            Long productId = reservedto.getProduct().getProductId();

            // Le paso al DTO los valores de la entidad
            reservedto.getProduct().setScore(productRepository.getById(productId).getScores());
            reservedto.getProduct().setImages(imageService.findByProductId(reserve.getProduct()));
            reservedto.getProduct().setAvgScore(scoreRepository.getAverageProductScore(productId));

            dtoResponse.add(reservedto);

        }

        return dtoResponse;

    }

    @Override
    public ReserveDTO getById(Long id) {
        Reserve response = reserveRepository.findById(id).orElseThrow(() ->
                new CustomBaseException("Reserva no encontrada, por favor compruebe", HttpStatus.NOT_FOUND.value())
        );

        // Acá voy guardando los dto para devolverlos al front
        ReserveDTO dtoRes = modelMapper.map(response, ReserveDTO.class);
        Long productId = dtoRes.getProduct().getProductId();

        // Le paso al DTO los valores de la entidad
        dtoRes.getProduct().setScore(productRepository.getById(productId).getScores());
        dtoRes.getProduct().setImages(imageService.findByProductId(response.getProduct()));
        dtoRes.getProduct().setAvgScore(scoreRepository.getAverageProductScore(productId));

        return dtoRes;
    }

    @Override
    public List<ReserveDTO> findReservationsByProductId(Long productId) {
        // Response de entidad
        List<Reserve> entityResponse = reserveRepository.findReservationsByProductId(productId);
        // Guardo los dto
        List<ReserveDTO> dtoResponse = new ArrayList<>();

        for (Reserve reserve : entityResponse) {
            // Variables que se re-definen y son necesarias para cada ciclo
            ReserveDTO reservedto = modelMapper.map(reserve, ReserveDTO.class);

            // Le paso al DTO los valores de la entidad
            reservedto.getProduct().setScore(productRepository.getById(productId).getScores());
            reservedto.getProduct().setImages(imageService.findByProductId(reserve.getProduct()));
            reservedto.getProduct().setAvgScore(scoreRepository.getAverageProductScore(productId));

            dtoResponse.add(reservedto);
        }

        return dtoResponse;
    }

    @Override
    public List<ReserveDTO> findReservationsByUserId(Long userId) {
        // Response de entidad
        List<Reserve> entityResponse = reserveRepository.findReservationsByUserId(userId);
        // Guardo los dto
        List<ReserveDTO> dtoResponse = new ArrayList<>();

        for (Reserve reserve : entityResponse) {

            // Variables que se re-definen y son necesarias para cada ciclo
            ReserveDTO reservedto = modelMapper.map(reserve, ReserveDTO.class);
            Long productId = reservedto.getProduct().getProductId();

            // Le paso al DTO los valores de la entidad
            reservedto.getProduct().setScore(productRepository.getById(productId).getScores());
            reservedto.getProduct().setImages(imageService.findByProductId(reserve.getProduct()));
            reservedto.getProduct().setAvgScore(scoreRepository.getAverageProductScore(productId));

            dtoResponse.add(reservedto);

        }

        return dtoResponse;
    }

    @Override
    public void create(Reserve newReserve) {
        Optional<Reserve> queryResponse = reserveRepository.checkAvailability(newReserve.getDateIn(), newReserve.getDateOut(),newReserve.getProduct().getProductId());
        if (queryResponse.isEmpty()) {
            reserveRepository.save(newReserve);
        } else {
            throw new CustomBaseException("Ya existe una reserva en esas fechas, por favor ingrese una fecha disponible", HttpStatus.BAD_REQUEST.value());
        }

    }

    @Override
    public void update(Reserve updatedReserve) {
    // Comprobación básica para saber que el id está presente y que la reserva existe
        if (updatedReserve.getReservationId() != null) {
            reserveRepository.findById(updatedReserve.getReservationId()).orElseThrow(() ->
                    new CustomBaseException("Reserva no encontrada, por favor compruebe", HttpStatus.NOT_FOUND.value())
            );
        } else {
            throw new CustomBaseException("El id de la reserva no puede estar vacío, por favor compruebe", HttpStatus.BAD_REQUEST.value());
        }

        // Corroborando que no se hayan cambiado: userId, productId. Si algún dato está modificado se arroja una excepción

        Reserve originalReserve = reserveRepository.getById(updatedReserve.getReservationId());

        if(originalReserve.getProduct().getProductId() == updatedReserve.getProduct().getProductId() && originalReserve.getUser().getUserId() == updatedReserve.getUser().getUserId()) {

            // Verifica si la fecha está disponible y lo guardo. Caso contrario arroja excepción
        	reserveRepository.softDelete(originalReserve.getReservationId());
            if(reserveRepository.checkAvailability(updatedReserve.getDateIn(), updatedReserve.getDateOut(),updatedReserve.getProduct().getProductId()).isEmpty()) {

                reserveRepository.save(updatedReserve);

            } else throw new CustomBaseException("La fecha elegida no está disponible", HttpStatus.BAD_REQUEST.value());

        }
        else if(originalReserve.getProduct().getProductId() != updatedReserve.getProduct().getProductId() && originalReserve.getUser().getUserId() != updatedReserve.getUser().getUserId()){
            throw new CustomBaseException("No se puede cambiar el producto ni el usuario en una reserva existente", HttpStatus.BAD_REQUEST.value());
        }
        else if (originalReserve.getProduct().getProductId() != updatedReserve.getProduct().getProductId()) {
            throw new CustomBaseException("No se puede cambiar el producto en una reserva existente", HttpStatus.BAD_REQUEST.value());
        }
        else if (originalReserve.getUser().getUserId() != updatedReserve.getUser().getUserId()) {
            throw new CustomBaseException("No se puede cambiar el usuario en una reserva existente", HttpStatus.BAD_REQUEST.value());
        }
    }

    @Override
    public void delete(Long id) {
        reserveRepository.findById(id).orElseThrow(() ->
                new CustomBaseException("Reserva con el id: " + id + " no encontrada por favor compruebe el id e intente nuevamente ", HttpStatus.NOT_FOUND.value())
        );
        reserveRepository.softDelete(id);
    }

    @Override
    public void deleteAllByProductId(Long productId) {
        productRepository.findById(productId).orElseThrow(() ->
                new CustomBaseException("Producto con el id: " + productId + " no encontrado por favor compruebe el id e intente nuevamente", HttpStatus.NOT_FOUND.value())
        );
        reserveRepository.softDeleteAllByProductId(productId);
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(() ->
                new CustomBaseException("Usuario con el id: " + userId + " no encontrado por favor compruebe el id e intente nuevamente", HttpStatus.NOT_FOUND.value())
        );
        reserveRepository.softDeleteAllByUserId(userId);
    }


}
