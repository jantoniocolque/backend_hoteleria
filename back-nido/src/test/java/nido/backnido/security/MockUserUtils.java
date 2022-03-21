package nido.backnido.security;


import nido.backnido.entity.Role;
import nido.backnido.entity.User;
public class MockUserUtils {

    private MockUserUtils() {
    }

    public static User getMockUser(String username) {
        User user = new User();
        user.setUserId(1L);
        user.setEmail(username);
        user.setPassword("secret");
        Role role = new Role();
        role.setName("ADMIN");
        user.setRole(role);
        user.setActive(true);
        user.setValidated(true);
        return user;
    }
}
