package service.custom.impl;

import dao.DaoFactory;
import dao.custom.BillingDao;
import entity.BillingEntity;
import org.modelmapper.ModelMapper;
import util.CrudUtil;
import model.Billing;
import model.Patient;
import service.custom.BillingService;
import util.DaoType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BillingServiceImpl implements BillingService {
    private static BillingServiceImpl instance;

    BillingDao billingDao=DaoFactory.getInstance().getDaoType(DaoType.BILLING);

    private BillingServiceImpl() {
    }

    public static BillingServiceImpl getInstance() {
        return (instance == null) ? (instance = new BillingServiceImpl()) : instance;
    }

    @Override
    public boolean addBilling(Billing billing) throws SQLException {

        return billingDao.save(new ModelMapper().map(billing, BillingEntity.class));


    }

    @Override
    public boolean deleteBilling(Integer id) throws SQLException {


        return billingDao.delete(String.valueOf(id));

    }

    @Override
    public ArrayList<Billing> getAll() {

        ArrayList<Billing> billingList =new ArrayList<>();
        billingDao.gettAll().forEach(billingEntity -> billingList.add(new ModelMapper().map(billingEntity,Billing.class)));
   return billingList;
    }

    @Override
    public boolean updateBilling(Billing billing) {

        return billingDao.update(new ModelMapper().map(billing, BillingEntity.class));

    }

    @Override
    public Billing searchBilling(Integer id) {

      return new ModelMapper().map(billingDao.search(String.valueOf(id)),Billing.class);
    }


    @Override
    public ArrayList<Patient> getPatientsID() {
      return PatientServiceImpl.getInstance().getPatientsID();
    }



    @Override
    public Integer getNextId() {

        return billingDao.getNextId();

    }
}
