package br.com.kopzinski.veiculos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarController {
	
	private final CarRepository carRepository;
	private final CarService carService;
	
	@Autowired
	public CarController(CarRepository carRepository, CarService carService) {
		this.carRepository = carRepository;
		this.carService = carService;
	}
	
	@GetMapping
	public @ResponseBody List<Car> getCarsToSell() {
		List<Car> allCars = this.carRepository.findAll();
		List<Car> availableCars = this.carService.filterAvailableCars(allCars);
		return availableCars;
	}
	
	
	@PostMapping("/buy")
	@ResponseStatus(HttpStatus.CREATED)
	public void addPurchasedCar(@RequestBody Car purchasedCar) {
		if(purchasedCar.getBrand().isEmpty()) {
			throw new CarException("Brand is required.");
		}
		if(purchasedCar.getModel().isEmpty()) {
			throw new CarException("Model is required.");
		}
		if(purchasedCar.getYear() == null || purchasedCar.getYear() <= 0) {
			throw new CarException("Year is required.");
		}
		if(purchasedCar.getPayPrice() == null || purchasedCar.getPayPrice() <= 0) {
			throw new CarException("Pay price is required.");
		}
		if(purchasedCar.getMarketPrice() == null || purchasedCar.getMarketPrice() <= 0) {
			throw new CarException("Market price is required.");
		}
		if(purchasedCar.getPayPrice() > (purchasedCar.getMarketPrice() * 0.8)) {
			throw new CarException("Pay price must be less than 80% of Market price.");
		}
		
		Optional<Car> found = this.carRepository.findById(purchasedCar.getId());
		if(found.isPresent()) {
			Car carFound = found.get();
			carFound.setQuantity(carFound.getQuantity() + 1);
			carFound.setPayPrice(purchasedCar.getPayPrice());
			carFound.setMarketPrice(purchasedCar.getMarketPrice());
			this.carRepository.save(carFound);
		} else {
			purchasedCar.setQuantity(1);
			this.carRepository.save(purchasedCar);
		}
		
	}
	
	@PostMapping("/sell")
	@ResponseStatus(HttpStatus.CREATED)
	public void removeSelledCar(@RequestBody Car selledCar) {
		Car selledCarFound = this.carRepository.findById(selledCar.getId()).get();
		
		if(selledCar.getQuantity() <= 0) {
			throw new CarException("This model is not available to sell.");
		}
		
		if(selledCar.getSellPrice() > ( selledCarFound.getMarketPrice() * ( 1 - selledCarFound.getDisccountPercentage()))) {
			throw new CarException("The SellPrice needs to be greater than that.");
		}
		
		selledCarFound.setQuantity(selledCarFound.getQuantity() - 1);
		selledCarFound.setSellPrice(selledCar.getSellPrice());
		this.carRepository.save(selledCarFound);
		
	}
	
	@PostMapping("/disccount")
	@ResponseStatus(HttpStatus.CREATED)
	public void changeDisccount(@RequestBody Car carToChangeDisccount) {
		Car carFound = this.carRepository.findById(carToChangeDisccount.getId()).get();
		carFound.setDisccountPercentage(carToChangeDisccount.getDisccountPercentage());
		this.carRepository.save(carFound);
	}

}
