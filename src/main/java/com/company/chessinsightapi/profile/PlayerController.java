package com.company.chessinsightapi.profile;

import com.company.chessinsightapi.profile.dto.GamePerf;
import com.company.chessinsightapi.profile.dto.PlayerProfileDto;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/user/{username}")
    public PlayerProfileDto getUser (@PathVariable String username) {
        return playerService.getPlayerProfile(username);
    }

    @GetMapping("/user/stats/{username}")
    public Object getUserStats(
            @PathVariable String username,
            @RequestParam(required = false) String type
    ) {
        PlayerProfileDto profile = playerService.getPlayerProfile(username);

        if ("blitz".equalsIgnoreCase(type)) {
            return playerService.getPlayerProfile(username).blitzObject();
        } else if ("bullet".equalsIgnoreCase(type)){
            return playerService.getPlayerProfile(username).bulletObject();
        }

        return Map.of(
                "blitz", profile.blitzObject(),
                "bullet", profile.bulletObject()
        );
    }
}
