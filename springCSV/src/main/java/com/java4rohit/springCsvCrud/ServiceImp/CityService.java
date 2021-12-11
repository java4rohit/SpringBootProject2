package com.java4rohit.springCsvCrud.ServiceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.java4rohit.springCsvCrud.Services.ICityServices;
import com.java4rohit.springCsvCrud.model.City;
import com.java4rohit.springCsvCrud.repositary.CityRepository;

public class CityService implements ICityServices {

	  @Autowired
	    private CityRepository repository;

	    @Override
	    public List<City> findAll() {

	        return  (List<City>) repository.findAll();
	    }

	    @Override
	    public City findById(Long id) {

	        return repository.findById(id).orElse(new City());
	    }
	}