#! /bin/bash
#see JQ documentation and samples:
#https://medium.com/how-tos-for-coders/https-medium-com-how-tos-for-coders-parse-json-data-using-jq-and-curl-from-command-line-5aa8a05cd79b
#https://stedolan.github.io/jq/manual/


#simple git fake data: TODO: addjust to BitBucket REST API and specific command
GIT_REPSONSE_JSON=$(curl -s https://www.githubstatus.com/api/v2/status.json)
echo $GIT_REPSONSE_JSON | jq
echo $GIT_REPSONSE_JSON | jq '.page.url'
#{
#  "page": {
#    "id": "kctbh9vrtdwd",
#    "name": "GitHub",
#    "url": "https://www.githubstatus.com",
#    "time_zone": "Etc/UTC",
#    "updated_at": "2023-04-24T08:12:01.511Z"
#  },
#  "status": {
#    "indicator": "none",
#    "description": "All Systems Operational"
#  }
#}

#echo '{"fruit":{"name":"apple","color":"green","price":1.20}}' | jq '.'
#echo '{"fruit":{"name":"apple","color":"green","price":1.20}}' | jq '.fruit.color'