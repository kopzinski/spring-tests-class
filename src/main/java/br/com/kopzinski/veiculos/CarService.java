package br.com.kopzinski.veiculos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CarService {
	
	public List<Car> filterAvailableCars(List<Car> allCars) {
		List<Car> carsToSell = new ArrayList<>();
		for (Car car : allCars) {
			if(car.getQuantity() > 0) {
				carsToSell.add(car);
			}
		}
		return carsToSell;
	}
}
