FROM behrica/clj-py-r:1.7.0

#should be nearly always overridden during `docker build` with YOUR user id via `--build-arg USER_ID=$(id -u) --build-arg GROUP_ID=$(id -g)` or similar
ARG USER_ID=999
ARG GROUP_ID=999
ARG COHEREKEY
ENV COHEREKEY $COHEREKEY
RUN addgroup --gid $GROUP_ID user
RUN adduser --disabled-password --gecos '' --uid $USER_ID --gid $GROUP_ID user
RUN chown -R user:user /tmp/.m2/repository
RUN apt-get -y update && apt-get -y upgrade && apt-get install -y sqlite3 libsqlite3-dev && apt-get clean
USER user
WORKDIR /home/user
RUN curl https://pyenv.run | bash
ENV HOME /home/user
ENV PYENV_ROOT $HOME/.pyenv
ENV PATH $PYENV_ROOT/shims:$PYENV_ROOT/bin:$PATH
ENV PYTHON_CONFIGURE_OPTS "--enable-loadable-sqlite-extensions"
RUN pyenv install 3.10.0 && pyenv global 3.10.0
# RUN cp /usr/local/bin/APserver /home/user
# RUN apt-get install ....
RUN pip3 install -U --no-cache-dir pip && \ 
    pip3 install -U --no-cache-dir  \
    numpy \
    wheel \
    scikit-learn \
    cython \
    cohere \
    unstructured \
    pypdf \
    pdfminer.six \
    pdf2image \
    langchain \
    opencv-contrib-python
RUN export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64 && pip3 install cljbridge==0.0.7

# RUN Rscript -e 'install.packages("....",repo="http://cran.rstudio.com/")'
