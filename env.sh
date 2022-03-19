#!/bin/sh

ENV_PATH="./src/main/resources/"
ENV_FILE="application.properties"
ENV_TYPE=${1:-"dev"}

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
    if [ "$ENV_TYPE" == "dev" ]
    then
        PGPASSWORD=postgres psql postgres -h localhost -d postgres -f $ENV_PATH/ddl-database.sql
    elif [ "$ENV_TYPE" == "prod" ]
    then
        PGPASSWORD=postgres psql postgres -h postgres -d postgres -f $ENV_PATH/ddl-database.sql
    fi
    echo "env" $ENV_TYPE": successfully configured!"
else
    echo "env" $ENV_TYPE": error configuring environment! check if you chose a valid env."
    echo "available envs: dev, prod."
fi
