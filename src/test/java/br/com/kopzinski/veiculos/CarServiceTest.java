package br.com.kopzinski.veiculos;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CarServiceTest {

	//METHOD to test: filterAvailableCars
	//this test will run faster and cheaper, so we can explore more scenarios
	//So, what the possible scenarios???
	//(we need to test bad scenarios, its really important, keep it in mind)

	CarService carService;
	Car unavailableCar;
	Car availableCar;
	List<Car> sourceCars;
	
	@Before
	public void setup() {
		carService = new CarService();
		availableCar = new Car();
		availableCar.setQuantity(1);
		
		unavailableCar =  new Car();
		unavailableCar.setQuantity(0);
		
		sourceCars = new ArrayList<>();
	}
	
	@Test
	public void returnsJustOneAvailableCar() {
		//given
		sourceCars.add(availableCar);
		sourceCars.add(unavailableCar);
		
		//when
		List<Car> availableCars = carService.filterAvailableCars(sourceCars);
		
		//then
		assertThat(availableCars.size()).isEqualTo(1);
	}
	
	@Test
	public void returnsJustZeroAvailableCarsIfAllHasQtyZero() {
		
		sourceCars.add(unavailableCar);
		sourceCars.add(unavailableCar);
		
		List<Car> availableCars = carService.filterAvailableCars(sourceCars);
		
		assertThat(availableCars.size()).isEqualTo(0);
	}
	
	@Test
	public void returnsJustZeroAvailableCarsWithNoCarWasPassed() {
		
		List<Car> availableCars = carService.filterAvailableCars(sourceCars);
		
		assertThat(availableCars.size()).isEqualTo(0);
	}
	
	
}
