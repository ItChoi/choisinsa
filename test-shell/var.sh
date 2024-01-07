TEST="1333323"
echo $TEST

hello="world1"
Hello="world2"
HELLO="world3"

echo "${hello}"
echo "${Hello}"
echo "${HELLO}"

E="aaa"
EE="bbb"
EEE="ccc"

echo ${E}
echo ${EE}
echo ${EEE}

echo "$E EE"
echo "$E $EE"
echo "${E} ${EE}"

TEST="123" TTTT="234"
echo ${TEST}
echo ${TTTT}
