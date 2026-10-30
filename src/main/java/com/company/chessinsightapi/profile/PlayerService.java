package com.company.chessinsightapi.profile;

import com.company.chessinsightapi.profile.dto.GamePerf;
import com.company.chessinsightapi.profile.dto.LichessUserResponse;
import com.company.chessinsightapi.profile.dto.PlayerProfileDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PlayerService {

    private final LichessClient lichessClient;

    public PlayerService(LichessClient lichessClient) {
        this.lichessClient = lichessClient;
    }

    @Cacheable("players")
    public PlayerProfileDto getPlayerProfile(String username) {
        LichessUserResponse response = lichessClient.fetchUser(username);

        // Profile
        String realName = null;
        String bio = null;
        Integer fideRating = null;

        // Bullet
        Integer bulletGames = null;
        Integer bulletRating = null;
        Integer bulletRd = null;
        Integer bulletProg = null;

        // Blits
        Integer blitzGames = null;
        Integer blitzRating = null;
        Integer blitzRd = null;
        Integer blitzProg = null;

        if (response.profile() != null) {
            // Profile
            realName = response.profile().realName();
            bio = response.profile().bio();
            fideRating = response.profile().fideRating();

        }

        if (response.perfs() != null) {
            // Bullet
            if (response.perfs().bullet() != null) {
                bulletGames = response.perfs().bullet().games();
                bulletRating = response.perfs().bullet().rating();
                bulletRd = response.perfs().bullet().rd();
                bulletProg = response.perfs().bullet().prog();
            }

            // Blitz
            if (response.perfs().blitz() != null) {
                blitzGames = response.perfs().blitz().games();
                blitzRating = response.perfs().blitz().rating();
                blitzRd = response.perfs().blitz().rd();
                blitzProg = response.perfs().blitz().prog();
            }
        }

        Instant memberSince = response.createdAt() != null
                ? Instant.ofEpochMilli(response.createdAt())
                : null;

        GamePerf bulletObject = new GamePerf(
                bulletGames,
                bulletRating,
                bulletRd,
                bulletProg
        );

        GamePerf blitzObject = new GamePerf(
                blitzGames,
                blitzRating,
                blitzRd,
                blitzProg
        );

        return new PlayerProfileDto(
                // Profile
                response.username(),
                realName,
                bio,
                fideRating,
                response.url(),
                memberSince,

                // Bullet
                bulletObject,

                // Blitz
                blitzObject
        );
    }
}
