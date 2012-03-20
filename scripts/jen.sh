#!/bin/bash

export JEN_HOME=${JEN_HOME:=$HOME/.jen}
export JEN_CONFIG=$JEN_HOME/config

#If there's no JEN_HOME, make it and the config
if [ ! -d "$JEN_HOME" ]; then
    java -jar target/jenever-0.0.1-jar-with-dependencies.jar --init
    touch $JEN_HOME/config
    exit 1
fi

#Change environment variable.
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

#Get our JEN_ENV variable
source $JEN_HOME/config

java -jar target/jenever-0.0.1-jar-with-dependencies.jar $*
