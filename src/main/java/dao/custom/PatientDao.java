package dao.custom;


import dao.CrudDao;
import entity.PatientEntity;


import java.util.ArrayList;

public interface PatientDao extends CrudDao<PatientEntity,String> {
    ArrayList<PatientEntity>getPatientsID();
}
