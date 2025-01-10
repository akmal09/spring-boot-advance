FROM postgres:latest

# Set the working directory inside the container
WORKDIR /docker-entrypoint-initdb

# Copy the database dump into the container
COPY db_backup.sql /docker-entrypoint-initdb/db_backup.sql

# Set environment variables
ENV POSTGRES_DB=mydatabase
ENV POSTGRES_USER=myuser
ENV POSTGRES_PASSWORD=1234

# PostgreSQL automatically initializes the database with dump files in /docker-entrypoint-initdb.d