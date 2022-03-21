package nido.backnido.service.implementations;

import nido.backnido.entity.Feature;
import nido.backnido.entity.Product;
import nido.backnido.entity.User;
import nido.backnido.exception.CustomBaseException;
import nido.backnido.repository.FeatureRepository;
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
public class FeatureServiceImplTest {

    @InjectMocks
    private FeatureServiceImpl featureService;
    @Mock
    private FeatureRepository featureRepository;

    final Set<Product> product = new HashSet<>();

    @Test
    public void saveFeatureTest_Ok(){
        Feature feature = new Feature(null,"name feature test", "icon feature test", product, true);
        Feature featureResponse = new Feature(1L,"name feature test", "icon feature test", product, true);

        when(featureRepository.save(feature)).thenReturn(featureResponse);
        featureService.create(feature);

        verify(featureRepository).save(feature);
        assertEquals(feature.getName(),featureResponse.getName());
        assertEquals(1L, featureResponse.getFeatureId());
    }

    @Test
    public void saveFeatureTest_Null(){
        when(featureRepository.save(any())).thenReturn(null);
        featureService.create(null);
        verify(featureRepository, times(0)).save(any());
    }

    @Test
    public void getAllFeatureTest_Ok(){
        Feature feature = new Feature(null,"name feature test", "icon feature test", product, true);
        List<Feature> featureList = new ArrayList<>();
        featureList.add(feature);

        when(featureRepository.findAll()).thenReturn(featureList);
        featureService.getAll();

        verify(featureRepository).findAll();
        assertEquals(1, featureList.size());
    }

    @Test
    public void getFeatureByIdTest_Ok(){
        Feature feature = new Feature(1L,"name feature test", "icon feature test", product, true);
        Feature featureResponse = new Feature(1L,"name feature test", "icon feature test", product, true);

        when(featureRepository.findById(anyLong())).thenReturn(Optional.of(featureResponse));
        featureService.getById(1L);

        verify(featureRepository).findById(feature.getFeatureId());
        assertEquals(feature.getName(), featureResponse.getName());
        assertEquals(1L, featureResponse.getFeatureId());

    }

    @Test
    public void updateFeatureTest_Ok(){
        Feature featureDB = new Feature(1L,"name feature test", "icon feature test", product, true);
        Feature featureNew = new Feature(1L,"name modify test", "icon feature test", product, true);
        Feature featureResponse = new Feature(1L,"name modify test", "icon feature test", product, true);

        when(featureRepository.findById(featureDB.getFeatureId())).thenReturn(Optional.of(featureResponse));
        featureService.update(featureNew);

        verify(featureRepository).save(featureNew);
        assertEquals(featureNew.getName(), featureResponse.getName());

    }

    @Test
    public void deleteFeatureTest_Ok(){
        Feature featureDB = new Feature(1L,"name feature test", "icon feature test", product, true);

        when(featureRepository.findById(anyLong())).thenReturn(Optional.of(featureDB));
        doNothing().when(featureRepository).deleteById(featureDB.getFeatureId());
        featureService.delete(1L);

        verify(featureRepository, times(1)).softDelete(featureDB.getFeatureId());
        verify(featureRepository).findById(anyLong());
    }

    @Test
    public void getFeatureByIdException(){
        when(featureRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomBaseException.class,()->{
            featureService.getById(1L);
        });

    }

    @Test
    public void updateFeatureByIdException(){
        Feature feature = new Feature();
        when(featureRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomBaseException.class,()->{
            featureService.update(feature);
        });

    }

    @Test
    public void deleteFeatureByIdException(){
        when(featureRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomBaseException.class,()->{
            featureService.delete(1L);
        });

    }



}
