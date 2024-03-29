// Question.java
package edu.uga.cs.countryquiz;

import android.util.Log;

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
    public List<String> incorrectContinents;

    public Question(String countryName, String correctContinent, List<String> allContinents) {
        this.countryName = countryName;
        this.correctContinent = correctContinent;
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




}

