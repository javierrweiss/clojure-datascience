FROM behrica/clj-py-r:1.5.1
RUN apt-get install sudo
RUN useradd -l -u 33333 -G sudo -md /home/gitpod -s /bin/bash -p gitpod gitpod \
    # passwordless sudo for users in the 'sudo' group
    && sed -i.bkp -e 's/%sudo\s\+ALL=(ALL\(:ALL\)\?)\s\+ALL/%sudo ALL=NOPASSWD:ALL/g' /etc/sudoers


RUN cp /usr/local/bin/APserver /home/gitpod

# RUN apt-get install ....
# RUN pip3 install ....
# RUN Rscript -e 'install.packages("....",repo="http://cran.rstudio.com/")'
