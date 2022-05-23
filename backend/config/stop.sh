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

protos=( destinationrules virtualservices gateways configmap )
for proto in "${protos[@]}"; do
  for resource in $(kubectl get -n ${NAMESPACE} "$proto" -o name); do
    kubectl delete -n ${NAMESPACE} "$resource";
  done
done

OUTPUT=$(mktemp)
export OUTPUT
echo "Application cleanup may take up to one minute"
for yaml in *-deployment.yaml
do
    kubectl delete -n ${NAMESPACE} -f "$SCRIPTDIR/$yaml" > "${OUTPUT}" 2>&1
done
ret=$?
function cleanup() {
  rm -f "${OUTPUT}"
}

trap cleanup EXIT

if [[ ${ret} -eq 0 ]];then
  cat "${OUTPUT}"
else
  # ignore NotFound errors
  OUT2=$(grep -v NotFound "${OUTPUT}")
  if [[ -n ${OUT2} ]];then
    cat "${OUTPUT}"
    exit ${ret}
  fi
fi

echo "Application cleaned up successfully"
