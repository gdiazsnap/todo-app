-- Seed default user with hardcoded UUID (idempotent using MERGE)
MERGE INTO users (id, username, created_at, updated_at)
KEY (id)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'Default User', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
