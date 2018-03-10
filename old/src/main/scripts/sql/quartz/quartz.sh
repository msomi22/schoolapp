#!/usr/bin/env bash
#
# This script initializes the database for the project.

# Created by peter mwenda, <peter@gmail.com> on july 09 2015

# Some notes on PostgreSQL:
# By default, PostgreSQL has a database called template1 containing privileges 
# and other housekeeping data and an adminstrative user named postgres. Unless 
# your system uses socket credentials, the postgres database user initially has 
# no password. To assign it a password (or to override the password assigned by 
# socket credentials), do as follows:
#
# * Become the user "postgres" by becoming superuser then "su - postgres"
# * Execute the following command:
# $> psql -c "ALTER USER postgres WITH PASSWORD 'newpassword'" -d template1
# The command above assigns the postgres user the password 'newpassword'.

#If you are creating the role and the database for the first time, then 
#you need to enable the lines below.
#Use these lines to automatically create a role in production environment 

#Begin automatic creation of role
DB_USERNAME="postgres"
DB_PASSWORD="postgres"
DB_HOST="localhost"

export PGUSER=$DB_USERNAME
export PGHOST=$DB_HOST
export PGPASSWORD=$DB_PASSWORD

DB_USERNAME="quartz_user"
DB_PASSWORD="quartz_password"
DB_HOST="localhost"

echo "Starting database initialization script..."

export PGUSER=$DB_USERNAME
export PGHOST=$DB_HOST
export PGPASSWORD=$DB_PASSWORD

psql -f quartz_postgres.sql -d postgres

echo "Have finished initializing database."
