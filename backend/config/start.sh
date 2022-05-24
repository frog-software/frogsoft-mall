#!/bin/bash

SCRIPTDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

# istioctl must exist
if ! which istioctl > /dev/null; then
  echo "istioctl not found in PATH"
  exit 1
fi

istioctl verify-install > /dev/null 2>&1 || {
  echo "You have not successfully installed istio. Please install instio on your cluster before running this script."
  exit 1
}

NAMESPACE=frogsoft-mall

echo "using NAMESPACE=${NAMESPACE}"
ns=$(kubectl get namespace "${NAMESPACE}" --no-headers --output=go-template="{{.metadata.name}}" 2>/dev/null)
if [[ -z ${ns} ]];then
  kubectl create ns frogsoft-mall
  kubectl label namespace frogsoft-mall istio-injection=enabled
  echo "已经为你自动创建命名空间 frogsoft-mall 并注入 istio"
fi
istioctl analyze --namespace "${NAMESPACE}"

IP=$(hostname --all-ip-addresses | grep -oE '^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\.|$|\w)){4}')
cp "${SCRIPTDIR}/configmap.yaml.example" "${SCRIPTDIR}/configmap.yaml"
sed -i "s/NACOS_IP_ADDRESS/${IP}/g" "${SCRIPTDIR}/configmap.yaml"
sed -i "s/OPENGAUSS_IP_ADDRESS/${IP}/g" "${SCRIPTDIR}/configmap.yaml"

find . -regextype egrep \
  -regex '.*(configmap|gateway|virtualservice|destinationrule|deployment).yaml' \
  -exec kubectl apply -n "${NAMESPACE}" -f {} \;

echo "Application started successfully"
