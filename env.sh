#!/bin/bash

ENV_TYPE=${1:-"dev"}
ENV_PATH="./src/main/resources/"
ENV_FILE="application.properties"
PG_HOST=$([[ "$ENV_TYPE" == "prod" ]] && echo "postgres" || echo "localhost")

if [ $# -eq 0 ]
then
    echo "using default env (dev)"
fi

if test -f "$ENV_PATH/application-$ENV_TYPE.properties"
then
    echo 'setting environment variables'
    echo -n > $ENV_PATH/application.properties
    cat $ENV_PATH/application-base.properties > $ENV_PATH/$ENV_FILE
    cat $ENV_PATH/application-$ENV_TYPE.properties >> $ENV_PATH/$ENV_FILE
    echo "configuring database"
    PGPASSWORD=postgres psql postgres -h $PG_HOST -d postgres -f $ENV_PATH/ddl-database.sql
    echo "env" $ENV_TYPE": successfully configured!"
else
    echo "env" $ENV_TYPE": error configuring environment! check if you chose a valid env."
    echo "available envs: dev, prod."
fi
