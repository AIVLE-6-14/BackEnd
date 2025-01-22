

# AISafety API

이 프로젝트는 **AI 기반 동물 감지 시스템**과 관련된 다양한 기능을 제공합니다. 사용자는 동물 감지 정보를 등록하고, 도로 교통 공사 및 야생 동물 보호 기관에 관련 알림을 제공하는 등의 작업을 할 수 있습니다. 이 API는 **Spring Boot**로 구축되었으며, Swagger를 통해 API 문서를 자동으로 제공합니다.

## 목차

- [API 소개](#api-소개)
- [기능](#기능)
  - [Animal API](#animal-api)
  - [Dashboard API](#dashboard-api)
  - [Map API](#map-api)
  - [Organization API](#organization-api)
  - [Post API](#post-api)
  - [User API](#user-api)
- [기타](#기타)

## API 소개

이 프로젝트는 동물 감지와 관련된 알림 시스템을 포함하며, **도로교통 공사**, **야생 동물 보호 기관** 등의 역할을 맡은 사용자들이 알림을 조회하고 처리할 수 있도록 도와줍니다. 또한, **대시보드**를 통해 다양한 통계 및 정보를 시각적으로 제공하며, 사용자 인증 및 게시물 관리 기능도 포함되어 있습니다.

## 기능

### Animal API
동물 감지 및 알림을 관리하는 API입니다.

#### 1. 동물 감지 등록
- **엔드포인트**: `/api/animals/fetch`
- **HTTP 메서드**: `POST`
- **설명**: 동물 감지 정보를 받아 저장합니다. 파이썬 서버에서 전송된 데이터를 처리하는 전용 API입니다.
- **인증 필요 없음**

#### 2. 모든 동물 감지 알림 조회
- **엔드포인트**: `/api/animals`
- **HTTP 메서드**: `GET`
- **설명**: 도로교통공사 관련 역할을 가진 사용자만 조회 가능. PENDING 상태가 아닌 모든 동물 감지 알림을 조회합니다.
- **권한**: `ROLE_ROAD_USER`

#### 3. 기관별 동물 감지 알림 조회
- **엔드포인트**: `/api/animals/{organizationId}`
- **HTTP 메서드**: `GET`
- **설명**: 야생 동물 보호 기관 관련 알림을 조회합니다. 해당 기관 ID가 필요합니다.
- **권한**: `ROLE_SAFETY_USER`

#### 4. 동물 감지 알림 삭제
- **엔드포인트**: `/api/animals/{id}`
- **HTTP 메서드**: `DELETE`
- **설명**: 동물 감지 알림을 삭제합니다.
- **권한**: `ROLE_ROAD_USER`

#### 5. 도로교통 공사의 자체 처리 요청
- **엔드포인트**: `/api/animals/{id}/self-handle`
- **HTTP 메서드**: `POST`
- **설명**: 도로교통 공사에서 동물 감지에 대해 자체 처리 요청을 할 수 있습니다.
- **권한**: `ROLE_ROAD_USER`

#### 6. 야생동물 보호기관 처리 요청
- **엔드포인트**: `/api/animals/{animalId}/other-handle`
- **HTTP 메서드**: `POST`
- **설명**: 야생 동물 보호 기관이 동물 감지에 대한 처리 요청을 할 수 있습니다.
- **권한**: `ROLE_ROAD_USER`

### Dashboard API
대시보드를 위한 데이터 제공 API입니다.

#### 1. Bar Chart 데이터
- **엔드포인트**: `/api/dashBoard/bar`
- **HTTP 메서드**: `GET`
- **설명**: Bar Chart에 필요한 데이터를 반환합니다.
- **권한**: `ROLE_ROAD_USER`, `ROLE_ADMIN`

#### 2. Map 데이터
- **엔드포인트**: `/api/dashBoard/map-position`
- **HTTP 메서드**: `GET`
- **설명**: 동물 위치 데이터를 반환합니다.
- **권한**: `ROLE_ROAD_USER`, `ROLE_ADMIN`

#### 3. Line Chart 데이터
- **엔드포인트**: `/api/dashBoard/line`
- **HTTP 메서드**: `GET`
- **설명**: Line Chart에 필요한 데이터를 반환합니다.
- **권한**: `ROLE_ROAD_USER`, `ROLE_ADMIN`

### Map API
지도 관련 데이터를 제공합니다.

#### 1. CCTV 위치 조회
- **엔드포인트**: `/api/map/cctv`
- **HTTP 메서드**: `GET`
- **설명**: CCTV 위치 데이터를 반환합니다.
- **권한**: `ROLE_ROAD_USER`, `ROLE_SAFETY_USER`

### Organization API
기관 관련 API입니다.

#### 1. 기관 등록
- **엔드포인트**: `/api/organizations/save`
- **HTTP 메서드**: `POST`
- **설명**: 기관을 등록합니다.
- **인증 필요 없음**

#### 2. 모든 기관 이름 조회
- **엔드포인트**: `/api/organizations`
- **HTTP 메서드**: `GET`
- **설명**: 등록된 모든 기관들의 이름을 반환합니다.

#### 3. 특정 기관 이름 조회
- **엔드포인트**: `/api/organizations/{organizationId}`
- **HTTP 메서드**: `GET`
- **설명**: 특정 기관의 이름을 조회합니다.

### Post API
게시물 관련 API입니다.

#### 1. 게시물 생성
- **엔드포인트**: `/api/posts/create`
- **HTTP 메서드**: `POST`
- **설명**: 게시물을 생성합니다. 파일 첨부가 가능합니다.
- **권한**: 로그인한 사용자

#### 2. 모든 게시물 조회
- **엔드포인트**: `/api/posts/all`
- **HTTP 메서드**: `GET`
- **설명**: 모든 게시물을 조회합니다.
- **권한**: `ROLE_ROAD_USER`

#### 3. 특정 기관 게시물 조회
- **엔드포인트**: `/api/posts/organization`
- **HTTP 메서드**: `GET`
- **설명**: 해당 사용자가 속한 기관의 게시물을 조회합니다.
- **권한**: `ROLE_SAFETY_USER`

#### 4. 게시물 상세 조회
- **엔드포인트**: `/api/posts/detail/{id}`
- **HTTP 메서드**: `GET`
- **설명**: 특정 게시물의 상세 정보를 조회합니다.
- **권한**: `ROLE_ROAD_USER`, `ROLE_SAFETY_USER`

### User API
회원 관련 API입니다.

#### 1. 회원가입
- **엔드포인트**: `/api/users/signup`
- **HTTP 메서드**: `POST`
- **설명**: 회원가입을 처리합니다. 회원가입 전에 기관 데이터가 존재해야 합니다.

#### 2. 로그인
- **엔드포인트**: `/api/users/login`
- **HTTP 메서드**: `POST`
- **설명**: JWT 방식으로 로그인합니다.

#### 3. 로그아웃
- **엔드포인트**: `/api/users/logout`
- **HTTP 메서드**: `POST`
- **설명**: JWT 토큰을 삭제하여 로그아웃합니다.

#### 4. 이메일 중복 확인
- **엔드포인트**: `/api/users/duplicate`
- **HTTP 메서드**: `POST`
- **설명**: 이메일 중복 여부를 확인합니다.

## 기타

- 모든 API는 **JWT 인증**을 사용하며, 권한에 따라 특정 기능에 접근할 수 있습니다.
- Swagger UI를 통해 API 문서를 자동으로 제공하므로, API를 쉽게 테스트하고 사용법을 확인할 수 있습니다.


