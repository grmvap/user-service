package com.example.dao.impl;


import com.example.config.HibernateUtil;
import com.example.dao.UserDao;
import com.example.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public User create(User user) throws Exception {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
            return user;
        } catch (Exception e) {
            if (tx != null && tx.getStatus().canRollback()) tx.rollback();
            log.error("Error creating user", e);
            throw e;
        }
    }

    @Override
    public Optional<User> findById(Long id) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            log.error("Error finding user by id", e);
            throw e;
        }
    }

    @Override
    public List<User> findAll() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> q = session.createQuery("from model.User", User.class);
            return q.getResultList();
        } catch (Exception e) {
            log.error("Error finding all users", e);
            throw e;
        }
    }

    @Override
    public User update(User user) throws Exception {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(user);
            tx.commit();
            return user;
        } catch (Exception e) {
            if (tx != null && tx.getStatus().canRollback()) tx.rollback();
            log.error("Error updating user", e);
            throw e;
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user == null) {
                if (tx != null && tx.getStatus().canRollback()) tx.rollback();
                return false;
            }
            session.remove(user);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.getStatus().canRollback()) tx.rollback();
            log.error("Error deleting user", e);
            throw e;
        }
    }
}
