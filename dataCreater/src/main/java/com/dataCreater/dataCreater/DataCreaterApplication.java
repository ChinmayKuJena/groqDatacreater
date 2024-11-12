package com.dataCreater.dataCreater;

import com.dataCreater.dataCreater.service.GroqService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class DataCreaterApplication implements CommandLineRunner {

	@Autowired
	private GroqService groqService;

	public static void main(String[] args) {
		SpringApplication.run(DataCreaterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Example places to query
		String place = "Paris";

		// Example of asking general questions
		String historicalFacts = groqService.askQuestion("historical_facts", place);
		// System.out.println("Historical Facts about " + place + ": " +
		// historicalFacts);

		// Example of asking comparison-related questions
		// String attractions = groqService.askComparisonQuestion("attractions", place);
		// System.out.println("Attractions in " + place + ": " + attractions);
		System.out.println("-----------------------------------------------");

		// Example of asking fun facts
		String funFacts = groqService.askFunFactsQuestion("fun_facts", place);
		// System.out.println("Fun Facts about " + place + ": " + funFacts);
		System.out.println(groqService.checkIfPlaceIsGeographic("Rairangpur"));
	}
}
