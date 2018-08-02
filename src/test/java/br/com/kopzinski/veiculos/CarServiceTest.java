package br.com.kopzinski.veiculos;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CarServiceTest {

	//METHOD to test: filterAvailableCars
	//this test will run faster and cheaper, so we can explore more scenarios
	//So, what the possible scenarios???
	//(we need to test bad scenarios, its really important, keep it in mind)

	@Test
	public void returnsJustOneAvailableCar() {
		CarService carService = new CarService();
		
		List<Car> sourceCars = new ArrayList<>();
		Car car1 = new Car();
		car1.setQuantity(0);
		sourceCars.add(car1);
		
		Car car2 = new Car();
		car2.setQuantity(1);
		sourceCars.add(car2);
		
		List<Car> availableCars = carService.filterAvailableCars(sourceCars);
		
		assertThat(availableCars.size()).isEqualTo(1);
	}
	
	@Test
	public void returnsJustZeroAvailableCarsIfAllHasQtyZero() {
		CarService carService = new CarService();
		
		List<Car> sourceCars = new ArrayList<>();
		Car car1 = new Car();
		car1.setQuantity(0);
		sourceCars.add(car1);
		
		Car car2 = new Car();
		car2.setQuantity(0);
		sourceCars.add(car2);
		
		List<Car> availableCars = carService.filterAvailableCars(sourceCars);
		
		assertThat(availableCars.size()).isEqualTo(0);
	}
	
	@Test
	public void returnsJustZeroAvailableCarsWithNoCarWasPassed() {
		CarService carService = new CarService();
		
		List<Car> sourceCars = new ArrayList<>();
		List<Car> availableCars = carService.filterAvailableCars(sourceCars);
		
		assertThat(availableCars.size()).isEqualTo(0);
	}
	
	
	
}
