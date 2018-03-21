#!/bin/sh

declare project_dir=$(dirname $0)
declare docker_compose_file=${project_dir}/docker-compose.yml
declare techcorner="techcorner"

function start() {
    echo 'Starting techcorner....'
    build_app
    stop
    docker-compose -f ${docker_compose_file} up --build --force-recreate -d ${techcorner}
    docker-compose -f ${docker_compose_file} logs -f
}

function deps() {
    echo 'Starting techcorner dependencies....'
    docker-compose -f ${docker_compose_file} up --build --force-recreate -d postgres-db grafana prometheus
    docker-compose -f ${docker_compose_file} logs -f
}

function stop() {
    echo 'Stopping techcorner....'
    docker-compose -f ${docker_compose_file} stop
    docker-compose -f ${docker_compose_file} rm -f
}

function build_app() {
    ./mvnw clean package -DskipTests
}

action="start"

if [ $1 != "0"  ]
then
    action=$@
fi

eval ${action}