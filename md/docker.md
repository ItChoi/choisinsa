# 도커
- 컨테이너  
  - 격리된 환경에서 작동하는 프로세스
  - 서버 관리 혁신
  - 서버의 효율적인 관리 가능
  - 클러스터링
  - 서비스 디스커버리
    - 서비스끼리 찾아주는 기능, 이름을 정하고, 이름으로 매칭 등
- 리눅스 커널의 여러 기술 활용
- 하드웨어 가상화 기술보다 가볍다.
- 이미지 단위로 프로세스 실행 환경 구성
  - 컨테이너 실행을 위한 압축 파일
  - Dockerfile 스크립트를 활용해 생성
- 확장성, 이식성이 좋다.
- 표준성이 좋다. 버전, 배포 방식 등을 고려하지 않아도 된다.
- 설정 관리 -> 환경 변수 활용
- 자원 관리 

## 도커 설치
- Docker for Desktop
- 도커는 기본적으로 리눅스 지원
  - 윈도우에 설치된 도커는 가상머신에 설치된다.
    - MacOS 
      - xhyve 사용
    - Window
      - Hyper-V 사용

- 설치 확인
  - docker version 
    - 클라이언트, 서버 버전이 둘 다 나온다.
      - 다운받은 개발자가 클라이언트, localhost에 도커가 떠 있다. 
        - 클라이언트가 전달한 명령어를 서버에 전달한다.

## 도커 기본 명령어
- run
   - 생성 후 시작, 존재하면 바로 시작
   - docker run [OPTION] IMAGE[:TAG|@DIGEST] [COMMAND] [ARG...]
     - -d: 백그라운드 모드
     - -p: 호스트와 컨테이너의 포트 연결
     - -v: 호스트와 컨테이너의 디렉토리 연결
     - -e: 컨테이너 내 사용할 환경변수 설정
     - --name: 컨테이너 이름 설정
     - --rm: 프로세스 종료시 컨테이너 자동 제거
     - -it: -i와 -t 동시 사용, 터미널 입력 옵션
     - --network: 네트워크 연결
   - 예시
     - 우분투 설치
       - docker run ubuntu:20.04
         - 실행되자마자, 종료된다.
       - docker run --rm -it ubuntu:20.04 /bin/sh
         - 우분투 시작 후, 우분투 터미널이 실행된다.
     - 하시코프 설치 (웹 서버)
       - docker run --rm -p 5678:5678 hashicorp/http-echo -text="hello world"
         - 백그라운드 실행, 호스트와 컨테이너의 포트 연결
         - port 앞은 클라이언트 포트, 뒤는 컨테이너 포트
     - redis 설치
       - docker run --rm -p 1234:6379 redis
       - telnet localhost 1234
         - 설치한 레디스 접속
     - MySQL 설치, 실행
       - docker run -d -p 3306:3306 \ -e MYSQL_ALLOW_EMPTY_PASSWORD=true \ --name mysql \ mysql:5.7
         - mysql 설치
         - 비밀번호 입력 안함 
         - 컨테이너 이름을 mysql로 지정
       - docker exec -it mysql mysql
         - mysql 이름으로 접속, mysql 명령어 실행
         - exec는 실행중인 도커 컨테이너 접속시, 컨테이너 안에 ssh server 등을 설치하지 않고 접속한다.
       - create database wp CHARACTER SET utf8;
       - grant all privileges on wp.* wp@'%' identified by 'wp';
       - flush privileges;
       - quit
     - 워드프레스 블로그 실행하기
       - docker run -d -p 8080:80 \ -e WORDPRESS_DB_HOST=host.docker.internal \ -e WORDPRESS_DB_NAME=wp \ -e WORDPRESS_DB_USER=wp \ -e WORDPRESS_DB_PASSWORD=wp \ wordpress
         - 앞서 만든 mysql 정보를 같이 입력해준다.
         - 환경설정을 통해 서비스끼리 연결
         - WORDPRESS_DB_HOST=host.docker.internal -> host.docker.internal라는 가상의 도메인 사용, 네트워크 생성(network-create)해서 연결하면 편하다. 
- ps
  - 예시
    - docker ps
      - 실행 중인 컨테이너 목록 체크
    - docker ps -a
      - 중지된 컨테이너도 포함해서 목록 체크
- stop
  - 예시
    - docker stop [OPTION] CONTAINER [CONTAINER...]
      - 실행 중 컨테이너 중지 
      - 띄어쓰기 기준으로 여러 개 중지 가능
- rm
  - 종료된 컨테이너 완전히 제거
  - 예시
    - docker rm [OPTION] CONTAINER [CONTAINER...]
- logs
  - docker logs [OPTIONS] CONTAINER
  - 많이 쓰인다.
  - 컨테이너 정상 동작 확인하기 좋은 방법 -> 로그 확인
  - 기본 옵션, -f, -tail 옵션
  - 예시
    - docker logs -f CONTAINER
      - -f를 통해 실시간 로그 체크 가능
- images
  - docker images [OPTIONS] [REPOSITORY[:TAG]]
  - 도커가 다운로드한 이미지 목록 표시
    - 이미지를 다운 받아서 컨테이너를 만든다.
- pull
  - 이미지 다운로드 명령어
  - docker pull [OPTIONS] NAME[:TAG|@DIGEST]
  - 예시
    - docker pull ubuntu:18.04
      - run 할 때 자동으로 다운 받지만, pull을 통해 수동으로 다운 받을 수 있다.
- rmi
  - 이미지 삭제 방법
  - 컨테이너 실행중 이미지는 삭제 불가
  - docker rmi [OPTIONS] IMAGE [IMAGE..]
- network create
  - 도커 컨테이너끼리 이름으로 통신할 수 있는 가상 네트워크 생성
  - docker network create [OPTIONS] NETWORK
  - 예시
    - docker network create app-network
      - app-network라는 이름으로 위에 생성한 이미지 두 개(wordpress, mysql)가 통신할 네트워크 생성
      - 네트워크 이름이 있으면 연결하기 편하다.
    - network connect app-network mysql
    - docker run -d -p 8080:80 \ -e --network=app-network WORDPRESS_DB_HOST=mysql \ -e WORDPRESS_DB_NAME=wp \ -e WORDPRESS_DB_USER=wp \ -e WORDPRESS_DB_PASSWORD=wp \ wordpress
      - network 옵션 추가하고, WORDPRESS_DB_HOST=host.docker.internal를 변경
      - 워드프레스를 app-network에 속하게 하고, mysql 이름으로 접근
- volume mount (-v)
  - mysql 삭제 후 네트워크 연결한 워드프레스를 재접속시 에러 발생 -> 가상 환경에 생성된 컨테이너와 데이터가 삭제 (영구 보장 X)
    - docker stop mysql
    - docker rm mysql
    - docker run -d -p 3306:3306 \ -e MYSQL_ALLOW_EMPTY_PASSWORD=true \ --network=app-network \ --name mysql \ mysql:5.7
  - 예시
    - docker run -d -p 3306:3306 \ -e MYSQL_ALLOW_EMPTY_PASSWORD=true \ --network=app-network \ --name mysql \ -v /Users/csh/workspace/docker-volume/mysql:/var/lib/mysql \ mysql:5.7
      - -v /my/own/datadir:/var/lib/mysql
        - /my/own/datadir 이 부분이 클라이언트 로컬 디렉토리고, 이 디렉토리를 컨테이너에 /var/lib/mysql와 연결하는 것 

## docker compose
- 도커 클라이언트 프로그램을 통해 명령어를 입력했는데, 명령어들은 조심스럽다.
  - 더 편하게 해준다. docker for mac 다운시 같이 들어있다.
    - 리눅스에서 도커 설치시 별도로 다운 받아야 한다.
  - 예시
    - docker-compose version

    ```yaml
    version: '2'
    services: 
      db:
        image: mysql:latest
        volumes:
          - ./mysql:/var/lib/mysql
        restart: always
        environment:
          MYSQL_ROOT_PASSWORD: wp
          MYSQL_DATABASE: wp
          MYSQL_USER: wp
          MYSQL_PASSWORD: wp
      wordpress:
        image: wordpress:latest
        volumes:
          - ./wp:/var/www/html
        ports:
          - "8000:80"
        restart: always
        environment:
          WORDPRESS_DB_HOST: db:3306
          WORDPRESS_DB_PASSWORD: wp
      ```  
    - docker-compose up
      - 자동으로 docker-compose.yml 파일을 읽어서 실행하게 된다.















