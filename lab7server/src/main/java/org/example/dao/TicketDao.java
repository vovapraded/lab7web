package org.example.dao;

import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.Cleanup;
import org.example.commands.authorization.NoAccessException;

import org.example.entity.Ticket;
import org.example.managers.HibernateManager;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.HashMap;
import java.util.stream.Collectors;

import static org.example.entity.QTicket.ticket;
@Repository
public class TicketDao {
    private final SessionFactory sessionFactory;

    public TicketDao(HibernateManager hibernateManager) {

        sessionFactory = hibernateManager.getConfiguration().buildSessionFactory();
    }

    public HashMap<Long, Ticket> loadCollection() throws FailedTransactionException{
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        try {
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
        catch (Exception e){
            session.getTransaction().rollback();
            throw new FailedTransactionException("Транзакция загрузки не удалась",e);
        }

    }
    public List<Ticket> findAll() throws FailedTransactionException{
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            var tickets = new JPAQuery<Ticket>(session)
                    .select(ticket)
                    .from(ticket)
                    .fetch().stream().toList();
//            session.getTransaction().commit();
            return  tickets;
        }
        catch (Exception e){
            session.getTransaction().rollback();
            throw new FailedTransactionException("Транзакция поиска не удалась",e);
        }

    }

    public void clear(String login) throws FailedTransactionException {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            new JPADeleteClause(session,ticket)
                    .where(ticket.createdBy.eq(login))
                    .execute();
            session.getTransaction().commit();
        } catch (Exception e){
            session.getTransaction().rollback();
            throw new FailedTransactionException("Транзакция не удалась",e);
        }

    }

    public void insert(Ticket ticket) throws FailedTransactionException {
        @Cleanup var session = sessionFactory.openSession();
            session.beginTransaction();
        try {
            session.save(ticket);
            session.getTransaction().commit();
        } catch (Exception e){
            session.getTransaction().rollback();
            throw new FailedTransactionException("Транзакция не удалась",e);
        }

    }
    public void update(Ticket newTicket, String login) throws FailedTransactionException,NoAccessException {
        @Cleanup var session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Ticket oldTicket = session.get(Ticket.class, newTicket.getId());
            if (oldTicket.getCreatedBy().equals(login)) {
                session.merge(newTicket); // Заменяем существующий билет новым
                session.getTransaction().commit();
            } else {
                session.getTransaction().rollback();
                throw new NoAccessException("Нет доступа");
            }
        }catch (Exception e){
            if (e instanceof NoAccessException) throw e;
            session.getTransaction().rollback();
            throw new FailedTransactionException("Транзакция не удалась",e);
        }


    }
    public void removeTicket(Ticket tick) throws FailedTransactionException{
        @Cleanup var session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            new JPADeleteClause(session,ticket)
                    .where(ticket.id.eq(tick.getId()))
                    .execute();
            session.getTransaction().commit();
        } catch (Exception e){
            session.getTransaction().rollback();
            throw new FailedTransactionException("Транзакция не удалась",e);
        }

    }
    public void removeTicket(Long id, String login) throws FailedTransactionException, NoAccessException {
        @Cleanup var session = sessionFactory.openSession();
        try {
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
        } catch (Exception e){
            if (e instanceof NoAccessException) throw e;
            session.getTransaction().rollback();
            throw new FailedTransactionException("Транзакция не удалась",e);
        }

    }



}

