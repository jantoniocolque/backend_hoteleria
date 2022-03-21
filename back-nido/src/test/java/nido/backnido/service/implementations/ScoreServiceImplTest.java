package nido.backnido.service.implementations;

import nido.backnido.entity.*;
import nido.backnido.entity.dto.ScoreDTO;
import nido.backnido.exception.CustomBaseException;
import nido.backnido.repository.ScoreRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ScoreServiceImplTest {

    @InjectMocks
    private ScoreServiceImpl scoreService;
    @Mock
    private ScoreRepository scoreRepository;

    @Test
    public void saveScoreTest_Ok(){
        Product product = new Product();
        User user = new User();
        List<Score> scoreList = new ArrayList<>();

        ScoreDTO score = new ScoreDTO(null,4, user, product);
        Score scoreResponse = new Score(1L, 4,user, product);

        for(Score s : scoreRepository.findAll()) {
            if(s.getProduct().getProductId() == score.getProduct().getProductId() && s.getUser().getUserId() == score.getUser().getUserId()) {
                score.setScoreId(s.getScoreId());
                scoreRepository.save(s);
            }
            when(scoreRepository.save(s)).thenReturn(scoreResponse);
            scoreService.create(score);

            verify(scoreRepository).save(s);
            assertEquals(score.getProduct(),scoreResponse.getProduct());
            assertEquals(1L, scoreResponse.getScoreId());
        }
    }

    @Test
    public void saveScoreTest_Null(){
        when(scoreRepository.save(any())).thenReturn(null);
        scoreService.create(null);
        verify(scoreRepository, times(0)).save(any());
    }

    @Test
    public void updateScoreTest_Ok(){
        Product product = new Product();
        User user = new User();
        List<Score> scoreList = new ArrayList<>();

        Score scoreDB = new Score(1L, 4,user, product);
        ScoreDTO scoreNew = new ScoreDTO(null,4, user, product);
        Score scoreResponse = new Score(1L, 4,user, product);

        for(Score s : scoreRepository.findAll()) {
            if(s.getProduct().getProductId() == scoreDB.getProduct().getProductId() && s.getUser().getUserId() == scoreDB.getUser().getUserId()) {
                scoreDB.setScoreId(s.getScoreId());
                scoreRepository.save(s);
            }
        when(scoreRepository.findById(scoreDB.getScoreId())).thenReturn(Optional.of(scoreResponse));
        scoreService.update(scoreNew);

        verify(scoreRepository).save(s);
        assertEquals(scoreNew.getScore(), scoreResponse.getScore());
        }

    }

    @Test
    public void deleteScoreTest_Ok(){
        Product product = new Product();
        User user = new User();
        Score scoreDB = new Score(1L, 4,user, product);

        when(scoreRepository.findById(anyLong())).thenReturn(Optional.of(scoreDB));
        doNothing().when(scoreRepository).deleteById(scoreDB.getScoreId());
        scoreService.delete(1L);

        verify(scoreRepository, times(1)).deleteById(scoreDB.getScoreId());
        verify(scoreRepository).findById(anyLong());
    }

    @Test
    public void getScoreByProductIdTest_Ok(){
         Location location = new Location();
         Category category = new Category();
         Set<Score> scores = new HashSet<>();
         Set<Feature> features = new HashSet<>();
         Set<Image> images = new HashSet<>();
         List<Score> scoreList = new ArrayList<>();

        Product product = new Product(1L, "name test", "description test", "subtitle test",
                "policy test", "rule test", "safety test", "adress test", 25.65, 35.88, location, category,images, scores, features, true);
        User user = new User();
        Score scoreDB = new Score(1L, 4,user, product);
        Score scoreResponse = new Score(1L, 4,user, product);
        scoreList.add(scoreResponse);

        when(scoreRepository.findByProduct_ProductId(anyLong())).thenReturn(scoreList);
        scoreService.getScoreByProductId(1L);

        verify(scoreRepository).findByProduct_ProductId(scoreDB.getScoreId());
        assertEquals(1, scoreList.size());

    }

    @Test
    public void updateScoreByIdException(){
        ScoreDTO score = new ScoreDTO();
        when(scoreRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomBaseException.class,()->{
            scoreService.update(score);
        });

    }

    @Test
    public void deleteScoreByIdException(){
        when(scoreRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomBaseException.class,()->{
            scoreService.delete(1L);
        });

    }


}
