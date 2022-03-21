package nido.backnido.entity;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reserves")
@Where(clause = "active = true")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Reserve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @NotNull
    private LocalDate dateIn;

    @NotNull
    private LocalDate dateOut;

    @NotNull
    private LocalTime hourIn;

    private Boolean covid;

    private String city;

    @Size(max = 180, message = "Este campo sólo acepta un máximo de 180 caracteres, por favor revisa")
    private String info;

    @ManyToOne
    @JoinColumn(name="users_user_id", referencedColumnName = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name="products_product_id", referencedColumnName = "productId")
    private Product product;

    @Column(name = "active")
    private Boolean active = true;


}
