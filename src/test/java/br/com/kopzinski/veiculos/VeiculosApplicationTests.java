package br.com.kopzinski.veiculos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class VeiculosApplicationTests {

	@MockBean
	private CarRepository carRepository;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void canListAvailableCarsFromStore() {
		//given - findAll will return 2 cars and one of them has quantity 0
		List<Car> allCars = new ArrayList<>();
		Car car1 = new Car();
		car1.setQuantity(0);
		car1.setId(1);
		allCars.add(car1);
		
		Car car2 = new Car();
		car2.setQuantity(1);
		car2.setId(2);
		allCars.add(car2);
		
		given(carRepository.findAll()).willReturn(allCars );
		
		//when - the [GET] "/car" is called
		ResponseEntity<Car[]> responseEntity = restTemplate.getForEntity("/cars", Car[].class);
		Car[] carsReturned = responseEntity.getBody();
		
		//then - just the another car (quantity:1) will be returned and httpStatus is OK
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(carsReturned.length).isEqualTo(1);
	}
	
	@Test
	public void canBuyCarToStoreWithPerfectAttr() {
		//given - a car with the essential fields filled correctly
		Car car = new Car();
		car.setId(1);
		car.setBrand("GM");
		car.setModel("Celta");
		car.setYear(2014);
		car.setMarketPrice(20000.0);
		car.setPayPrice(16000.0);
		car.setQuantity(1);
		
		//when - try to add a car
		ResponseEntity<Car> responseEntity = restTemplate.postForEntity("/cars/buy", car, Car.class);
		
		//then - the response will be OK
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		
		//when we need more info about the response, we can get with this
//		MediaType contentType = responseEntity.getHeaders().getContentType();
	}
	
	@Test
	public void cannotBuyCarToStoreWithTooHigherPayPrice() {
		//given - a car with big PayPrice
		Car car = new Car();
		car.setId(1);
		car.setBrand("GM");
		car.setModel("Celta");
		car.setYear(2014);
		car.setMarketPrice(20000.0);
		car.setPayPrice(19000.0);
		car.setQuantity(1);
		
		//when - try to add a car
		ResponseEntity<Car> responseEntity = restTemplate.postForEntity("/cars/buy", car, Car.class);
		
		//then - the response will be NOT_FOUND? (really...?)
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	
	public void canChangeDisccountSellCarFromStore() {
		//TODO with students
	}
	
	public void canSellCarFromStore() {
		//TODO with students
	}
	
	//TODO We need to write tests to all ERRORS scenarios?

}
