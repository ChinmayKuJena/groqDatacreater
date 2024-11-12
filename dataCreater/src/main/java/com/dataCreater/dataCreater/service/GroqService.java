package com.dataCreater.dataCreater.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.dataCreater.dataCreater.PromptLibrary;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GroqService {

    @Value("${groq.apiKey}")
    private String API_KEY;

    @Value("${groq.apiUrl}")
    private String API_URL;

    @Autowired
    private PromptLibrary promptLibrary;

    // Method to send a question to the Groq API
    public String askGroq(String question, String place) {
        // Updated input data with assistant role description
        String inputData = "{"
                + "\"model\": \"llama3-8b-8192\","
                + "\"messages\": [{"
                + "\"role\": \"system\","
                + "\"content\": \"You are a helpful assistant with expertise in geographical and place-based information, similar to Google Maps.\""
                + "}, {"
                + "\"role\": \"user\","
                + "\"content\": \"" + question + "\""
                + "}]"
                + "}";

        // Initialize RestTemplate to send the request
        RestTemplate restTemplate = new RestTemplate();

        // Set up the headers, including the API key for authorization
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY); // API key for authorization
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HTTP request entity with input data and headers
        HttpEntity<String> entity = new HttpEntity<>(inputData, headers);

        // Send the POST request to the Groq API
        ResponseEntity<String> response = restTemplate.exchange(
                API_URL, HttpMethod.POST, entity, String.class);

        // Extract the assistant's response from the API response body
        String responseBody = response.getBody();
        String assistantResponse = extractAssistantResponse(responseBody, place);

        return assistantResponse;
    }

    private String extractAssistantResponse(String responseBody, String place) {
        // HashMap to store the extracted values
        HashMap<String, String> extractedData = new HashMap<>();
        HashMap<String, String> content = new HashMap<>();

        try {
            // Parse the response JSON using Jackson's ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);

            // Extract the assistant's message content from "choices"
            JsonNode choicesNode = rootNode.path("choices");
            String assistantContent = "No valid choices in response.";
            if (choicesNode.isArray() && choicesNode.size() > 0) {
                JsonNode firstChoice = choicesNode.get(0);
                JsonNode messageNode = firstChoice.path("message");
                assistantContent = messageNode.path("content").asText();
            }
            content.put("responseBody", assistantContent);
            content.put("placeName", place);
            extractedData.put("placeName", place);
            CsvService.saveMainContentToCSV(content);
            // Store assistant content in the HashMap
            // extractedData.put("assistant_content", assistantContent);

            // Extract the usage information and store it in the HashMap
            JsonNode usageNode = rootNode.path("usage");
            if (usageNode.isObject()) {
                Iterator<Map.Entry<String, JsonNode>> fields = usageNode.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> field = fields.next();
                    extractedData.put(field.getKey(), field.getValue().asText());
                }
            }

            // Save extracted data to CSV
            CsvService.saveToCSV(extractedData);

            // Return the assistant content
            return assistantContent;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing response from API.";
        }
    }
    // private String extractAssistantResponse(String responseBody) {
    // // Assuming the response format is as follows:
    // // {"choices": [{"message": {"role": "assistant", "content": "response
    // text"}}],
    // // "usage": {...}}

    // try {
    // // Parse the response JSON using Jackson's ObjectMapper
    // ObjectMapper objectMapper = new ObjectMapper();
    // JsonNode rootNode = objectMapper.readTree(responseBody);

    // // Extract the assistant's message content from "choices"
    // JsonNode choicesNode = rootNode.path("choices");
    // String assistantContent = "No valid choices in response.";
    // if (choicesNode.isArray() && choicesNode.size() > 0) {
    // JsonNode firstChoice = choicesNode.get(0);
    // JsonNode messageNode = firstChoice.path("message");
    // assistantContent = messageNode.path("content").asText();
    // }

    // // Extract the usage information and print each field
    // JsonNode usageNode = rootNode.path("usage");
    // if (usageNode.isObject()) {
    // // System.out.println("Usage Information:");

    // // Iterate through all fields in the "usage" node and print them
    // Iterator<Map.Entry<String, JsonNode>> fields = usageNode.fields();
    // while (fields.hasNext()) {
    // Map.Entry<String, JsonNode> field = fields.next();
    // // System.out.println(field.getKey() + ": " + field.getValue().asText());
    // }
    // }

    // // You can also return the assistant's content
    // return assistantContent;
    // } catch (Exception e) {
    // e.printStackTrace();
    // return "Error parsing response from Groq API.";
    // }
    // }

    // Method to ask a question using a prompt type and a place
    public String askQuestion(String promptType, String place) {

        String prompt = promptLibrary.PlacePrompts.getOrDefault(promptType, "Default prompt not found.");
        String formattedPrompt = prompt.replace("{0}", place); // Replace {0} with the place name
        // Get the correct prompt from the library and format it with the place
        // String prompt = promptLibrary.PlacePrompts.getOrDefault(promptType, "Default
        // prompt not found.");

        // String formattedPrompt = prompt + place;
        // System.out.println(formattedPrompt);
        // Send the formatted prompt to the Groq API
        return askGroq(formattedPrompt, place);
    }

    // TODO for 2 place names
    // Method to ask comparison type questions using a place
    public String askComparisonQuestion(String promptType, String place) {
        // Get the comparison prompt from the library and format it with the place
        String prompt = promptLibrary.PlaceComparisonPrompts.getOrDefault(promptType,
                "Default comparison prompt not found.");
        String formattedPrompt = prompt + place;

        // Send the formatted comparison prompt to the Groq API
        return askGroq(formattedPrompt, place);
    }

    // Method to ask fun facts type questions using a place
    public String askFunFactsQuestion(String promptType, String place) {
        // Get the fun facts prompt from the library and format it with the place
        String prompt = promptLibrary.funfactsPrompt.getOrDefault(promptType, "Default fun facts prompt not found.");
        String formattedPrompt = prompt.replace("{0}", place); // Replace {0} with the place name
        // System.out.println(formattedPrompt);
        // Send the formatted fun facts prompt to the Groq API
        return askGroq(formattedPrompt, place);
    }

    // Method to check if a place is a known geographic location
    public String checkIfPlaceIsGeographic(String place) {
        String prompt = String.format("Is '%s' a known geographic location? Respond with 'yes' or 'no' only.", place);

        // Send the prompt to Groq API
        return askGroq(prompt, place);
    }

    // Method to get coordinates of a place
    public String getCoordinatesOfPlace(String place) {
        String prompt = String.format("What are the geographic coordinates (latitude and longitude) of '%s'?", place);

        // Send the prompt to Groq API
        return askGroq(prompt, place);
    }
}
