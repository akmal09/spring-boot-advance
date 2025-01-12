package com.project.module.feature1.controller;

import com.project.module.entities.CrudData;
import com.project.module.feature1.service.CrudDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/cruddata")
public class CrudController {
    @Autowired
    private CrudDataService crudDataService;

    @GetMapping("/test-api")
    public ResponseEntity<?> testApi(){
        System.out.println("lalala");
        return new ResponseEntity<>("test-connection", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createData(@RequestBody CrudData crudData) {
        CrudData createdData = crudDataService.create(crudData);
        return new ResponseEntity<>(createdData, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllData() {
        List<CrudData> dataList = crudDataService.getAllData();
        return new ResponseEntity<>(dataList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDataById(@PathVariable Integer id) {
        CrudData data = crudDataService.getDataById(id);
        if(Objects.isNull(data)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable Integer id, @RequestBody CrudData crudData) {
        CrudData updatedData = crudDataService.update(id, crudData);
        return new ResponseEntity<>(updatedData, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteData(@PathVariable Integer id) {
        crudDataService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Exception handler for EntityNotFoundException
    @ExceptionHandler(javax.persistence.EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(javax.persistence.EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
