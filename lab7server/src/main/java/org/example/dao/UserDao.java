package org.example.dao;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.Cleanup;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.example.entity.QUser;
import org.example.entity.User;
import org.example.managers.HibernateManager;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

import static org.example.entity.QUser.*;
@Repository
public class UserDao {
    private final SessionFactory sessionFactory;

    public UserDao(HibernateManager hibernateManager) {

        sessionFactory = hibernateManager.getConfiguration().buildSessionFactory();
    }

    public ImmutablePair<BigInteger,String> getPasswordAndSaltByLogin(String login) throws FailedTransactionException{
        @Cleanup var session = sessionFactory.openSession();
        if (login == null) return null;
        session.beginTransaction();
        try {
            var hashAndSalt=new JPAQuery<QUser>(session)
                    .select(user.password, user.salt)
                    .from(user)
                    .where(user.login.eq(login))
                    .fetchOne();
            session.getTransaction().commit();
            if (hashAndSalt == null) return null;
            var hash = hashAndSalt.get(0,BigInteger.class);
            var salt = hashAndSalt.get(1,String.class);
            return new ImmutablePair<BigInteger,String>(hash,salt);
        }catch (Exception e){
            session.getTransaction().rollback();
            throw new FailedTransactionException("Транзакция не удалась");
        }

    }
    public User insertUser(String login, BigInteger hash, String salt) throws FailedTransactionException{
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            User user = User.builder()
                    .login(login)
                    .password(hash)
                    .salt(salt)
                    .build();
            session.save(user);
            session.getTransaction().commit();
            return user;
        }catch (Exception e){
            session.getTransaction().rollback();
            throw new  FailedTransactionException("Транзакция не удалась");
        }



    }

    public List<User> findAll() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        try {

            var users=new JPAQuery<User>(session).
                    select(user)
                            .from(user)
                                    .fetch();
            session.getTransaction().commit();
            return users;
    }catch (Exception e){
        session.getTransaction().rollback();
        throw new  FailedTransactionException("Транзакция не удалась");
    }

}
}
