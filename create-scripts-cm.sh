#! /bin/bash

CM=pod-build-scripts
VERSION=0.1
kubectl delete cm $CM
kubectl create cm $CM  --from-file=scripts/
kubectl label --overwrite cm $CM version=$VERSION
kubectl describe cm $CM