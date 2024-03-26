// Question.java
package edu.uga.cs.countryquiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Question {
    private String countryName;
    private String correctContinent;
    private List<String> incorrectContinents;

    public Question(String countryName, String correctContinent, List<String> allContinents) {
        this.countryName = countryName;
        this.correctContinent = correctContinent;
        this.incorrectContinents = generateIncorrectContinents(correctContinent, allContinents);
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCorrectContinent() {
        return correctContinent;
    }

    public List<String> getIncorrectContinents() {
        return incorrectContinents;
    }

    public List<String> getAllOptions() {
        List<String> allOptions = new ArrayList<>();
        allOptions.add(correctContinent);
        allOptions.addAll(incorrectContinents);
        Collections.shuffle(allOptions); // Shuffle options to randomize their order
        return allOptions;
    }

    private List<String> generateIncorrectContinents(String correctContinent, List<String> allContinents) {
        List<String> incorrectContinents = new ArrayList<>(allContinents);
        incorrectContinents.remove(correctContinent); // Remove the correct continent
        Collections.shuffle(incorrectContinents); // Shuffle remaining continents
        return incorrectContinents.subList(0, 2); // Select the first two (random) continents as incorrect
    }
}

