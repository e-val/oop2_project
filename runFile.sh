#!/bin/bash

rm -rf bin

mkdir bin
cp -rf style bin/style
cp -rf img bin/img

javac -cp "bin" -sourcepath src -d bin src/exec/Main.java
java -cp "bin" exec.Main



