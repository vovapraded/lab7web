package org.example.dao;

import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.Cleanup;
import org.common.commands.authorization.NoAccessException;
import org.common.dao.interfaces.CollectionInDatabaseManager;
import org.common.dto.Ticket;
import org.example.managers.HibernateManager;
import org.hibernate.SessionFactory;

import java.util.HashMap;
import java.util.stream.Collectors;

import static org.common.dto.QTicket.ticket;

public class TicketDao implements CollectionInDatabaseManager {
    private final SessionFactory sessionFactory;

    public TicketDao() {

        sessionFactory = HibernateManager.getConfiguration().buildSessionFactory();
    }

    @Override
    public HashMap<Long, Ticket> loadCollection(){
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        HashMap <Long,Ticket> ticketHashMap = (HashMap<Long, Ticket>) new JPAQuery<Ticket>(session)
                 .select(ticket)
                 .from(ticket)
                 .fetch().stream()
                .collect(Collectors.toMap(
                        ticket -> ticket.getId(),
                        // Функция для извлечения значения
                        ticket -> ticket
                ));
        session.getTransaction().commit();
        return ticketHashMap;
    }

    @Override
    public void clear(String login) {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        new JPADeleteClause(session,ticket)
                .where(ticket.createdBy.eq(login))
                .execute();
        session.getTransaction().commit();
    }

    @Override
    public void insert(Ticket ticket) {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(ticket);
        session.getTransaction().commit();
    }
    @Override
    public void update(Ticket newTicket, String login) {
        removeTicket(newTicket.getId(),login);
        insert(newTicket);
    }
    @Override
    public void removeTicket(Ticket tick) {
        @Cleanup var session = sessionFactory.openSession();
            session.beginTransaction();
        new JPADeleteClause(session,ticket)
                .where(ticket.id.eq(tick.getId()))
                .execute();
            session.getTransaction().commit();
    }
    @Override
    public void removeTicket(Long id, String login) throws NoAccessException {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var tick = new JPAQuery<Ticket>(session)
                .select(ticket)
                .from(ticket)
                .where(ticket.id.eq(id).and(ticket.createdBy.eq(login)))
                .fetchOne();
        if (tick == null) throw new NoAccessException("Нет доступа");

        new JPADeleteClause(session,ticket)
                .where(ticket.id.eq(tick.getId()))
                .execute();
        session.getTransaction().commit();
    }



}

