package com.dataCreater.dataCreater;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class PromptLibrary {
        // public static final Map<String, String> PlacePrompts = Map.of(
        // // "historical_facts", "Just Want '5' points. Tell me about the historical
        // // significance of ",
        // "historical_facts", "Just Want '5' points. Tell me about the historical
        // significance of {0}",

        // "attractions", "Just Want '5' points. What are the main attractions in {0}?
        // ",
        // "famous_places",
        // "Just Want '5' points. Which famous places should I not miss in {0}?",
        // "unique_information", "Just Want '5' points. Share some unique information
        // about {0}.");
        public static final Map<String, String> PlacePrompts = Map.of(
                        // Historical Facts
                        "historical_facts", "Can you provide 5 key historical facts about the significance of {0}?",

                        // Attractions
                        "attractions",
                        "What are the top 5 must-see attractions in {0}? Please provide details about each.",

                        // Famous Places
                        "famous_places",
                        "Which famous landmarks or places should I visit in {0}? Please highlight the key locations.And just return the locations between 5-10kms",

                        // Unique Information
                        "unique_information",
                        "Can you share 5 unique and interesting facts about {0}? Provide specific details that set it apart.");

        public static final Map<String, String> PlaceComparisonPrompts = Map.of(
                        "historical_facts", "Provide 5 key historical facts about {0}.",
                        "attractions", "List 5 main attractions in {0}.",
                        "famous_places", "What are 5 famous places in {0}?",
                        "unique_information", "Share 5 unique facts about {0}.");

        public static final Map<String, String> funfactsPrompt = Map.of(
                        "historical_facts", "Share historical facts about {0}.",
                        "trivia", "What are some fun and interesting trivia facts about {0}?",
                        "fun_facts", "Provide some unique and quirky facts about {0} that people may not know.");

}
