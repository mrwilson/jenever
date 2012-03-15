#!/usr/bin/env sh

export JEN_ENV=foo
export JEN_HOME=~/.jen
FOO=$M2_HOME
echo $FOO
export JEN_TARGET=$JEN_HOME/$JEN_ENV
echo $JEN_TARGET

