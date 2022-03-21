package nido.backnido.service;

import nido.backnido.entity.Reserve;
import nido.backnido.entity.dto.ReserveDTO;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReserveService {

    List<ReserveDTO> getAll();
    ReserveDTO getById(Long id);
    List<ReserveDTO> findReservationsByProductId(Long productId);
    List<ReserveDTO> findReservationsByUserId(Long userId);
    void create(Reserve newReserve);
    void update(Reserve updatedReserve);
    void delete(Long id);
    void deleteAllByProductId(Long productId);
    void deleteAllByUserId(Long userId);

}
