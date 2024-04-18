package com.revolt;

public class UserNew {
    public String name;
    public String id;
    public String email;
    public String profile;

    public int score;

    public int scoreMedium;
    public int scoreHard;


    public UserNew(String name, String id, String email, String profile, int score, int scoreMedium, int scoreHard){
        this.name = name;
        this.id = id;
        this.email = email;
        this.profile = profile;
        this.score = score;
        this.scoreMedium = scoreMedium;
        this.scoreHard = scoreHard;
    }

    public int getScore() {
        return score;
    }

    public int getScoreHard() {
        return scoreHard;
    }

    public int getScoreMedium() {
        return scoreMedium;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
    public String getProfile() {
        return profile;
    }
}
