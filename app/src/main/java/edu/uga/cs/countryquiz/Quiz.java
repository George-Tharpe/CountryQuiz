// Quiz.java
package edu.uga.cs.countryquiz;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Quiz {

    private List<Question> questions;
    private List<Country> countryList;
    private Date quizDate;
    private int currentScore;
    private int questionsAnsweredSoFar;
    private List<String> allContinents;
    private Set<String> usedCountries;
    public List<String> incorrectContinents;

    public Quiz(CountryData countryData) {
        this.questions = new ArrayList<>();
        this.quizDate = new Date();
        this.currentScore = 0;
        this.questionsAnsweredSoFar = 0;
        this.countryList = countryData.retrieveAllCountries();
        this.allContinents = this.getAllContinents();
        this.usedCountries = new HashSet<>();
        this.incorrectContinents = new ArrayList<>();
        generateQuestions();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Date getQuizDate() {
        return quizDate;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getQuestionsAnsweredSoFar() {
        return questionsAnsweredSoFar;
    }

    public void updateScore(boolean answerCorrectly) {
        if (answerCorrectly) {
            currentScore += 1; // Each question is worth 1 point for correct answers
        }
        questionsAnsweredSoFar++;
    }

    public List<String> getAllContinents() {
        Set<String> continentSet = new HashSet<>();
        for (Country country : countryList) {
            continentSet.add(country.getContinent());
        }
        return new ArrayList<>(continentSet);
    }

    public void generateQuestions() {
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            // Choose a random country from the country list
            if (countryList.size() > 0) {
                int randomIndex = random.nextInt(countryList.size());
                Country randomCountry = countryList.get(randomIndex);

                // Check if the country has been used in a previous question
                while (usedCountries.contains(randomCountry.getName())) {
                    randomIndex = random.nextInt(countryList.size());
                    randomCountry = countryList.get(randomIndex);
                }

                // Add the country to the used countries set
                usedCountries.add(randomCountry.getName());

                // Create a question and add it to the list
                Question question = new Question(randomCountry.getName(), randomCountry.getContinent(), allContinents);
                questions.add(question);
            }
        }
    }
}
