package com.project.module.feature1.service;

import com.project.module.entities.CrudData;
import com.project.module.repository.CrudDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CrudDataService {
    @Autowired
    private CrudDataRepository crudDataRepository;


    public CrudData create(CrudData crudData) {
        crudData.setCreatedAt(new Date());
        crudData.setUpdatedAt(new Date());
        return crudDataRepository.save(crudData);
    }


    public List<CrudData> getAllData() {
        return crudDataRepository.findAll();
    }


    public CrudData getDataById(Integer id) {
        Optional<CrudData> crudData = crudDataRepository.findById(id);
        if(crudData.isEmpty()){
            return null;
        }
        return crudData.get();
    }


    public CrudData update(Integer id, CrudData crudData) {
        CrudData existingData = getDataById(id);

        existingData.setDescription(crudData.getDescription());
        existingData.setUpdatedAt(new Date());

        return crudDataRepository.save(existingData);
    }


    public void delete(Integer id) {
        CrudData existingData = getDataById(id);
        crudDataRepository.delete(existingData);
    }
}
