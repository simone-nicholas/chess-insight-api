package com.company.chessinsightapi.profile.dto;

public record LichessPerfs(
        GamePerf bullet,
        GamePerf blitz,
        GamePerf rapid,
        GamePerf classical
) {}