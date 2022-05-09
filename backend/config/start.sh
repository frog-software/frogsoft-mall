#!/bin/bash

SCRIPTDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

# only ask if in interactive mode
if [[ -t 0 && -z ${NAMESPACE} ]];then
  echo -n "namespace ? [frogsoft-mall] "
  read -r NAMESPACE
fi

# verify if the namespace exists, otherwise use default namespace
if [[ -n ${NAMESPACE} ]];then
  ns=$(kubectl get namespace "${NAMESPACE}" --no-headers --output=go-template="{{.metadata.name}}" 2>/dev/null)
  if [[ -z ${ns} ]];then
    echo "NAMESPACE ${NAMESPACE} not found."
    NAMESPACE=frogsoft-mall
  fi
fi

# if no namespace is provided, use default namespace
if [[ -z ${NAMESPACE} ]];then
  NAMESPACE=frogsoft-mall
fi

echo "using NAMESPACE=${NAMESPACE}"

IP=$(hostname --all-ip-addresses | grep -oE '^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\.|$|\w)){4}')
echo -e "NACOS_URL=$IP" > url.env.local
echo -e "OPENGAUSS_URL=$IP" >> url.env.local
kubectl create configmap frogsoft-mall-config --from-env-file=url.env.local -n ${NAMESPACE}

for yaml in *-deployment.yaml
do
    kubectl apply -n ${NAMESPACE} -f "$SCRIPTDIR/$yaml" 
done

protos=( gateway virtualservice destinationrule )
for proto in "${protos[@]}"; do
  for yaml in *${proto}.yaml
  do
      kubectl apply -n ${NAMESPACE} -f "$SCRIPTDIR/$yaml" 
  done
done



echo "Applications start successfully"