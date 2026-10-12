# Backend contract

CalmCloud contains an Android client, not a backend implementation. This document describes the contract consumed by [`ApiService.kt`](../app/src/main/java/com/example/calmcloud/api/ApiService.kt), so a reviewer can understand the integration and a developer can connect a compatible service.

## Wellness service

All routes are relative to `CALMCLOUD_API_BASE_URL`.

| Method | Route | Request | Successful response |
| --- | --- | --- | --- |
| GET | `/api` | None | Any successful HTTP status; body ignored |
| POST | `/login` | User | User |
| POST | `/register` | User | User |
| GET | `/moods` | None | Array of Mood |
| POST | `/moods` | Mood | Mood |
| GET | `/stress-levels` | None | Array of StressLevel |
| POST | `/stress-levels` | StressLevel | StressLevel |
| GET | `/sleep` | None | Array of Sleep |
| POST | `/sleep` | Sleep | Sleep |

These examples use synthetic values. Field names and enum spelling must match the Kotlin models.

```json
{
  "id": "example-mood-id",
  "date": "10-12-2026",
  "moodType": "CALM"
}
```

```json
{
  "id": "example-stress-id",
  "date": "10-12-2026",
  "stressLevels": ["LOW_STRESS"]
}
```

```json
{
  "id": "example-sleep-id",
  "date": "10-12-2026",
  "sleepDurationStart": "10:30 PM",
  "sleepDurationEnd": "6:30 AM",
  "sleepQuality": 4,
  "sleepFactors": "Quiet room"
}
```

The `User` request model has `email` and `password` string fields. The prototype reuses it as a response model, but the UI does not need a returned password. A production service should return a dedicated identity/session response, and the client should be updated together with that API. User isolation, password storage, sessions, uniqueness, retention, and deletion require backend implementation; they are not established by these client models.

## Chat proxy

`CALMCLOUD_CHAT_BASE_URL` is optional and empty by default. When configured, the client sends an ordered conversation to `POST /v1/chat/completions`:

```json
{
  "model": "your-provider-model-id",
  "messages": [
    {"role": "user", "content": "I would like to reflect on my day."}
  ]
}
```

The response shape consumed by the UI is:

```json
{
  "choices": [
    {"message": {"role": "assistant", "content": "What stood out today?"}}
  ]
}
```

The developer-operated proxy supplies provider credentials. The Android client does not currently send an authenticated application session to this proxy. Do not expose an unrestricted provider-backed proxy publicly. Configure HTTPS for device/cloud access and add authentication and request limits as a coordinated client/backend change.

## Build configuration

| Gradle property | Default |
| --- | --- |
| `CALMCLOUD_API_BASE_URL` | `http://10.0.2.2:3000/` |
| `CALMCLOUD_CHAT_BASE_URL` | Empty: chat is unconfigured |
| `CALMCLOUD_CHAT_MODEL` | `gpt-3.5-turbo`, retained from the original prototype; override for your proxy |

Endpoint settings are compiled into `BuildConfig`, so rebuild after changing them. `local.properties`, API keys, and environment-specific credentials should stay out of version control.
