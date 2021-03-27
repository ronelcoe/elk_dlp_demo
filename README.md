# elk_dlp_demo
1. Access kibana and import json for visualization

2. Generate dummy log
curl http://localhost:8080/dlp/load/_data_elk_sensitiveData
curl http://localhost:8080/dlp/generateLog/_data_elk_applogs_app1.log10
curl http://localhost:8080/dlp/generateLog/_data_elk_applogs_app2.log100
curl http://localhost:8080/dlp/generateLog/_data_elk_applogs_app3.log500

3. View logs in dashboard
