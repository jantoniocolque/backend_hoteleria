package nido.backnido.service.implementations;

import nido.backnido.entity.Score;
import nido.backnido.entity.dto.ScoreDTO;
import nido.backnido.exception.CustomBaseException;
import nido.backnido.repository.ScoreRepository;
import nido.backnido.service.ProductService;
import nido.backnido.service.ScoreService;
import nido.backnido.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    ScoreRepository scoreRepository;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public void create(ScoreDTO score) {
        List<Score> scores = scoreRepository.findAll();
        for(Score s : scores) {
            if(s.getProduct().getProductId() == score.getProduct().getProductId() && s.getUser().getUserId() == score.getUser().getUserId()) {
                score.setScoreId(s.getScoreId());
                Score aux = modelMapper.map(score, Score.class);
                scoreRepository.save(aux);
            }
        }
        if (score != null && score.getScoreId() == null) {
            Score aux = modelMapper.map(score, Score.class);
            scoreRepository.save(aux);
        }
    }

    @Override
    public void update(ScoreDTO score) {
        if(score.getScoreId() != null) {
            scoreRepository.findById(score.getScoreId()).orElseThrow( () ->
                    new CustomBaseException("Puntuación no encontrada, por favor compruebe", HttpStatus.NOT_FOUND.value()));
        } else {
            throw new CustomBaseException("El id de la puntuación no puede estar vacío, por favor compruebe", HttpStatus.BAD_REQUEST.value());
        }
        Score aux = modelMapper.map(score, Score.class);
        scoreRepository.save(aux);
    }

    @Override
    public void delete(Long id) {
        scoreRepository.findById(id).orElseThrow( () ->
                new CustomBaseException("Puntuación con el id: " + id + " no encontrada, por favor compruebe", HttpStatus.BAD_REQUEST.value()));
        scoreRepository.deleteById(id);
    }

    @Override
    public double getAverageProductScore(Long productId) {
        try {
            return scoreRepository.getAverageProductScore(productId);
        }catch (AopInvocationException e){
            throw new CustomBaseException("Error el producto no tiene puntuaciones", HttpStatus.BAD_REQUEST.value());
        }catch (Exception exception){
            throw new CustomBaseException("Error al buscar producto, verifique la información", HttpStatus.BAD_REQUEST.value());
        }
    }

	@Override
	public List<Score> getScoreByProductId(Long productId) {
		return scoreRepository.findByProduct_ProductId(productId);
	}
}
