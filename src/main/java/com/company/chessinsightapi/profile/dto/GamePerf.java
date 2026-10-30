package com.company.chessinsightapi.profile.dto;

public record GamePerf(
        Integer games,
        Integer rating,
        Integer rd,
        Integer prog
) {
}