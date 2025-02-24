package service.custom.impl;

import dao.DaoFactory;
import dao.custom.ResourceDao;
import entity.ResourceEntity;
import org.modelmapper.ModelMapper;

import model.Resource;
import service.custom.ResourceService;
import util.DaoType;


import java.sql.SQLException;
import java.util.ArrayList;

public class ResourceServiceImpl implements ResourceService {
    public static ResourceServiceImpl insance;

    ResourceDao resourceDao = DaoFactory.getInstance().getDaoType(DaoType.RESOURCE);

    private ResourceServiceImpl() {
    }

    public static ResourceServiceImpl getInstance() {
        return insance == null ? insance = new ResourceServiceImpl() : insance;

    }

    @Override
    public boolean addResource(Resource resource) throws SQLException {

        return resourceDao.save(new ModelMapper().map(resource, ResourceEntity.class));

    }

    @Override
    public boolean deleteResource(Integer id) throws SQLException {

        return resourceDao.delete(String.valueOf(id));


    }

    @Override
    public ArrayList<Resource> getAll() {
        ArrayList<Resource> resourceArrayList = new ArrayList<>();
        resourceDao.gettAll().forEach(resourceEntity -> resourceArrayList.add(new ModelMapper().map(resourceEntity, Resource.class)));
        return resourceArrayList;
    }

    @Override
    public boolean UpdateResource(Resource resource) {

        return resourceDao.update(new ModelMapper().map(resource, ResourceEntity.class));

    }

    @Override
    public Resource searchResource(Integer id) {

        return new ModelMapper().map(resourceDao.search(String.valueOf(id)), Resource.class);
    }

    @Override
    public Integer getNextId() {
        return resourceDao.getNextId();


    }
}
