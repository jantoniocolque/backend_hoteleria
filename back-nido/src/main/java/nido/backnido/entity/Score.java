package nido.backnido.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "scores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreId;

    @NotNull
    @Min(value = 1, message = "El puntaje minimo es 1")
    @Max(value = 5, message = "El puntaje máximo es 5")
    private int score;
    
    @NotNull
    @OneToOne
    @JsonIgnore
    @JoinColumn(name="users_user_id",referencedColumnName = "userId")
    private User user;
    
    @NotNull
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "products_product_id", referencedColumnName = "productId")
    private Product product;

}
