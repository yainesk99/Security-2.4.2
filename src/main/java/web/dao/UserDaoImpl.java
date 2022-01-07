package web.dao;

import org.springframework.stereotype.Component;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class UserDaoImpl implements UserDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void removeUser(Long id) {
        entityManager.remove(getUserById(id));
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> listUser() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User findUserByName(String username) {
        User user = entityManager.createQuery(
                "SELECT u from User u WHERE u.username = :username", User.class).
                setParameter("username", username).getSingleResult();
        return user;
    }
}
