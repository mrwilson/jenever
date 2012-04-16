#!/bin/bash

set -eu

mvn clean package
export JEN_HOME=${JEN_HOME:=$HOME/.jen}
export JEN_CONFIG=$JEN_HOME/config

mkdir $JEN_HOME
touch $JEN_CONFIG

cp target/jenever.jar $JEN_HOME
cp -r scripts $JEN_HOME
