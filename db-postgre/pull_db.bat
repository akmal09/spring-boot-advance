@echo off

echo Pulling the latest image:
docker pull akmal23/jwt-db-image:20251401_162352

REM Stop and remove any existing container with the same name
docker rm jwt-db

REM Create and run a new container from the latest image
echo Creating and running the container: %ContainerName%..
docker run --name jwt-db -e POSTGRES_USER=jwt-db -e POSTGRES_PASSWORD=myuser1234 -e POSTGRES_DB=jwt_auth_database -p 4000:5432 -d akmal23/jwt-db-image:20251401_162352

REM Wait for PostgreSQL to start
echo Waiting for PostgreSQL to initialize...
timeout 15

REM Execute the SQL file inside the container to create the database
echo Running SQL file to create the database...
docker exec -it jwt-db psql -U jwt-db -c CREATE DATABASE jwt_auth_database
timeout 15
docker exec -it jwt-db psql -U jwt-db -d jwt_auth_database -f /jwt_db_backup_20251401_162352.sql

echo Database setup completed.