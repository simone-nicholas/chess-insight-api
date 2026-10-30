package com.company.chessinsightapi.profile.dto;

public record LichessPerfs(
        LichessBulletPerfs bullet,
        LichessBlitzPerfs blitz
) {}