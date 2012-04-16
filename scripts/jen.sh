#!/bin/bash

export JEN_HOME=${JEN_HOME:=$HOME/.jen}
export JEN_CONFIG=$JEN_HOME/config

#If there's no JEN_HOME, make it and the config
if [ ! -d "$JEN_HOME" ]; then
    java -jar $JEN_HOME/jenever.jar --init
    
fi

#Get our JEN_ENV variable
source $JEN_HOME/config

java -jar $JEN_HOME/jenever.jar $*
