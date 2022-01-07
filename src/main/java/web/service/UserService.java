package web.service;

import org.springframework.stereotype.Service;
import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Set;

@Service
public interface UserService {
    void addUser(User user);

    void updateUser(User user);

    void removeUser(Long id);

    User getUserById(Long id);

    List<User> listUser();

    Set<Role> getSetOfRole(List<String> id);

    User findUserByName(String username);

}
