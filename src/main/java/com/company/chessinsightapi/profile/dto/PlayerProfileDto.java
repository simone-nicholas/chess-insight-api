package com.company.chessinsightapi.profile.dto;

import java.time.Instant;

public record PlayerProfileDto(
        // Profile
        String username,
        String realName,
        String bio,
        Integer fideRating,
        String lichessUrl,
        Instant memberSince,

        // Bullet
        GamePerf bulletObject,

        // Blitz
        GamePerf blitzObject
) {
}