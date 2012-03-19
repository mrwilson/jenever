#!/bin/bash

echo -e "export JEN_ENV=default\nexport JEN_HOME=$HOME/.jen" > $HOME/.jen/config

source $HOME/.jen/config

java -jar target/jenever-0.0.1-jar-with-dependencies.jar $*

