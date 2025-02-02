package dao.custom.impl;

import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.custom.PatientDao;
import entity.PatientEntity;
import util.HibernateUtil;


import java.util.logging.Logger;

public class PatientDaoImpl implements PatientDao {

    private static final Logger LOGGER = Logger.getLogger(PatientDaoImpl.class.getName());

    @Override
    public boolean save(PatientEntity patientEntity) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.persist(patientEntity);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            LOGGER.severe("Error saving PatientEntity: " + e.getMessage());
            return false;
        }

    }


    @Override
    public boolean delete(String id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            PatientEntity patientEntity = session.get(PatientEntity.class, id);
            if (patientEntity != null) {
                session.remove(patientEntity);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            LOGGER.severe("Error saving PatientEntity: " + e.getMessage());
            return false;
        }


    }

    @Override
    public ObservableList<PatientEntity> gettAll() {
        return null;
    }

    @Override
    public boolean update(PatientEntity entity) {
        return false;
    }

    @Override
    public PatientEntity search(String s) {
        return null;
    }

    @Override
    public Integer getNextId() {
        return 0;
    }

}