# BackEnd 맴버 : 박연규, 김영인

# AISafety API

이 프로젝트는 **AI 기반 동물 감지 시스템**과 관련된 다양한 기능을 제공합니다. 사용자는 동물 감지 정보를 등록하고, 도로 교통 공사 및 야생 동물 보호 기관에 관련 알림을 제공하는 등의 작업을 할 수 있습니다. 이 API는 **Spring Boot**로 구축되었으며, Swagger를 통해 API 문서를 자동으로 제공합니다.


## 목차

- [인증](#인증)
- [동물 API](#동물-api)
  - [동물 감지 등록](#동물-감지-등록)
  - [동물 알림 조회](#동물-알림-조회)
  - [동물 처리](#동물-처리)
- [대시보드 API](#대시보드-api)
- [맵 API](#맵-api)
- [기관 API](#기관-api)
- [게시물 API](#게시물-api)
- [예측 API](#예측-api)
- [유저 API](#유저-api)

---

## 인증

모든 요청은 인증이 필요하며, `POST /api/users/signup`과 `POST /api/users/login`을 제외한 모든 요청은 JWT 토큰을 이용한 인증이 필요합니다.

- **로그인**: `POST /api/users/login`
- **로그아웃**: `POST /api/users/logout`

---

## 동물 API

### 동물 감지 등록

- **POST /api/animals/fetch**  
  - **설명**: 동물 감지 데이터를 등록합니다.
  - **요청 본문**: `AnimalDTO` 형식의 동물 감지 데이터 목록.
  - **인증 필요 없음**.

### 동물 알림 조회

- **GET /api/animals**  
  - **설명**: 모든 동물 알림을 조회합니다.
  - **필요한 권한**: `ROLE_ROAD_USER`
  
- **GET /api/animals/{organizationId}**  
  - **설명**: 특정 기관 ID에 해당하는 동물 알림을 조회합니다.
  - **Path 변수**: `organizationId` (기관 ID).
  - **필요한 권한**: `ROLE_SAFETY_USER`

### 동물 처리

- **DELETE /api/animals/{id}**  
  - **설명**: 특정 동물 감지 알림을 삭제합니다.
  - **Path 변수**: `id` (동물 감지 알림 ID).
  - **필요한 권한**: `ROLE_ROAD_USER`

- **POST /api/animals/{id}/self-handle**  
  - **설명**: 도로 사용자에 의한 동물 감지 알림 자체 처리 요청을 수행합니다.
  - **Path 변수**: `id` (동물 감지 알림 ID).
  - **필요한 권한**: `ROLE_ROAD_USER`

- **POST /api/animals/{animalId}/other-handle**  
  - **설명**: 야생동물 보호 기관에 의한 동물 감지 알림 처리 요청을 수행합니다.
  - **Path 변수**: `animalId` (동물 감지 ID).
  - **요청 본문**: `OtherHandleDTO` 형식의 처리 요청 데이터.
  - **필요한 권한**: `ROLE_ROAD_USER`

---

## 대시보드 API

### 바 차트 데이터

- **GET /api/dashBoard/bar**  
  - **설명**: 바 차트에 필요한 데이터를 반환합니다.
  - **필요한 권한**: `ROLE_ROAD_USER` 또는 `ROLE_ADMIN`

### 지도 위치 데이터

- **GET /api/dashBoard/map-position**  
  - **설명**: 동물 위치 데이터를 반환합니다.
  - **필요한 권한**: `ROLE_ROAD_USER` 또는 `ROLE_ADMIN`

### 라인 차트 데이터

- **GET /api/dashBoard/line**  
  - **설명**: 라인 차트에 필요한 데이터를 반환합니다.
  - **필요한 권한**: `ROLE_ROAD_USER` 또는 `ROLE_ADMIN`

---

## 맵 API

### CCTV 데이터 조회

- **GET /api/map/cctv**  
  - **설명**: CCTV 위치, 이름, 영상 정보를 조회합니다.
  - **필요한 권한**: `ROLE_ROAD_USER` 또는 `ROLE_SAFETY_USER`

---

## 기관 API

### 기관 등록

- **POST /api/organizations/save**  
  - **설명**: 기관을 리스트 형태로 등록합니다.
  - **요청 본문**: `OrganizationDTO` 형식의 기관 목록.

### 기관 이름 조회

- **GET /api/organizations**  
  - **설명**: 등록된 모든 기관의 이름을 조회합니다.
  
- **GET /api/organizations/{organizationId}**  
  - **설명**: 특정 기관 ID에 해당하는 기관 이름을 조회합니다.
  - **Path 변수**: `organizationId` (기관 ID).

---

## 게시물 API

### 게시물 생성

- **POST /api/posts/create**  
  - **설명**: JWT 토큰에서 유저 정보를 가져와 게시물을 생성합니다.
  - **요청 본문**: `PostRequestDTO` 형식의 게시물 데이터.
  - **파일 업로드 가능**.

### 모든 게시물 조회

- **GET /api/posts/all**  
  - **설명**: 모든 게시물을 조회합니다.
  - **필요한 권한**: `ROLE_ROAD_USER`

### 특정 기관의 게시물 조회

- **GET /api/posts/organization**  
  - **설명**: 현재 로그인한 유저의 기관에 해당하는 게시물을 조회합니다.
  - **필요한 권한**: `ROLE_SAFETY_USER`

### 게시물 상세 조회

- **GET /api/posts/detail/{id}**  
  - **설명**: 특정 게시물에 대한 상세 정보를 조회합니다.
  - **Path 변수**: `id` (게시물 ID).
  - **필요한 권한**: `ROLE_ROAD_USER` 또는 `ROLE_SAFETY_USER`

---

## 예측 API

### 예측 결과 저장

- **POST /predict**  
  - **설명**: 예측 모델 결과값을 테이블에 저장합니다.
  - **요청 본문**: `PredictRiskRequestDTO` 형식의 예측 요청 데이터.
  - **필요한 권한**: `ROLE_ROAD_USER`

### 예측 결과 조회

- **GET /predict**  
  - **설명**: 예측 모델이 판단한 도로의 위험도 리스트를 조회합니다.
  - **필요한 권한**: `ROLE_ROAD_USER`

---

## 유저 API

### 회원가입

- **POST /api/users/signup**  
  - **설명**: 새로운 유저를 회원가입합니다.
  - **요청 본문**: `UserSignupDTO` 형식의 회원가입 데이터.

### 로그인

- **POST /api/users/login**  
  - **설명**: JWT 토큰으로 로그인을 처리합니다.
  - **요청 본문**: `UserLoginDTO` 형식의 로그인 데이터.

### 로그아웃

- **POST /api/users/logout**  
  - **설명**: JWT 토큰을 삭제하여 로그아웃을 처리합니다.

### 이메일 중복 확인

- **POST /api/users/duplicate**  
  - **설명**: 이메일 중복 여부를 확인합니다.
  - **요청 본문**: `UserEmailDupDTO` 형식의 이메일 데이터.
  
---

이 문서는 AISafety 프로젝트의 각 API에 대한 기본적인 설명을 제공합니다. 각 API는 다양한 기능을 제공하며, 일부 기능은 권한이 필요한 요청이므로 적절한 권한을 가진 사용자만 접근할 수 있습니다.
