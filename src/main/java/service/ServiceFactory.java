package service;

import Util.ServiceType;
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

        }
        return null;

    }
}
