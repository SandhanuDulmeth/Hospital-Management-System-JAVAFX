package dao.custom;


import dao.CrudDao;
import entity.PatientEntity;
import model.Patient;

import java.util.ArrayList;

public interface PatientDao extends CrudDao<PatientEntity,String> {
    ArrayList<PatientEntity>getPatientsID();
}
