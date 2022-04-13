apk add bash
var=$(docker ps -alq)
export CONTAINER_ID="$var"
echo $CONTAINER_ID
docker cp $CONTAINER_ID:./app .
docker cp $CONTAINER_ID:./jar .
