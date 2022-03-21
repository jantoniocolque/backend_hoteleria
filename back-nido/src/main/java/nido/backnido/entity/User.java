package nido.backnido.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users")
@Where(clause = "active = true")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String surname;

    @NotNull
    @NotBlank
    @Email(message = "Error de formato de correo electrónico")
    @Column(unique = true)
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 6, message = "La contraseña debe tener más de 6 caracteres")
    @ToString.Exclude
    private String password;

    private boolean validated;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "roles_role_id", referencedColumnName = "roleId")
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Reserve> reservations;

    @Column(name = "active", columnDefinition = "boolean DEFAULT 'true'")
    private Boolean active;

}
