package nido.backnido.service.implementations;

import nido.backnido.entity.*;
import nido.backnido.entity.dto.FavoriteDTO;
import nido.backnido.entity.dto.ProductDTO;
import nido.backnido.entity.dto.ReserveDTO;
import nido.backnido.entity.dto.UserDTO;
import nido.backnido.exception.CustomBaseException;
import nido.backnido.repository.ProductRepository;
import nido.backnido.repository.ReserveRepository;
import nido.backnido.repository.ScoreRepository;
import nido.backnido.service.ImageService;
import nido.backnido.service.ProductService;
import nido.backnido.service.ScoreService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReserveServiceImplTest {

    @InjectMocks
    private ReserveServiceImpl reserveService;
    @Mock
    private ReserveRepository reserveRepository;
    @Mock
    private ImageService imageService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ScoreRepository scoreRepository;

    @Test
    public void saveReserveTest_Ok(){
        Product product = new Product();
        User user = new User();

        Reserve reserve = new Reserve(1L,
                LocalDate.of(2021,12,10),
                LocalDate.of(2021, 12, 12),
                LocalTime.of(1,30,20),
                true,"City",
                "Info test",
                user,
                product,
                true);
        Reserve reserveResponse = new Reserve( 1L,LocalDate.of(2021,12,10),
                LocalDate.of(2021,12,12),
                LocalTime.of(1,30,20),
                true,"City",
                "Info test",
                user,product,true);

        when(reserveRepository.save(reserve)).thenReturn(reserveResponse);

        reserveService.create(reserve);

        verify(reserveRepository).save(reserve);
        assertEquals(reserve.getDateIn(),reserveResponse.getDateIn());
        assertEquals(1L, reserveResponse.getReservationId());
    }

    @Test
    public void getAllReservesTest_Ok(){
        ProductDTO product = new ProductDTO();
        UserDTO user = new UserDTO();

        ReserveDTO reserve = new ReserveDTO(1L,LocalDate.of(2021,12,10),
                LocalDate.of(2021,12,12),
                LocalTime.of(1,30,20),true,"City", "info test", user, product);
        List<Reserve> reserveList = new ArrayList<>();

        for(Reserve reserve1: reserveRepository.findAll()) {
            reserve.getProduct().setImages(imageService.findByProductId(reserve1.getProduct()));
            reserveList.add(reserve1);
            when(reserveRepository.findAll()).thenReturn(reserveList);
            reserveService.getAll();
            verify(reserveRepository).findAll();
            assertEquals(1, reserveList.size());
        }
    }

    @Test
    public void deleteReserveTest_Ok(){
        Product product = new Product();
        User user = new User();

        Reserve reserveDB = new Reserve(1L,LocalDate.of(2021,12,10),
                LocalDate.of(2021,12,12),
                LocalTime.of(1,30,20),true,"City", "info test", user, product, true);

        when(reserveRepository.findById(anyLong())).thenReturn(Optional.of(reserveDB));
        doNothing().when(reserveRepository).deleteById(reserveDB.getReservationId());
        reserveService.delete(1L);

        verify(reserveRepository, times(1)).softDelete(reserveDB.getReservationId());
        verify(reserveRepository).findById(anyLong());
    }

     @Test
    public void deleteReserveByIdException(){
        when(reserveRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomBaseException.class,()->{
            reserveService.delete(1L);
        });

    }

}
