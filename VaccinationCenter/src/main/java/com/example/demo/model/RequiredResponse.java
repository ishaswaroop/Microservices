package com.example.demo.model;

import java.util.List;

import com.example.demo.entity.VaccinationCenter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequiredResponse {

	private VaccinationCenter center;
	private List<Citizen>citizens;
}
