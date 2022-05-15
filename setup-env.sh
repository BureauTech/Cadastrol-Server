#!/bin/bash

ENV_TYPE=${1:-"dev"}
ENV_PATH="./src/main/resources/"
ENV_FILE="application.properties"
PG_HOST=$([[ "$ENV_TYPE" == "docker" ]] && echo "postgres" || echo "localhost")

if [ $# -eq 0 ]
then
    echo "using default env (dev)"
fi

executeDDL=1
while [[ $# -gt 0 ]]; do
    case "$1" in 
        -s|--skipDDL) executeDDL=0
    esac
    shift
done

if test -f "$ENV_PATH/application-$ENV_TYPE.properties"
then

    if [ $executeDDL -eq 1 ]
    then
        echo "configuring database"
        PGPASSWORD=postgres psql postgres -h $PG_HOST -d postgres -f postgres/ddl-database.sql
        echo -e "database successfully configured!\n"
    fi

    echo 'setting environment variables'
    echo -n > $ENV_PATH/application.properties
    cat $ENV_PATH/application-base.properties > $ENV_PATH/$ENV_FILE
    cat $ENV_PATH/application-$ENV_TYPE.properties >> $ENV_PATH/$ENV_FILE
    echo -e "env" $ENV_TYPE": successfully configured!\n"
else
    echo "env" $ENV_TYPE": error configuring environment! check if you chose a valid env."
    echo -e "available envs: dev, docker.\n"
fi
