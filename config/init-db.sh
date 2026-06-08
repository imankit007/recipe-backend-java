#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE recipe_user_db OWNER postgres;
EOSQL

echo "Database recipe_user_db created successfully"


