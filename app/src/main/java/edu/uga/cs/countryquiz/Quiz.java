// Quiz.java
package edu.uga.cs.countryquiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Quiz {
    private List<Question> questions;
    private Date quizDate;
    private int currentScore;
    private int questionsAnsweredSoFar;

    public Quiz(List<Question> questions, Date quizDate) {
        this.questions = questions;
        this.quizDate = quizDate;
        this.currentScore = 0;
        this.questionsAnsweredSoFar = 0;
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
            currentScore += 1; // Each question is worth 1 points for correct answers
        }
        questionsAnsweredSoFar++;
    }
}
