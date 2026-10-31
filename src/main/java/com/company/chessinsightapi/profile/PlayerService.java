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

        // Rapid
        Integer rapidGame = null;
        Integer rapidRating = null;
        Integer rapidRd = null;
        Integer rapidProg = null;

        // Classical
        Integer classicalGame = null;
        Integer classicalRating = null;
        Integer classicalRd = null;
        Integer classicalProg = null;

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

            // Rapid
            if (response.perfs().rapid() != null) {
                rapidGame = response.perfs().rapid().games();
                rapidRating = response.perfs().rapid().rating();
                rapidRd = response.perfs().rapid().rd();
                rapidProg = response.perfs().rapid().prog();
            }

            // Classical
            if (response.perfs().classical() != null) {
                classicalGame = response.perfs().classical().games();
                classicalRating = response.perfs().classical().rating();
                classicalRd = response.perfs().classical().rd();
                classicalProg = response.perfs().classical().prog();
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

        GamePerf rapidObject = new GamePerf(
                rapidGame,
                rapidRating,
                rapidRd,
                rapidProg
        );

        GamePerf classicalObject = new GamePerf(
                classicalGame,
                classicalRating,
                classicalRd,
                classicalProg
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
                blitzObject,

                // Rapid
                rapidObject,

                // Classical
                classicalObject
        );
    }
}
