FROM behrica/clj-py-r:1.7.0

#should be nearly always overridden during `docker build` with YOUR user id via `--build-arg USER_ID=$(id -u) --build-arg GROUP_ID=$(id -g)` or similar
ARG USER_ID=999
ARG GROUP_ID=999

RUN addgroup --gid $GROUP_ID user
RUN adduser --disabled-password --gecos '' --uid $USER_ID --gid $GROUP_ID user
RUN chown -R user:user /tmp/.m2/repository
USER user
WORKDIR /home/user
# RUN cp /usr/local/bin/APserver /home/user
# RUN apt-get install ....
RUN pip3 install cohere pylzma langchain 
# RUN Rscript -e 'install.packages("....",repo="http://cran.rstudio.com/")'
