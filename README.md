# GroqService - Spring Boot Service for Asking Questions to Groq API

This service allows you to interact with the Groq API to ask geographic and place-related questions, such as getting coordinates of a place, checking if a place is a known geographic location, and retrieving fun facts.

## Features
- **askGroq**: Sends questions to the Groq API and returns the assistant's response.
- **askQuestion**: Sends a formatted prompt from the prompt library with a place to the Groq API.
- **askComparisonQuestion**: Sends comparison-related questions using a place.
- **askFunFactsQuestion**: Asks for fun facts about a place using prompts from the prompt library.
- **checkIfPlaceIsGeographic**: Checks if a place is a known geographic location.
- **getCoordinatesOfPlace**: Retrieves the geographic coordinates (latitude and longitude) of a place.

## Configuration
The service requires the following configuration in the pplication.properties file:
- **groq.apiKey**: Your Groq API key.
- **groq.apiUrl**: The Groq API URL.

## Usage
To use this service, simply inject the GroqService into your Spring Boot application and call the respective methods, passing in a place or question as a parameter. The service will send the request to the Groq API and return the response.

Example:

```java

@Autowired
private GroqService groqService;

public void exampleUsage() {
    String response = groqService.askQuestion(
locationInfo, New
York);
    System.out.println(response);
}
```

## Dependencies
- Spring Boot
- RestTemplate
- Jackson (for parsing JSON)

## License
This project is licensed under the MIT License.


