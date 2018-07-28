package br.com.kopzinski.veiculos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarController {
	
	private final CarRepository carRepository;
	
	@Autowired
	public CarController(CarRepository carRepository) {
		this.carRepository = carRepository;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	List<Car> getCarsToSell() {
		
		List<Car> allCars = this.carRepository.findAll();
		
		List<Car> carsToSell = new ArrayList<>();
		for (Car car : allCars) {
			if(car.getQuantity() > 0) {
				carsToSell.add(car);
			}
		}
		
		return carsToSell;
	}

}
