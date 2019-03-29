package com.sktl.nbrbcurrency;

import java.util.Comparator;

public class Quotation {


    private String id;
    private String date;
    private String abbreviation;
    private String name;
    private String scale;
    private String rate;
    private boolean chosen;
    private int position;

    @Override
    public String toString() {
        return "Quotation{" +
                "date='" + date + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", name='" + name + '\'' +
                ", scale='" + scale + '\'' +
                ", rate='" + rate + '\'' +
                ", isChosen='" + chosen + '\'' +
                ", position='" + position + '\'' +
                '}' + "\n";
    }


    public static Comparator<Quotation> COMPARE_BY_POSITION = new Comparator<Quotation>() {
        public int compare(Quotation one, Quotation other) {
            if (one.getPosition() > other.getPosition()) return -1;
            if (one.getPosition() == other.getPosition()) return 0;

            else return 1;
        }
    };


    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean choose) {
        this.chosen = choose;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
