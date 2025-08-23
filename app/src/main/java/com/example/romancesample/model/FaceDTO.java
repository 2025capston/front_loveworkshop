package com.example.romancesample.model;

public class FaceDTO {
    private Integer userId;
    private String faceEmbedding;

    public FaceDTO(Integer userId, String faceEmbedding) {
        this.userId = userId;
        this.faceEmbedding = faceEmbedding;
    }

    // getter, setter 필요시 추가
}
