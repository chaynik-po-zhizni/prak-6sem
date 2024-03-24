package ru.msu.cmc.prak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;

@SpringBootApplication
public class PrakApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrakApplication.class, args);
	}

}
