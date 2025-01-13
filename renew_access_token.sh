#!/bin/bash

# appKey, appSecret은 Github Secrets에서 받아옵니다
appKey=$API_APP_KEY
appSecret=$API_APP_SECRET

# 토큰 발급 요청
response=$(curl -s -X POST \
  https://openapi.koreainvestment.com:9443/oauth2/tokenP \
  -H "Content-Type: application/json" \
  -d "{\"grant_type\":\"client_credentials\", \"appkey\":\"$appKey\", \"appsecret\":\"$appSecret\"}")

# Access token 추출
accessToken=$(echo $response | jq -r .access_token)

# application.properties 파일 업데이트
sed -i "s/^api.access_token=.*/api.access_token=$accessToken/" ./investement/src/main/resources/application.properties
