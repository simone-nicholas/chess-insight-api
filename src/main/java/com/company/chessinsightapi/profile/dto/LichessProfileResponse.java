package com.company.chessinsightapi.profile.dto;

public record LichessProfileResponse(
        String realName,
        String bio,
        Integer fideRating
) {
}