package nido.backnido.service.implementations;

import nido.backnido.entity.Feature;
import nido.backnido.entity.Image;
import nido.backnido.entity.Product;
import nido.backnido.entity.User;
import nido.backnido.exception.CustomBaseException;
import nido.backnido.repository.FeatureRepository;
import nido.backnido.repository.ImageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ImageServiceImplTest {
    @InjectMocks
    private ImageServiceImpl imageService;
    @Mock
    private ImageRepository imageRepository;

    final Product product = new Product();

    @Test
    public void saveImageTest_Ok(){
        Image image = new Image(null,"title image test", "urlimagetest.jpg", product);
        Image imageResponse = new Image(1L,"title image test", "urlimagetest.jpg", product);

        when(imageRepository.save(image)).thenReturn(imageResponse);
        imageService.create(image);

        verify(imageRepository).save(image);
        assertEquals(image.getTitle(),imageResponse.getTitle());
        assertEquals(1L, imageResponse.getImageId());
    }

    @Test
    public void saveImageTest_Null(){
        when(imageRepository.save(any())).thenReturn(null);
        imageService.create(null);
        verify(imageRepository, times(0)).save(any());
    }

    @Test
    public void getAllImageTest_Ok(){
        Image image = new Image(null,"title image test", "urlimagetest.jpg", product);
        List<Image> imageList = new ArrayList<>();
        imageList.add(image);

        when(imageRepository.findAll()).thenReturn(imageList);
        imageService.getAll();

        verify(imageRepository).findAll();
        assertEquals(1, imageList.size());
    }

    @Test
    public void getImageByIdTest_Ok(){
        Image image = new Image(1L,"title image test", "urlimagetest.jpg", product);
        Image imageResponse = new Image(1L,"title image test", "urlimagetest.jpg", product);

        when(imageRepository.getById(anyLong())).thenReturn(imageResponse);
        imageService.getById(1L);

        verify(imageRepository).getById(image.getImageId());
        assertEquals(image.getUrl(), imageResponse.getUrl());
        assertEquals(1L, imageResponse.getImageId());

    }

    @Test
    public void updateImageTest_Ok(){
        Image imageDB = new Image(1L,"title image test", "urlimagetest.jpg", product);
        Image imageNew = new Image(1L,"title test", "urlimagetest.jpg", product);
        Image imageResponse = new Image(1L,"title test", "urlimagetest.jpg", product);

        when(imageRepository.findById(imageDB.getImageId())).thenReturn(Optional.of(imageResponse));
        imageService.update(imageNew);

        verify(imageRepository).save(imageNew);
        assertEquals(imageNew.getTitle(), imageResponse.getTitle());

    }

    @Test
    public void deleteImageTest_Ok(){
        Image imageDB = new Image(1L,"title image test", "urlimagetest.jpg", product);

        when(imageRepository.findById(anyLong())).thenReturn(Optional.of(imageDB));
        doNothing().when(imageRepository).deleteById(imageDB.getImageId());
        imageService.delete(1L);

        verify(imageRepository, times(1)).deleteById(imageDB.getImageId());
        verify(imageRepository).findById(anyLong());
    }

    @Test
    public void createImageByIdException(){
        Image image = new Image();
        when(imageRepository.save(any())).thenReturn(Optional.empty());

        assertThrows(CustomBaseException.class,()->{
            imageService.create(image);
        });

    }

    @Test
    public void updateImageByIdException(){
        Image image = new Image();
        when(imageRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomBaseException.class,()->{
            imageService.update(image);
        });

    }

    @Test
    public void deleteImageByIdException(){
        when(imageRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomBaseException.class,()->{
            imageService.delete(1L);
        });

    }


}
