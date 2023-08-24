#!/usr/bin/env bash
#set -euo pipefail

#sudo docker run -it --rm -v $HOME/.m2:/home/jrivero/.m2 -v "$(pwd):/workdir" -p  12345:12345 -w /workdir clojure-datascience 

docker run -it --rm --user $(id -u):$(id -g) -v $HOME/.m2:/home/user/.m2 -v "$(pwd):/workdir" -p  12345:12345 -w /workdir clojure-datascience
