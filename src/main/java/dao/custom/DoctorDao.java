package dao.custom;

import dao.CrudDao;
import entity.DoctorEntity;

import java.util.ArrayList;

public interface DoctorDao extends CrudDao<DoctorEntity,String> {
    ArrayList<DoctorEntity> getDocID();
}
