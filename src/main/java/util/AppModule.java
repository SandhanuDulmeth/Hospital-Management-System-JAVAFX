package util;

import com.google.inject.AbstractModule;
import dao.custom.PatientDao;
import dao.custom.impl.PatientDaoImpl;
import service.custom.PatientService;
import service.custom.impl.PatientServiceImpl;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
       bind(PatientService.class).to(PatientServiceImpl.class);
       //bind(PatientDao.class).to(PatientDaoImpl.class);
    }
}
