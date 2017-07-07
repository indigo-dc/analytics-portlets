# Service Reference Card

**Functional description:**

  Provide the possibility to execute an intercomparison between models through a first portlet providing a form to select the input parameters and the submission and other two portlets to visualize the corresponding map and the nc-dump file.

**Services running:**

  * Tomcat8 needed by Liferay
  
 **Configuration:**
 
  The modules add new panels to the Liferay portal providing the possibility to select a series of input parameters and submit a models intercomparison experiment over the INDIGO-Data Cloud infrastructure. Other two modules will get the results of the submission and visualize the produced map and the related nc-dump file.
  
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



