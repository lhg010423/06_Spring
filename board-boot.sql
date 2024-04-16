/* 계정 생성 (관리자 계정으로 접속) */
ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;

CREATE USER spring_lhg IDENTIFIED BY spring1234;

GRANT CONNECT, RESOURCE TO spring_lhg;

ALTER USER spring_lhg DEFAULT TABLESPACE SYSTEM QUOTA UNLIMITED ON SYSTEM;

--> 계정 생성 후 접속 방법(새 데이터베이스) 추가


-----------------------------------------------------------------------


/* SPRING 계정 접속 */

-- "" : 내부에 작성된 글(모양) 그대로 인식 -> 대/소문자 구분 
--> "" 작성 권장


-- CHAR(10)      : 고정 길이 문자열 10바이트 (최대 2000 바이트)
-- VARCHAR2(10)  : 가변 길이 문자열 10바이트 (최대 4000 바이트)

-- NVARCHAR2(10) : 가변 길이 문자열 10글자 (유니코드, 최대 4000 바이트)
-- CLOB : 가변 길이 문자열 (최대 4GB)


/* MEMBER 테이블 생성 */
CREATE TABLE "MEMBER"(
	"MEMBER_NO"       NUMBER CONSTRAINT "MEMBER_PK" PRIMARY KEY,
	"MEMBER_EMAIL" 		NVARCHAR2(50)  NOT NULL,
	"MEMBER_PW"				NVARCHAR2(100) NOT NULL,
	"MEMBER_NICKNAME" NVARCHAR2(10)  NOT NULL,
	"MEMBER_TEL"			CHAR(11)       NOT NULL,
	"MEMBER_ADDRESS"	NVARCHAR2(150),
	"PROFILE_IMG"			VARCHAR2(300),
	"ENROLL_DATE"			DATE           DEFAULT SYSDATE NOT NULL,
	"MEMBER_DEL_FL"		CHAR(1) 			 DEFAULT 'N'
									  CHECK("MEMBER_DEL_FL" IN ('Y', 'N') ),
	"AUTHORITY"				NUMBER 			   DEFAULT 1
										CHECK("AUTHORITY" IN (1, 2) )
);


-- 회원 번호 시퀀스 만들기
CREATE SEQUENCE SEQ_MEMBER_NO NOCACHE;


-- 샘플 회원 데이터 삽입
INSERT INTO "MEMBER"
VALUES(SEQ_MEMBER_NO.NEXTVAL, 
			 'user01@kh.or.kr',
			 'pass01!',
			 '유저일',
			 '01012341234',
			 NULL,
			 NULL,
			 DEFAULT,
			 DEFAULT,
			 DEFAULT
);

COMMIT;


SELECT * FROM "MEMBER";





UPDATE "MEMBER" SET
MEMBER_PW = '$2a$10$ctbQ1uuhNDQfxgPJvfS4AOmlq/HcSCi/Ok1ukMryR3SRx9O5tyQKm'
WHERE MEMBER_NO = 1;

COMMIT;



-- 로그인 SQL
SELECT MEMBER_NO, MEMBER_EMAIL, MEMBER_NICKNAME, MEMBER_PW,
MEMBER_TEL, MEMBER_ADDRESS, PROFILE_IMG, AUTHORITY,
TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일" HH24"시" MI"분" SS"초"') ENROLL_DATE
FROM "MEMBER"
WHERE MEMBER_EMAIL = 'user01@kh.or.kr'
AND MEMBER_DEL_FL = 'N';


-- 이메일 중복검사
SELECT COUNT(*)
FROM "MEMBER"
WHERE MEMBER_DEL_FL = 'N'
AND MEMBER_EMAIL = 'user01@kh.or.kr';

/* 이메일, 인증키 저장 테이블 생성 */
CREATE TABLE "TB_AUTH_KEY"(
	"KEY_NO"    NUMBER PRIMARY KEY,
	"EMAIL"	    NVARCHAR2(50) NOT NULL,
	"AUTH_KEY"  CHAR(6)	NOT NULL,
	"CREATE_TIME" DATE DEFAULT SYSDATE NOT NULL
);
COMMENT ON COLUMN "TB_AUTH_KEY"."KEY_NO"      IS '인증키 구분 번호(시퀀스)';
COMMENT ON COLUMN "TB_AUTH_KEY"."EMAIL"       IS '인증 이메일';
COMMENT ON COLUMN "TB_AUTH_KEY"."AUTH_KEY"    IS '인증 번호';
COMMENT ON COLUMN "TB_AUTH_KEY"."CREATE_TIME" IS '인증 번호 생성 시간';
CREATE SEQUENCE SEQ_KEY_NO NOCACHE; -- 인증키 구분 번호 시퀀스
SELECT * FROM "TB_AUTH_KEY";


---------------------------------------------------------

-- 파일 업로드 테스트용 테이블
CREATE TABLE "UPLOAD_FILE"(
	FILE_NO NUMBER PRIMARY KEY,
	FILE_PATH VARCHAR2(500) NOT NULL,
	FILE_ORIGINAL_NAME VARCHAR2(300) NOT NULL,
	FILE_RENAME VARCHAR2(100) NOT NULL,
	FILE_UPLOAD_DATE DATE DEFAULT SYSDATE,
	MEMBER_NO NUMBER REFERENCES "MEMBER"
);
COMMENT ON COLUMN "UPLOAD_FILE".FILE_NO    IS  '파일 번호(PK)';
COMMENT ON COLUMN "UPLOAD_FILE".FILE_PATH  IS  '클라이언트 요청 경로';
COMMENT ON COLUMN "UPLOAD_FILE".FILE_ORIGINAL_NAME IS  '파일 원본명';
COMMENT ON COLUMN "UPLOAD_FILE".FILE_RENAME IS  '변경된 파일';
COMMENT ON COLUMN "UPLOAD_FILE".FILE_UPLOAD_DATE IS  '업로드 날짜';
COMMENT ON COLUMN "UPLOAD_FILE".MEMBER_NO IS  'MEMBER 테이블의 PK(MEMBER_NO) 참조';
CREATE SEQUENCE SEQ_FILE_NO NOCACHE;
SELECT * FROM "UPLOAD_FILE";























