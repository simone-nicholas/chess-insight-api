package com.company.chessinsightapi.profile.dto;

public record LichessUserResponse(
        String id,
        String username,
        String country,
        String flair,
        Long createdAt,
        String url,
        LichessProfileResponse profile,
        LichessPerfs perfs
) {
}