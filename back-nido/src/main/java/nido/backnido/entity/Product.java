package nido.backnido.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Where(clause = "active = true")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

    private String subtitle;

    @NotNull
    @NotBlank
    @Size(max = 250 , message = "Este campo sólo acepta un máximo de 250 caracteres, por favor revisa")
    private String policy;

    @NotNull
    @NotBlank
    @Size(max = 250, message = "Este campo sólo acepta un máximo de 250 caracteres, por favor revisa")
    private String rule;

    @NotNull
    @NotBlank
    @Size(max = 250 , message = "Este campo sólo acepta un máximo de 250 caracteres, por favor revisa")
    private String safety;

    @NotNull
    @NotBlank
    private String address;
    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
    /*
    @NotNull
    private Boolean availability;

    @NotNull
    private Integer score;
*/

    @NotNull
    @ManyToOne
    @JoinColumn(name = "locations_location_id", referencedColumnName = "locationId")
    private Location location;

    @NotNull
    @ManyToOne
    @JoinColumn(name="categories_category_id", referencedColumnName = "categoryId")
    private Category category;
    
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Image> images;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Score> scores;

    @JoinTable(
            name = "products_has_features",
            joinColumns = { @JoinColumn(name = "products_product_id")},
            inverseJoinColumns = {@JoinColumn(name = "features_feature_id")}
    )
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Feature> features;

     @Column(name = "active")
    private Boolean active = true;


//    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
//    private Set<Reserve> reserves;

}
