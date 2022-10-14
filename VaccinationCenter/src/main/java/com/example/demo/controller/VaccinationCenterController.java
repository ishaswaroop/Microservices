package com.example.demo.controller;






import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.VaccinationCenter;
import com.example.demo.model.Citizen;
import com.example.demo.model.RequiredResponse;
import com.example.demo.repo.VaccinationCenterRepo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;



@RestController
@RequestMapping("/vaccinationcenter")
public class VaccinationCenterController {

	@Autowired
	private VaccinationCenterRepo repo;
	@Autowired
	private RestTemplate restTemplate;
	
	@PostMapping(path="/add")
	public ResponseEntity<VaccinationCenter> addCenter(@RequestBody VaccinationCenter vaccinationcenter){
		VaccinationCenter vaccinationCenterAdded=repo.save(vaccinationcenter);
		return new ResponseEntity<>(vaccinationCenterAdded,HttpStatus.OK);
	}
	@GetMapping(path="/id/{id}")
	@HystrixCommand(fallbackMethod="HandleCitizenDownTime")
	public ResponseEntity<RequiredResponse>getAllDataBasedonCenterId(@PathVariable Integer id){
		RequiredResponse requiredresponse =new RequiredResponse();
		
		VaccinationCenter center=repo.findById(id).get();
		requiredresponse.setCenter(center);
		
		List<Citizen> listOfCitizens=restTemplate.getForObject("http://CITIZEN-SERVICE/citizen/id/"+id,List.class);
		
		requiredresponse.setCitizens(listOfCitizens);
		return new ResponseEntity<RequiredResponse>(requiredresponse,HttpStatus.OK);
	}
	
   public ResponseEntity<RequiredResponse>HandleCitizenDownTime(@PathVariable Integer id){
	   RequiredResponse requiredresponse =new RequiredResponse();
		
		VaccinationCenter center=repo.findById(id).get();
		requiredresponse.setCenter(center);
		return new ResponseEntity<RequiredResponse>(requiredresponse,HttpStatus.OK);
}
}