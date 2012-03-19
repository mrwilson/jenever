#!/bin/bash

export JEN_HOME=${JEN_HOME:=$HOME/.jen}
export JEN_CONFIG=$JEN_HOME/config
if [ ! -d "$JEN_HOME" ]; then
    java -jar target/jenever-0.0.1-jar-with-dependencies.jar --init
    touch $JEN_HOME/config
    exit 1
fi

while getopts ":m:" opt; do
	case $opt in
	m)
	echo "Changing JEN environment to $OPTARG"
	echo -e "#!/bin/bash\nexport JEN_ENV=$OPTARG" > $JEN_CONFIG
	source $HOME/.jen/config
	exit 1
	;;
	\?)
	;;
	:)
	echo "Option -$OPTARG requires an argument." >&2
	exit 1
	;;
	esac
done

source $JEN_HOME/config

java -jar target/jenever-0.0.1-jar-with-dependencies.jar $*
