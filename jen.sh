#!/usr/bin/env sh

export JEN_ENV=foo
export JEN_HOME=~/.jen/$JEN_ENV

java -jar target/jenever-0.0.1-SNAPSHOT-jar-with-dependencies.jar $*

