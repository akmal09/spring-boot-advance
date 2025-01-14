@echo off
REM Get current date and time
for /f "tokens=1-4 delims=/ " %%A in ("%DATE%") do (
    set Year=%%C
    set Month=%%A
    set Day=%%B
)
for /f "tokens=1-4 delims=:., " %%A in ("%TIME%") do (
    set Hour=%%A
    set Minute=%%B
    set Second=%%C
)

REM Format timestamp (e.g., 20250108_123045)
set Timestamp=%Year%%Month%%Day%_%Hour%%Minute%%Second%

@echo backup database
docker exec -it jwt-db pg_dump -U jwt-db -d jwt_auth_database -f jwt_db_backup_%Timestamp%.sql
timeout 20
@echo commit database
docker commit jwt-db akmal23/jwt-db-image:%Timestamp%
timeout 20
@echo push database
docker push akmal23/jwt-db-image:%Timestamp%