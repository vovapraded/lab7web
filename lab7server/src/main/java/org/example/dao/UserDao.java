package org.example.dao;

import com.querydsl.jpa.impl.JPAInsertClause;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.Cleanup;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.example.entity.QUser;
import org.example.entity.User;
import org.example.managers.HibernateManager;
import org.hibernate.SessionFactory;

import static org.example.entity.QUser.*;

public class UserDao {
    private final SessionFactory sessionFactory;

    public UserDao() {

        sessionFactory = HibernateManager.getConfiguration().buildSessionFactory();
    }

    public ImmutablePair<String,String> getPasswordAndSaltByLogin(String login){
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        if (login == null) return null;
        var hashAndSalt=new JPAQuery<QUser>(session)
                .select(user.password, user.salt)
                .from(user)
                .where(user.login.eq(login))
                .fetchOne();
        session.getTransaction().commit();
        if (hashAndSalt == null) return null;
        var hash = hashAndSalt.get(0,String.class);
        var salt = hashAndSalt.get(1,String.class);
        return new ImmutablePair<String,String>(hash,salt);
    }
    public void insertUser(String login, String hash, String salt){
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        User user = User.builder()
                .login(login)
                .password(hash)
                .salt(salt)
                .build();
        session.save(user);
        session.getTransaction().commit();

    }
}
