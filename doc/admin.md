# Administration Guide

To include the ENES analytics portlets in Liferay, the portal administrator has to deploy the following jar modules into the portal:
- it.cmcc.indigo.enes.multi.model-x.y.z.jar
- it.cmcc.indigo.enes.monitoring-x.y.z.jar
- it.cmcc.indigo.enes.avg-x.y.z.jar
- it.cmcc.indigo.enes.max-x.y.z.jar
- it.cmcc.indigo.enes.min-x.y.z.jar
- it.cmcc.indigo.enes.std-x.y.z.jar
- it.cmcc.indigo.enes.var-x.y.z.jar

The module upload page is available on the portal available under `Liferay > Control Panel > Apps > App Manager`.

All the analytics portlets make use of the IAM token for user authentication and authorization. For this reason, the portal administrator should check that all the jars of the IAM authentication modules are deployed on the same Liferay portal.
