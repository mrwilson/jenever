Insant, a set of annotations to provide debugging at runtime.

# How to use

## Building
    git clone https://github.com/mrwilson/jenever
    cd jenever
    mvn clean package

## Uses

Jenever has two functions: a small dependency manager using the maven repositories, and an environment manager.

##
    # Add this to your .bashrc, to use the environment switcher
    source /path/to/jenever/scripts/jenutil

    ./scripts/jen.sh --init
    ./scripts/jen.sh -m foobar
    ./scripts/jen.sh --install <groupid>:<artifactid>:<version>

Not fully tested, use at your own risk.
