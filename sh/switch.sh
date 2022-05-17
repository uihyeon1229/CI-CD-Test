##!/usr/bin/env bash
#
## switch.sh
## nginx 연결 설정 스위치
#
#ABSPATH=$(readlink -f $0)
#ABSDIR=$(dirname $ABSPATH)
#source ${ABSDIR}/profile.sh
#
#function switch_proxy() {
#    IDLE_PORT=$(find_idle_port)
#
#    echo "> 전환할 Port: $IDLE_PORT"
#    echo "> Port 전환"
#    # nginx와 연결한 주소 생성
#    # | sudo tee ~ : 앞에서 넘긴 문장을 service-url.inc에 덮어씀
#    echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc
#
#    echo "> 엔진엑스 Reload"
#    # nignx reload. restart와는 다르게 설정 값만 불러옴
#    sudo service nginx reload
#}

#!/usr/bin/env bash

echo "> 현재 구동중인  Port 확인"
CURRENT_PROFILE=$(curl -s http://localhost/profile)

if [ $CURRENT_PROFILE == dev ]
then
  IDLE_PORT=8082
elif [ $CURRENT_PROFILE == dev2 ]
then
  IDLE_PORT=8081
else
  echo "> 일치하는 Profile이 없습니다. Profile: $CURRENT_PROFILE"
  echo "> 8081을 할당합니다."
  IDLE_PORT=8081
fi

echo "> 전환할 Port: $IDLE_PORT"
echo "> Port 전환"
echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" |sudo tee /etc/nginx/conf.d/service-url.inc

PROXY_PORT=$(curl -s http://localhost/profile)
echo "> Nginx Current Proxy Port: $PROXY_PORT"

echo "> Nginx Reload"
sudo service nginx reload # reload는 설정만 재적용하기 때문에 바로 적용이 가능합니다.