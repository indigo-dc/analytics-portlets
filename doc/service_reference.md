# Service Reference Card

**Functional description:**

Provide the possibility to execute an intercomparison between models through a first portlet providing a form to select the input parameters, a module to monitor the experiments submitted and the visualisation portlets to visualize the corresponding map files.

**Services running:**

  * Tomcat8 needed by Liferay
  
**Configuration:**

The ENES analytics portlets rely on the Future Gateway API server available at the link: https://fgw01.ncg.ingrid.pt/apis/v1.0.

The data-analytics-multi-model portlet needs two external files to correctly submit a multi-model experiment through the Kepler-batch application:
1. the tosca_template.yaml file, a TOSCA example to specify a Chronos Job that runs a batch Kepler job;
2. the models-mapping.json file, where there is a mapping between the different climate models and the related data centers.

The file models-mapping.json should be similar to the example below. 

{
    "models": [{
        "model": "<model>",
        "host": "<hostname>",
        "port": "<port>",
        "username": "<username>",
        "password": "<password>"
    },{
        "model": "<model>",
        "host": "<hostname>",
        "port": "<port>",
        "username": "<username>",
        "password": "<password>"
    }]  
}
  
**Logfile locations (and management) and other useful audit information:**

  * Liferay log: the analytics portlets write their log in the Liferay log files under a tomcat outside of the CATALINA_HOME.
  
**Open ports:**

  Liferay Server  
  * 80 and 443
  
**Where is service state held (and can it be rebuilt):**

  Information on the configuration are managed by Liferay which mantains the state either on the restart and/or during the update of the modules.
  
**Cron jobs:**

No cron jobs needed by the analytics portlets.

**Security information:**

Security is managed by Liferay and the communications between the components of the project infrastructure are secured by the IAM token which have to be preserved through *https* communication protocol.

**Location of reference documentation:**
   [Analytics Portlets on Gitbook](https://www.gitbook.com/book/indigo-dc/analytics-portlets/details)



