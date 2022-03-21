package nido.backnido.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {
	private Long locationId;
    private String city;
    private String country;
    @JsonIgnore
    private Set<ProductDTO> products;

}
