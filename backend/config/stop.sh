#!/bin/bash

SCRIPTDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

NAMESPACE=frogsoft-mall

echo "using NAMESPACE=${NAMESPACE}"

find . -regextype egrep \
  -regex '.*(virtualservice|destinationrule|gateway|deployment|configmap).yaml' \
  -exec kubectl delete -n "${NAMESPACE}" -f {} \;

echo "Application cleaned up successfully"
