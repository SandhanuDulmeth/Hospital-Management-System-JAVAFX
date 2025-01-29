package service;

import Util.ServiceType;
import service.custom.impl.AppointmentServiceImpl;
import service.custom.impl.DoctorSerivceImpl;
import service.custom.impl.PatientServiceImpl;


public class ServiceFactory {
    private static ServiceFactory instance;

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return instance == null ? instance = new ServiceFactory() : instance;
    }

    public <T extends SuperService> T getServiceType(ServiceType type) {
        switch (type) {
            case PATIENT:
                return (T) PatientServiceImpl.getInstance();
            case APPOINTMENT:
                return (T) AppointmentServiceImpl.getInstance();
            case DOCTOR:
                return (T) DoctorSerivceImpl.getInstance();


        }
        return null;

    }
}
