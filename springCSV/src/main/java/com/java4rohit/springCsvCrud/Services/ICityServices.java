package com.java4rohit.springCsvCrud.Services;

import java.util.List;

import com.java4rohit.springCsvCrud.model.City;

public interface ICityServices {
	

    List<City> findAll();
    City findById(Long id);

}
