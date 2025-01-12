package com.project.module.repository;

import com.project.module.entities.CrudData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CrudDataRepository extends CrudRepository<CrudData, Integer> {
    Optional<CrudData> findById(Integer integer);
    List<CrudData> findAll();
}
