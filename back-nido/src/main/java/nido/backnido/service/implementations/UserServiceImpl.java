package nido.backnido.service.implementations;

import net.bytebuddy.asm.Advice;
import nido.backnido.configuration.TokenProvider;
import nido.backnido.entity.Role;
import nido.backnido.entity.User;
import nido.backnido.entity.dto.AuthTokenDTO;
import nido.backnido.entity.dto.UserDTO;
import nido.backnido.exception.CustomBaseException;
import nido.backnido.repository.UserRepository;
import nido.backnido.service.RoleService;
import nido.backnido.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    RoleService roleService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    ModelMapper modelMapper = new ModelMapper();


    @Override
    public List<UserDTO> getAll() {
        List<UserDTO> userResponse = new ArrayList<>();

        for (User user : userRepository.findAll()) {
            UserDTO userdto = modelMapper.map(user, UserDTO.class);
            userResponse.add(userdto);
        }

        return userResponse;
    }

    @Override
    public UserDTO getById(Long id) {
        User response = userRepository.findById(id).orElseThrow(() ->
                new CustomBaseException("Usuario no encontrado, por favor compruebe", HttpStatus.BAD_REQUEST.value())
        );

        return modelMapper.map(response, UserDTO.class);

    }

    @Override
    public void create(User newUser) {
        if (newUser != null) {
            newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
            Role role;

            if (newUser.getEmail().split("@")[1].equals("admin.nido")) {
                role = roleService.findRoleByName("ADMIN");
            } else {
                role = roleService.findRoleByName("USER");
            }

            newUser.setRole(role);

            userRepository.save(newUser);
        }
    }

    @Override
    public void update(User updatedUser) {
        if (updatedUser.getUserId() != null) {
            userRepository.findById(updatedUser.getUserId()).orElseThrow(() ->
                    new CustomBaseException("Usuario no encontrado, por favor compruebe", HttpStatus.NOT_FOUND.value())
            );
        } else {
            throw new CustomBaseException("El id del usuario no puede estar vacio, por favor compruebe", HttpStatus.BAD_REQUEST.value());
        }
        userRepository.save(updatedUser);
    }

    @Override
    public void delete(Long id) {
        userRepository.findById(id).orElseThrow(() ->
                new CustomBaseException("Usuario con el id: " + id + " no encontrado por favor compruebe el id e intente nuevamente ", HttpStatus.NOT_FOUND.value())
        );
        userRepository.softDelete(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new CustomBaseException("Email no encontrado, por favor compruebe la información e intente nuevamente", HttpStatus.NOT_FOUND.value()));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
            new CustomBaseException("Email no encontrado, por favor revise e intente nuevamente", HttpStatus.NOT_FOUND.value()));
        if (user == null) {
            throw new UsernameNotFoundException("Usuario o contraseña invalida.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }


    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));
        return authorities;
    }

}
