# 쇼핑
![image](https://github.com/user-attachments/assets/78e43257-de1e-4ac5-bfb3-bb58455b6da0)
---

## 1. 셀레니움이란
- Selenium은 웹사이트 테스트를 위한 도구로 브라우저 동작을 자동화할 수 있습니다. 셀레니움을 이용하는 웹크롤링 방식은 바로 이점을 적극적으로 활용하는 것입니다. 프로그래밍으로 브라우저 동작을 제어해서 마치 사람이 이용하는 것 같이 웹페이지를 요청하고 응답을 받아올 수 있습니다.
- 현재 많은 웹페이지들은 React,Vue 등 여러 라이브러리,프레임 워크를 사용하여 웹페이지를 동적으로 구성하기 떄문에 셀레니움을 사용한 크롤링이 효율적이라고 판단하여 사용하였습니다.

## 2. 셀레니움 사용법
- 셀레니움은 전 버전 같은 경우 Chrome기반 웹드라이버가 필요하지만, 최신 버전부터 셀레니움 라이브러리에 종속된 웹 드라이버 매니저 라이브러리를 사용하여 별도의 웹드라이버 지정이 필요없음

## 3. 문제 해결
- 처음 크롤링을 진행 하였을 경우, 최상단 이미지 3개를 제외한 이미지가 모두 빈 이미지 코드로 반환되는 문제가 있었음.
- 해당 문제를 검색하다 보니 웹페이지의 반응성 및 속도를 향상 시키기 위해 lazy loading 기법이 사용된 것을 알 수 있었음.
  - lazy loading이란?
    - 아직 화면에 보여지지 않은 이미지들은 로딩 시점을 뒤로 미루어 웹 성능을 최적화하는 기법이다. 사용자가 화면에서 보이지 않는 이미지는 사용자가 페이지의 해당 부분으로 스크롤 할 때까지 로드되지 않는 것을 의미합니다. 이 기술은 초기 로딩 시간을 크게 줄일 수 있으며, 더 빠른 페이지 속도와 더 나은 사용자 경험을 제공할 수 있습니다.
- 해당 뷰포트에 표현되지 않을 경우, 이미지가 로딩 되지않아 해당 문제를 셀레니움 javascript action을 통해 해결함
  - javascript action으로 스크롤을 약간의 시간을 두고 스크롤을 페이지의 끝까지 내리는 방식을 채택하여 해결함.
  - 결국 크롤링이 성공적으로 이루어짐.
