# 2018년 서울시 앱 공모전 출품작(세이프랜드)

본 repository는 2018년 서울시 앱 공모전에 출품될 안드로이드 기반 어플리케이션입니다.

### 팀원:
- 김선국 
- 서호진
- 박범민
- 홍혜림

# 라이센스(License)
<a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/" target="_blank"><img alt="크리에이티브 커먼즈 라이선스" style="border-width:0" src="https://i.creativecommons.org/l/by-sa/4.0/88x31.png" /></a><br />이 저작물은 <a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/" target="_blank">크리에이티브 커먼즈 저작자표시-동일조건변경허락 4.0 국제 라이선스</a>에 따라 이용할 수 있습니다.

# Android Development Naming Rule
## 0. 공통 규칙
- 0.1 파스칼 표기법(PascalCase)과 카멜 표기법(camelCase)과 스네이크 표기법(snake_case)을 사용한다.
- 0.2 약어 사용 시 클래스 시작 부분에 주석으로 표기한다.
- 0.3 반의어는 반드시 대응하는 개념으로 사용한다.
- 0.4 메소드 작성 후 메소드 바로 위에 메소드에 대한 설명을 주석으로 표기한다.
<pre><code>/**
* 결과 받아오기
* @param requestCode 요청 코드 * @param resultCode 결과 코드
* @param data 넘어온 데이터
*/
public void onActivityResult(int requestCode, int resultCode, Intent data) {} 
</code></pre>
- 0.5 소스 코드 관리는 github를 활용한다.
- 0.6 기능 구현은 feature 브런치에서 하고, 담당자가 develop 브런치에 merge한다.
- 0.7 중괄호 및 닫힘 태그는 코드 마지막에 붙인다.
- 0.8 반복문, 조건문 사용 시 반드시 중괄호를 사용한다.
- 0.9 들여쓰기는 탭을 사용한다.

## 1. 패키지 명명 규칙
- 1.1 com.seoul_app_contest.safe_friend 을 따른다.
- 1.2 소문자로만 작성한다.
- 1.3 한 단어의 명사를 사용한다.
<pre>com.seoul_app_contest.safe_friend
com.seoul_app_contest.safe_friend.dto
</pre>

## 2. 클래스 명명 규칙
- 2.1 파스칼 표기법을 따른다.
<pre><code>public class HelloWorld {}</code></pre>
- 2.2 의미에 따라 접미사를 붙인다.
<pre><code>public class UserDto {}
public class MapFragment {}
public class BusAdapter {}</code></pre>
    
## 3. 메소드 명명 규칙
- 3.1 카멜 케이스 표기법을 따른다.
<pre><code>void selectGuide() {}</code></pre>
- 3.2 속성에 접근하는 메소드는 접두사에 get/set을 사용한다.
<pre><code>void getUserId() {}
void setUserId() {}</code></pre>

## 4. 변수 명명 규칙
- 4.1 카멜 케이스 표기법을 따른다.
- 4.2 메소드의 파라미터의 경우 데이터 타입과 같게 사용한다.
<pre><code>void getUser(UserDto userDto) {}</code></pre>
- 4.3 상수의 경우 대문자와 _를 사용한다.
<pre><code>private static final String TAG = “safe_friend”;</code></pre>
- 4.4 boolean 타입의 경우 접두사에 is를 사용한다.
