package nido.backnido.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity
@Table(name = "favorites")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favorite {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long favoriteId;
	
	@ManyToOne
    @JoinColumn(name="users_user_id", referencedColumnName = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name="products_product_id", referencedColumnName = "productId")
    private Product product;
}
