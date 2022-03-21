package nido.backnido.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String url;
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="products_product_id",referencedColumnName="productId")
    private Product product;

}
