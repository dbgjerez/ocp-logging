source utils/demo-magic.sh

pe "# DEMO - OpenShift Logging"
pei "## Installing Elasticsearch operator"
pei "oc create ns openshift-operators-redhat"
pei "oc apply -f operators/elasticsearch.yaml"