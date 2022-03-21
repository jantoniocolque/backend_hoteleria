package nido.backnido.entity.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {
	private Long categoryId;
    private String title;
    private String description;
    private String urlImage;

}
