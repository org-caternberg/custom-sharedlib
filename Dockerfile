FROM ubuntu

ADD /scripts /opt/ci-toolbox

RUN chmod +x /opt/ci-toolbox/* \
    && for file in /opt/ci-toolbox/*.sh ;do ln -s $file /usr/local/bin;done

WORKDIR $HOME

