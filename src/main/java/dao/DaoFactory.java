package dao;

import Util.DaoType;
import dao.Custom.Impl.PatientDaoImpl;
import service.custom.PatientService;

public class DaoFactory {

    private static DaoFactory instance;
    private DaoFactory(){}
    public static DaoFactory getInstance() {
        return instance==null?instance=new DaoFactory():instance;
    }
    public <T extends SuperDao>T getDaoType(DaoType daoType){
        switch (daoType){
            case PATIENT: return (T) new PatientDaoImpl();
           // case ITEM: return (T) new ItemDaoImpl();
        }
        return null;
    }

}
