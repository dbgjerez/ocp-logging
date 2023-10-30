source utils/demo-magic.sh

pei "# DEMO - OpenShift Logging"

pei "## Installing Elasticsearch operator"
pei "oc create ns openshift-operators-redhat"
pei "oc apply -f operators/elasticsearch.yaml"
pei 'oc label namespace openshift-operators-redhat openshift.io/cluster-monitoring="true"'

pei "## Installing Cluster logging operator"
pei "oc create ns openshift-logging"
pei "oc apply -f operators/cluster-logging.yaml"
pei 'oc label namespace openshift-logging openshift.io/cluster-monitoring="true"'

pei '# Waiting for a user press enter'
pe 'oc apply -f cl-instance.yaml'
pei 'oc get po -n openshift-logging -w'