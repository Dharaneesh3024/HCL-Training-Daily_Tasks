#!/bin/bash
mkdir -p out
javac -d out src/payment/*.java src/app/*.java && java -cp out app.Main
