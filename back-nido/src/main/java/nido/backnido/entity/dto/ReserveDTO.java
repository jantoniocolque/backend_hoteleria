package nido.backnido.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDTO {

    private Long reservationId;
    private LocalDate dateIn;
    private LocalDate dateOut;
    private LocalTime hourIn;
    private Boolean covid;
    private String city;
    private String info;
    private UserDTO user;
    private ProductDTO product;

}
