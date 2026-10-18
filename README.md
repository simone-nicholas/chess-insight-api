

# Chess Insight API

REST API built with Spring Boot that wraps the public [Chess.com API](https://www.chess.com/news/view/published-data-api) and exposes player profiles, current ratings and rating history through a clean, provider-independent response model.

## Features

- Player profile lookup
- Current ratings for every time control (rapid, blitz, bullet, daily) in a single call
- Rating history built from the monthly game archives (rated standard chess only)
- Chess.com DTOs kept separate from the public response DTOs, so the external format never leaks to clients
- Stateless security with HTTP Basic

## Tech stack

- Java 26
- Spring Boot 4.1 (Spring MVC, Spring Security)
- `RestClient` for outgoing HTTP calls
- Maven

## Getting started

### Prerequisites

- JDK 26 (or the version configured in `pom.xml`)
- Maven (or the included wrapper)

### Run

```bash
git clone <repo-url>
cd chess-insight-api
export APP_PASSWORD=choose-a-password
./mvnw spring-boot:run
```

The app starts on `http://localhost:8080`.

### Configuration

`src/main/resources/application.properties`:

```properties
spring.security.user.name=admin
spring.security.user.password=${APP_PASSWORD}
```

Never commit real credentials: read them from environment variables.

## API

Base path: `/api/v1`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/players/{username}` | Player profile |
| GET | `/players/{username}/ratings` | Current rating per time control |
| GET | `/players/{username}/rating-history` | Rating over time, from game archives |

### Rating history parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| `timeClass` | `rapid` | `rapid`, `blitz`, `bullet` or `daily` |
| `months` | `3` | Number of most recent archive months to read (capped at 12) |

### Examples

```bash
curl http://localhost:8080/api/v1/players/hikaru/ratings
```

```json
{ "rapid": 1456, "blitz": 1191, "bullet": 1194, "daily": null }
```

```bash
curl "http://localhost:8080/api/v1/players/hikaru/rating-history?timeClass=blitz&months=6"
```

```json
{
  "username": "hikaru",
  "timeClass": "blitz",
  "points": [
    {
      "date": "2026-05-03T18:22:10Z",
      "rating": 1175,
      "color": "white",
      "result": "win",
      "url": "https://www.chess.com/game/live/..."
    }
  ]
}
```

A time control the player has never played is returned as `null`.

## Project structure

```
com.company.chessinsightapi
├── config      # security and HTTP client configuration
├── player      # controller, service and public DTOs
└── chesscom    # Chess.com DTOs and client code
```

## Security

The app is stateless, with CSRF disabled and HTTP Basic authentication. Rules are defined in `SecurityConfig`: adjust them to decide which endpoints are public. For production use, consider OAuth2/JWT.

## Roadmap

- [ ] Caching (Caffeine): long TTL for past months, short TTL for the current one
- [ ] Global error handling (`@RestControllerAdvice`) for upstream 404s
- [ ] Request validation for `timeClass` (enum)
- [ ] Insights: win rate by color, monthly rating change, most played openings

## Disclaimer

This project is not affiliated with Chess.com. Data comes from their public API and is subject to their terms and rate limits.