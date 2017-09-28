# Service Reference Card

**Functional description:**

Provide the possibility to execute an intercomparison between models through a first portlet providing a form to select the input parameters, a module to monitor the experiments submitted and the visualisation portlets to visualize the corresponding map files.

**Services running:**

  * Tomcat8 needed by Liferay
  
**Configuration:**
 
The ENES analytics portlets needs an external file, in JSON format, to correctly map the different climate models with the related data centers. The file models-mapping.json should be provided and should be similar to the example below. 

{
    "models": [{
        "model": "\<model>",
        "host": "\<hostname>",
        "port": "\<port>",
        "username": "\<username>",
        "password": "\<password>"
    },{
        "model": "\<model>",
        "host": "\<hostname>",
        "port": "\<port>",
        "username": "\<username>",
        "password": "\<password>"
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



