#!/bin/bash

mvn clean install

cp target/coordinador.war $WEB
