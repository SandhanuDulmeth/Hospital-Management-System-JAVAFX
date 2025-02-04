package dao;

import dao.custom.impl.AppointmentDaoImpl;
import dao.custom.impl.DoctorDaoImpl;
import dao.custom.impl.PrescriptionDaoImpl;
import util.DaoType;
import dao.custom.impl.PatientDaoImpl;


public class DaoFactory {

    private static DaoFactory instance;

    private DaoFactory(){}

    public static DaoFactory getInstance() {
        return instance==null?instance=new DaoFactory():instance;
    }

    public <T extends SuperDao>T getDaoType(DaoType daoType){
        switch (daoType){
            case PATIENT: return (T) new PatientDaoImpl();
            case DOCTOR: return (T) new DoctorDaoImpl();
            case PRESCRIPTION: return (T) new PrescriptionDaoImpl();
            case APPOINTMENT: return (T) new AppointmentDaoImpl();
        }
        return null;
    }

}
