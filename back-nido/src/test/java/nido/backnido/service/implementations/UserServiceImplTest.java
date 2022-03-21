package nido.backnido.service.implementations;

import nido.backnido.entity.*;
import nido.backnido.exception.CustomBaseException;
import nido.backnido.repository.CategoryRepository;
import nido.backnido.repository.UserRepository;
import nido.backnido.service.RoleService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder bcryptEncoder;
    @Mock
    private RoleService roleService;


    private final Role role = new Role();
    private final Set<Reserve> reserveSet = new HashSet<>();

    @Test
    public void saveUserTest_Ok(){
        User user = new User(1L,"name test", "surname test", "email@gmail.com","passwordtest", true,role,reserveSet, true);
        User userResponse = new User(1L,"name test", "surname test", "email@gmail.com","passwordtest", true,role,reserveSet, true);

        when(userRepository.save(user)).thenReturn(userResponse);
        userService.create(user);

        verify(userRepository).save(user);
        assertEquals(user.getEmail(),userResponse.getEmail());
        assertEquals(1L, userResponse.getUserId());
    }

    @Test
    public void updateUserTest_Ok(){
        User userDB = new User(1L,"name test", "surname test", "email@gmail.com","passwordtest", true,role,reserveSet, true);
        User userNew = new User(1L,"name test", "surname test", "email@gmail.com","passwordtest", true,role,reserveSet, true);
        User userResponse = new User(1L,"name test", "surname test", "email@gmail.com","passwordtest", true,role,reserveSet, true);

        when(userRepository.findById(userDB.getUserId())).thenReturn(Optional.of(userResponse));
        userService.update(userNew);

        verify(userRepository).save(userNew);
        assertEquals(userNew.getEmail(), userResponse.getEmail());

    }

    @Test
    public void deleteUserTest_Ok(){
        User userDB = new User(1L,"name test", "surname test", "email@gmail.com","passwordtest", true,role,reserveSet, true);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userDB));
        doNothing().when(userRepository).deleteById(userDB.getUserId());
        userService.delete(1L);

        verify(userRepository, times(1)).softDelete(userDB.getUserId());
        verify(userRepository).findById(anyLong());
    }

    @Test
    public void getUserByIdTest_Ok(){
        User user = new User(1L,"name test", "surname test", "email@gmail.com","passwordtest", true,role,reserveSet, true);
        User userResponse = new User(1L,"name test", "surname test", "email@gmail.com","passwordtest", true,role,reserveSet, true);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userResponse));
        userService.getById(1L);

        verify(userRepository).findById(user.getUserId());
        assertEquals(user.getEmail(), userResponse.getEmail());
        assertEquals(1L, userResponse.getUserId());

    }

    @Test
    public void getUserByIdException(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomBaseException.class,()->{
            userService.getById(1L);
        });

    }

    @Test
    public void updateUserByIdException(){
        User user = new User();
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomBaseException.class,()->{
            userService.update(user);
        });

    }

    @Test
    public void deleteUserByIdException(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomBaseException.class,()->{
            userService.delete(1L);
        });

    }

    @Test
    public void findUserByEmailTest_Ok(){
        User user = new User(1L,"name test", "surname test", "email@gmail.com","passwordtest", true,role,reserveSet, true);
        User userResponse = new User(1L,"name test", "surname test", "email@gmail.com","passwordtest", true,role,reserveSet, true);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userResponse));
        userService.findByEmail("email@gmail.com");

        verify(userRepository).findByEmail(user.getEmail());
        assertEquals(user.getEmail(), userResponse.getEmail());
        assertEquals(1L, userResponse.getUserId());
    }

}
