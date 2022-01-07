package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import web.dao.UserDao;
import web.model.MyUserDetails;
import web.model.Role;
import web.model.User;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserDaoAndEncoder(UserDao userDao,
                                     PasswordEncoder passwordEncoder,
                                     RoleService roleService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public void addUser(User user) {

        userDao.addUser(user);
    }



    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void removeUser(Long id) {
        userDao.removeUser(id);
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public List<User> listUser() {
        return userDao.listUser();
    }

    @Override
    public Set<Role> getSetOfRole(List<String> rolesId) {
        Set<Role> roleSet = new HashSet<>();
        for (String id : rolesId) {
            roleSet.add(roleService.getRoleById(Long.parseLong(id)));
        }
        return roleSet;
    }

    @Override
    public User findUserByName(String username) {
        return userDao.findUserByName(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(
                    "No user found with username: " + username);
        }
        Set<Role> roleSet = user.getRoles();
        return new MyUserDetails(user, roleSet);
    }
}
