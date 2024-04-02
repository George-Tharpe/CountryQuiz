package edu.uga.cs.countryquiz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SavedQuizData {
    private long quizId;
    private int score;
    private long dateMillis;

    public SavedQuizData(long quizId, int score, long dateMillis) {
        this.quizId = quizId;
        this.score = score;
        this.dateMillis = dateMillis;
    }

    public long getQuizId() {
        return quizId;
    }

    public int getScore() {
        return score;
    }

    public String getDate() {
        if (dateMillis == 0) {
            return "Unknown Date";
        }

        // Convert milliseconds to Date
        Date date = new Date(dateMillis);

        // Format the Date object to a more readable format
        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy - hh:mm a", Locale.getDefault());
        return dateFormat.format(date);
    }
}