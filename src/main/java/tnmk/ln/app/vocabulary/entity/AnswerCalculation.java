package tnmk.ln.app.vocabulary.entity;

import org.springframework.data.mongodb.core.index.Indexed;

/**
 * @author khoi.tran on 1/31/17.
 */
public class AnswerCalculation {
    private int totalCount = 0;
    private int correctCount = 0;
    private int correctPoint = 0;
    @Indexed
    private double correctPercentage = 0;

    public AnswerCalculation() {
    }

    public AnswerCalculation(int totalCount, int correctCount, int correctPoint, double correctPercentage) {
        this.totalCount = totalCount;
        this.correctCount = correctCount;
        this.correctPoint = correctPoint;
        this.correctPercentage = correctPercentage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public int getCorrectPoint() {
        return correctPoint;
    }

    public void setCorrectPoint(int correctPoint) {
        this.correctPoint = correctPoint;
    }

    public double getCorrectPercentage() {
        return correctPercentage;
    }

    public void setCorrectPercentage(double correctPercentage) {
        this.correctPercentage = correctPercentage;
    }
}
