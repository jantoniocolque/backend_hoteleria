package nido.backnido.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "features")
@Where(clause = "active = true")
@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long featureId;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String icon;

    @ManyToMany(mappedBy = "features", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Product> products;

    @Column(name = "active")
    private Boolean active = true;

}
