# 간단한 뉴스 어플

## 동기
![헤드라잇_축소](https://github.com/user-attachments/assets/96f66a1c-7f5b-4b83-a47d-4d148ae82142)
<p>이전에 헤드라잇이라는 앱은 모든 신문사의 뉴스를 카테고리 별로 확인할 수 있어서 굉장히 편리했었다.<br>
이제는 수익성 부족으로 서비스하지 않지만 그때의 사용경험이 강렬했기에 비슷하게 만들려고 시도했다.</p>

## 개발기간
2024/08/29 ~ 2024/09/10

## 개발환경
- **os**: ![Windows](https://img.shields.io/badge/Windows%2011-0078D6?style=for-the-badge&logo=windows&logoColor=white)
- **Language**: ![코틀린_축소2](https://github.com/user-attachments/assets/b4724c9c-eccb-4d13-a3d6-d19513476f2d)
- **Tool**: ![안드로이드_축소2](https://github.com/user-attachments/assets/e12ebcc3-0187-43e3-a792-02d0d4ffc7ca)
- **API**: ![뉴스_API_축소](https://github.com/user-attachments/assets/189dd78d-d04d-4843-aafc-0aa1fed89c24)

## 구현내용
- newsApi를 통해 뉴스들의 URI와 카테고리를 받아옴.
- URI의 정보를 크롤링하여 제목, 이미지(없는 경우 기본이미지), 날짜와 같은 정보를 리스트 형태로 출력.
- 카테고리 변경, 새로고침, 더보기 기능.
- 요약보기 및 상세보기.

## 후기
- 코틀린 공부랑 병행하면서 만들어봤던건데 헤드라잇에서 겪었던 경험을 나름대로 녹여낼 수 있어서 뿌듯했고, 당시에는 아무것도 모르고 앱을 사용했는데 헤드라잇 앱이 얼마나 사용성이 좋은 앱이었는지 알았다...
- 개발 막바지에 테스트를 하려고 여러번 시도하다가, 그 날은 아예 목록들이 출력이 안된적이 있었다. 이유는 NewsApi가 무료 사용자에게 제공하는게 하루 50회였기 때문이다.
- API 문서를 제대로 안찾아본 내 잘못이지만...
- 그래도 덕분에, 예외처리도 해보고 다양한 시도들 끝에 오류도 알아내서 배우는데 더욱 도움이 됐다.
![image](https://github.com/user-attachments/assets/d140cef8-3c3b-4a03-8117-2611140906dd)
- JAVA랑 비슷한가 싶으면서도 다른부분들이 꽤 많아서 어렵지만 재미도 있었다.
- 이번에는 API로 시도했지만 다음번에도 비슷한 구현을 한다면 RSS로 구현해보려고 한다.

## 실행 장면
![실행화면1](https://github.com/user-attachments/assets/d31ecb68-3770-4eba-b76b-09acace7ad82)
![실행화면2](https://github.com/user-attachments/assets/f33ac2f6-cdde-4833-b44c-f386b2b7eafa)
