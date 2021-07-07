#!/bin/bash

mvn clean package && java -jar target/alloy-search-and-predict-web-interface-1.0-SNAPSHOT.war
