#! /bin/bash
#see https://kubernetes.io/docs/tasks/configure-pod-container/configure-pod-configmap/
#see https://kubernetes.io/docs/tasks/configure-pod-container/configure-pod-configmap/#populate-a-volume-with-data-stored-in-a-configmap
CM=pod-build-scripts
VERSION=0.1
kubectl delete cm $CM
kubectl create cm $CM  --from-file=resources/scripts/
kubectl label --overwrite cm $CM version=$VERSION
kubectl describe cm $CM