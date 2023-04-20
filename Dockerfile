FROM alpine
WORKDIR /usr/local/bin
LABEL maintainerAndreas Caternberg <andreas.caternberg@gmail.com>

ENV DOCKER_VERSION=18.06.1-ce KUBECTL_VERSION=v1.20 HELM_VERSION=v3.0 JQ_VERSION=1.6

ADD /scripts /opt/ci-toolbox

RUN chmod +x /opt/ci-toolbox/* \
    && for file in /opt/ci-toolbox/*.sh ;do ln -s $file /usr/local/bin;done

#USER root
RUN apk add --no-cache make \
    ca-certificates \
    bash \
    curl \
    diffutils \
    jq \
    yq \
    git

#Kubectl
RUN curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl; chmod +x ./kubectl; mv ./kubectl /usr/local/bin/kubectl
#RUN echo $PATH

