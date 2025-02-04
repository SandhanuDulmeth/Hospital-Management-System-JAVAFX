package dao;

import dao.custom.impl.*;
import util.DaoType;


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
            case RESOURCE: return (T) new ResourceDaoImpl();
            case BILLING:return (T) new BillingDaoImpl();
        }
        return null;
    }

}
