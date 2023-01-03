package com.kharim.inventoryservice;

import com.kharim.inventoryservice.models.Inventory;
import com.kharim.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
//
//		return args -> {
//			Inventory inventory = new Inventory();
//			inventory.setSkuCode("Sneakers");
//			inventory.setQuantity(2);
//
//			Inventory inventory1 = new Inventory();
//			inventory1.setSkuCode("Fuse");
//			inventory1.setQuantity(7);
//
//			inventoryRepository.save(inventory);
//			inventoryRepository.save(inventory1);
//		};

//	}


}
