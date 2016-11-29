package com.kelimeezberimde;

public class Words {
    private String word;
    private String mean;
    private String imageUrl;

    public Words(String word, String mean, String imageUrl) {
        this.word = word;
        this.mean = mean;
        this.imageUrl = imageUrl;
    }

    public String getWord() {
        return word;
    }

    public String getMean() {
        return mean;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}