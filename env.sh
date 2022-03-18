#!/bin/sh

ENV_PATH='./src/main/resources/'
ENV_FILE='application.properties'
ENV_TYPE=${1:-'dev'}

echo -n > $ENV_PATH/application.properties
cat $ENV_PATH/application-base.properties > $ENV_PATH/$ENV_FILE
cat $ENV_PATH/application-$ENV_TYPE.properties >> $ENV_PATH/$ENV_FILE
