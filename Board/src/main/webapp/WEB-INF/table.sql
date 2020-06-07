
--코드그룹 
create table board_code_group(
code_group_id varchar2(20) primary key,
code_group_name varchar2(20));

--상세코드
create table board_code_detail(
code_group_id varchar2(20) references board_code_group(code_group_id),
detail_code varchar2(5) primary key,
code_name varchar2(20),
eng_code_name varchar2(20));


--유저 테이블
create table board_user(
user_id varchar2(20) primary key,
user_pwd varchar2(100) not null,
user_email varchar2(30),
user_name varchar2(20),
user_state_code varchar2(5) default 'S001' references board_code_detail(detail_code),
user_authority_code varchar2(5) default 'U001' references board_code_detail(detail_code),
user_regdate date,
login_count number default 0);

--게시판 테이블
create table board(
board_num number primary key,
board_title varchar2(500) not null,
board_writer varchar2(20) references board_user(user_id),
board_content clob,
board_view_count number,
board_regdate date,
board_type_code varchar2(5) references board_code_detail(detail_code),
baord_ori_num number,
reply_order_num number,
reply_depth_num number
);

--파일 테이블
create table board_file(
file_num number primary key,
board_num number references board(board_num),
file_ori_title varchar2(50),
file_save_title varchar2(50),
file_size long,
file_regdate date
);

--코드그룹 아이디 테이블 인서트
insert into board_code_group values('user_grade', '회원권한코드');
insert into board_code_group values('user_state', '회원상태코드');
insert into board_code_group values('board_type', '게시판코드');

--세부코드 테이블 인서트
insert into board_code_detail values('user_grade', 'U001', '일반','userGradeNormal');
insert into board_code_detail values('user_grade', 'U002', '관리자','userGradeAdmin');
insert into board_code_detail values('user_state', 'S001', '정상','userStateNormal');
insert into board_code_detail values('user_state', 'S002', '정지','userStateStop');
insert into board_code_detail values('user_state', 'S003', '탈퇴','userStateDrop');
insert into board_code_detail values('board_type', 'B001', '자유게시판','boardTypeFree');
insert into board_code_detail values('board_type', 'B002', '공지게시판','boardTypeNotice');
insert into board_code_detail values('board_type', 'B003', '질문게시판','boardTypeQna');


