package service;

import util.ServiceType;
import service.custom.impl.*;


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
            case BILLING:
                return (T) BillingServiceImpl.getInstance();
            case RESOURCE:
                return (T) ResourceServiceImpl.getInstance();
            case LOGIN:
                return (T) LoginServiceImpl.getInstance();
            case REGISTER:
                return (T) RegisterSerivceImpl.getInstance();
       }
        return null;

    }
}
