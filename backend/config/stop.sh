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

find . -regextype egrep \
  -regex '.*(gateway|virtualservice|destinationrule|deployment).yaml' \
  -exec kubectl delete -n "${NAMESPACE}" -f {} \;

echo "Application cleaned up successfully"
