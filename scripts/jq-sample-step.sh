#! /bin/bash

echo '{"fruit":{"name":"apple","color":"green","price":1.20}}' | jq '.'

echo '{"fruit":{"name":"apple","color":"green","price":1.20}}' | jq '.fruit.color'