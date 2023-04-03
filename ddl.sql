create table authority
(
    id                    bigint auto_increment
        primary key,
    type                  enum ('SUPER', 'ADMIN', 'MEMBER')    not null comment '권한',
    is_admin_authority    tinyint(1)                           not null comment '관리자 / 이용자 권한 여부',
    is_display            tinyint(1) default 1                 not null comment '이용자 display 여부',
    is_use_menu_authority tinyint(1) default 1                 not null comment '메뉴 권한 사용 여부 (true: 사용(특정 메뉴만 접근 가능), false: 미사용(모든 메뉴 접근 가능))',
    created_dt            timestamp  default CURRENT_TIMESTAMP not null comment '생성일',
    updated_dt            timestamp                            null comment '수정일'
)
    comment '권한';

create table authority_menu
(
    id           bigint auto_increment
        primary key,
    menu_id      bigint                              not null comment '메뉴 id',
    authority_id bigint                              not null comment '권한 id',
    is_use       tinyint(1)                          not null comment '메뉴 사용 여부(메뉴 미사용시 메뉴 포함 상세 api url 접근 불가능)',
    created_dt   timestamp default CURRENT_TIMESTAMP not null,
    updated_dt   timestamp                           null
)
    comment '권한 메뉴 매핑 테이블';

create table category
(
    id            bigint auto_increment
        primary key,
    type          enum ('EVENT')                      not null comment '카테고리 타입 (EVENT: 이벤트)',
    category_name varchar(100)                        not null comment '카테고리 명',
    created_dt    timestamp default CURRENT_TIMESTAMP not null,
    updated_dt    timestamp                           null
)
    comment '카테고리';

create table event
(
    id                        bigint auto_increment
        primary key,
    category_id               bigint                                                                                                                         not null,
    event_method              enum ('PERIOD', 'ALWAYS', 'RECOMMENDATION', 'ADVERTISING', 'FCFA', 'DESIGNATED_HOUR', 'DESIGNATED_DAY', 'DESIGNATED_DAY_HOUR') not null comment 'PERIOD: 기간 타입, ALWAYS: 무제한 타입, RECOMMENDATION: 추천 타입, ADVERTISING: 광고 타입, FCFA: 선착순 타입, DESIGNATED_HOUR: 지정된 시간, DESIGNATED_DAY: 지정된 요일, DESIGNATED_DAY_HOUR: 지정된 요일 + 지정된 시간',
    event_type                enum ('INVITE_FRIEND', 'RAFFLE', 'RANDOM', 'LOOK_BOOK', 'EXPERIENCE')                                                          not null comment 'INVITE_FRIEND: 친구 초대, RAFFLE: 래플, RANDOM: 랜덤, LOOK_BOOK: 룩북, EXPERIENCE: 체험단',
    title                     varchar(255)                                                                                                                   not null comment '이벤트 제목',
    content                   varchar(2000)                                                                                                                  null comment '이벤트 내용',
    event_start_dt            timestamp                                                                                                                      null comment '이벤트 시작일',
    event_end_dt              timestamp                                                                                                                      null comment '이벤트 종료일',
    event_status              enum ('ACTIVE', 'INACTIVE')                                                                                                    not null comment 'ACTIVE: 활성, INACTIVE: 비활성',
    entry_fee_type            enum ('FREE', 'PAID', 'CONDITION_FREE')                                                                                        not null comment 'FREE: 무료, PAID: 유료, CONDITION_FREE: 조건 무료',
    participation_limit_count int       default (0)                                                                                                          null,
    created_dt                timestamp default (now())                                                                                                      not null,
    updated_dt                timestamp                                                                                                                      null
)
    comment '이벤트';

create table event_benefit
(
    id               bigint                                             not null
        primary key,
    event_id         bigint                                             not null,
    condition_type   enum ('PARTICIPATION', 'SHARING', 'PAY')           null comment '혜택 조건 타입 (PARTICIPATION: 참여, SHARING: 공유, PAY: 일정 금액 이상)',
    condition_number int       default 0                                null comment '조건 타입에 해당하는 횟수 또는 금액 ',
    benefit_type     enum ('NORMAL', 'DISCOUNT', 'PRESENT', 'PAY_BACK') not null comment '이벤트 혜택 타입 (NORMAL: 일반, DISCOUNT: 할인, PRESENT: 선물, PAY_BACK: 페이백',
    benefit_number   int       default 0                                null comment '혜택에 해당하는 할인율 또는 금액 등',
    title            varchar(255)                                       not null comment '이벤트 제목',
    content          varchar(2000)                                      null comment '이벤트 내용',
    created_dt       timestamp default (now())                          not null,
    updated_dt       timestamp                                          null
)
    comment '이벤트 혜택';

create table event_participant
(
    id        bigint not null
        primary key,
    member_id bigint not null,
    event_id  bigint not null
)
    comment '이벤트 참석자 매핑 테이블';

create table event_specific_point_in_time
(
    event_id               bigint                                                                              not null
        primary key,
    event_day_of_week_type enum ('SUNDAY', 'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY') not null,
    event_hour             int                                                                                 null,
    event_minute           int                                                                                 null,
    locale                 varchar(20) default 'KO'                                                            null,
    created_dt             timestamp   default (now())                                                         not null,
    updated_dt             timestamp                                                                           null
)
    comment '이벤트 활성 구체 시간';

create table member
(
    id               bigint auto_increment
        primary key,
    login_id         varchar(100)                                                      not null comment '로그인 아이디',
    password         varchar(255)                                                      not null comment '비밀번호',
    status           enum ('NORMAL', 'LEAVE', 'BAN', 'STOP') default 'NORMAL'          null comment 'NORMAL: 정상, LEAVE: 회원 탈퇴, BAN: 회원 영구 정지, STOP: 일시 정지',
    name             varchar(255)                                                      null comment '이름 (개인 정보 암호화 필요)',
    email            varchar(255)                                                      null comment '이메일 (개인 정보 암호화 필요)',
    nick_name        varchar(100)                                                      null comment '닉네임',
    phone_number     varchar(255)                                                      null comment '핸드폰 번호 (개인 정보 암호화 필요)',
    profile_file_url varchar(500)                                                      null comment '프로필 사진 경로',
    login_type       enum ('DEFAULT', 'SITE', 'KAKAO', 'APPLE', 'NAVER', 'FACEBOOK')   not null comment '로그인 타입',
    created_dt       timestamp                               default CURRENT_TIMESTAMP not null comment '생성일',
    updated_dt       timestamp                                                         null comment '수정일'
)
    comment '회원';

create table member_authority
(
    member_id    bigint                              not null
        primary key,
    authority_id bigint                              not null,
    created_dt   timestamp default CURRENT_TIMESTAMP not null comment '생성일',
    updated_dt   timestamp                           null comment '수정일'
)
    comment '회원 - 권한 매핑';

create table member_certification
(
    member_id  bigint                                   not null
        primary key,
    type       enum ('REFRESH_TOKEN', 'EMAIL', 'PHONE') not null comment '인증 타입',
    value      varchar(255)                             not null comment '인증 값',
    valid_time timestamp                                null comment '인증 유효 시간',
    created_dt timestamp default CURRENT_TIMESTAMP      not null comment '생성일',
    updated_dt timestamp                                null comment '수정일'
)
    comment '회원 인증';

create table member_detail
(
    id                    bigint auto_increment
        primary key,
    member_id             bigint                               not null,
    birthday              varchar(10)                          null comment '생년 월일 (ex: 1991-04-29)',
    gender                enum ('SECRET', 'MALE', 'FEMALE')    not null comment '성별',
    height                int                                  null comment '키',
    weight                int                                  null comment '몸무게',
    is_accept_marketing   tinyint(1) default 0                 not null comment '마케팅 수신 동의 여부',
    is_authenticate_email tinyint(1) default 0                 not null comment '이메일 인증 여부',
    is_authenticate_phone tinyint(1) default 0                 not null comment '핸드폰 인증 여부',
    self_introduce        varchar(2000)                        null comment '자기소개',
    recommender_member_id bigint                               null comment '추천인 회원 ID',
    created_dt            timestamp  default CURRENT_TIMESTAMP not null comment '생성일',
    updated_dt            timestamp                            null comment '수정일'
)
    comment '회원 상세';

create table menu
(
    id            bigint auto_increment
        primary key,
    type          enum ('SERVICE', 'ADMIN')            not null comment '메뉴 타입 (SERVICE: 서비스 페이지, ADMIN: 관리자 페이지)',
    parent_id     bigint                               null comment '부모 메뉴 id',
    depth         int                                  not null comment '메뉴 계층 깊이',
    display_order int                                  not null comment '계층별 순서',
    name          varchar(100)                         null comment '메뉴명',
    api_url       varchar(255)                         null comment '메뉴 페이지 api url',
    is_display    tinyint(1) default 1                 not null comment '메뉴 표시 여부',
    created_dt    timestamp  default CURRENT_TIMESTAMP not null,
    updated_dt    timestamp                            null
)
    comment '메뉴';

create table menu_detail_authority
(
    id                bigint auto_increment
        primary key,
    authority_menu_id bigint                                       not null comment '권한 메뉴 매핑 id',
    type              enum ('WRITE', 'READ', 'UPLOAD', 'DOWNLOAD') not null comment '상세 권한 타입 (WRITE: 쓰기, READ: 읽기, UPLOAD: 업로드, DOWNLOAD: 다운로드',
    created_dt        timestamp default CURRENT_TIMESTAMP          not null,
    updated_dt        timestamp                                    null
)
    comment '유저 상세 권한';

create table menu_include_detail_api_url
(
    id         bigint auto_increment
        primary key,
    menu_id    bigint                              not null comment '메뉴 id',
    api_url    varchar(255)                        null comment '메뉴 페이지 관련 상세 api url',
    created_dt timestamp default CURRENT_TIMESTAMP not null,
    updated_dt timestamp                           null
)
    comment '메뉴 포함 상세 API';

create table role
(
    authority_id bigint                              not null
        primary key,
    role         enum ('MD')                         not null comment '역할 타입',
    created_dt   timestamp default CURRENT_TIMESTAMP not null comment '생성일',
    updated_dt   timestamp                           null comment '수정일'
)
    comment '역할';

create table stock
(
    id         bigint auto_increment
        primary key,
    product_id bigint null,
    quantity   bigint null,
    version    bigint null
);