#!/bin/bash

set -eu

echo -e "Building package from source..."
mvn -q -DskipTests clean package
echo -e "Done\n"

export JEN_HOME=${JEN_HOME:=$HOME/.jen}
export JEN_CONFIG=$JEN_HOME/config

echo -e "Making JEN_HOME directory and config at $JEN_HOME"
mkdir $JEN_HOME
touch $JEN_CONFIG
echo -e "Done\n"

echo "Copying jar and scripts to $JEN_HOME"
cp -v target/jenever.jar $JEN_HOME
cp -rv scripts/linux $JEN_HOME
echo -e "Done\n"

echo "Install complete"
