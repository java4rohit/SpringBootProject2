package com.java4rohit.springCsvCrud.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java4rohit.springCsvCrud.Services.ICityServices;
import com.java4rohit.springCsvCrud.model.City;
import com.java4rohit.springCsvCrud.util.WriteCsvToResponse;

@RestController
public class MyCityController {

    @Autowired
     ICityServices cityServices;

    @RequestMapping(value = "/cities", produces = "text/csv")
    public void findCities(HttpServletResponse response) throws IOException {

        List<City> cities = (List<City>)cityServices.findAll();

        WriteCsvToResponse.writeCities(response.getWriter(), cities);
    }

    @RequestMapping(value = "/cities/{cityId}", produces = "text/csv")
    public void findCity(@PathVariable Long cityId, HttpServletResponse response) throws IOException {

        City city = cityServices.findById(cityId);
        WriteCsvToResponse.writeCity(response.getWriter(), city);
    }
}