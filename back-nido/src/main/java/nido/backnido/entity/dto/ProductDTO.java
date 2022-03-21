package nido.backnido.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nido.backnido.entity.*;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDTO {
	private Long productId;
    private String name;
    private String description;
    private String subtitle;
    private String policy;
    private String rule;
    private String safety;
    private String address;
    private Double latitude;
    private Double longitude;
    private Location location;
    private Category category;
    private Double avgScore = 0.0;
    private Set<Image> images;
    private Set<Score> score;
    private Set<Feature> features;


}
