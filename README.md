### 일정 관리 애플리케이션

**일정 생성**
- Method : POST
- URL : /api/tasks
- Parameter : HTML form (@ModelAttribute)
- Response : 등록 정보 
- Status code : 200 OK 

**전체 일정 조회**
- 수정 날짜와 사용자의 이름을 기준으로 일정을 조회하는 기능
- Method : GET
- URL : /api/tasks
- Parameter : date, user (@RequestParam)
- Response : 다건 응답 정보
- Status code : 200 OK 
- 기타 : date와 user는 모두 필수 파라미터가 아님

**선택한 일정 조회**
- 특정 Id에 해당하는 일정을 조회하는 기능
- Method : GET
- URL : /api/tasks/{id}
- Parameter : id (@PathVariable)
- Response : 단건 응답 정보
- Status code : 200 OK 
- 기타 : 해당하는 id를 갖는 일정이 없는 경우 404 NOT_FOUND가 반환

**선택한 일정 수정**
- 일정 내용과 작성자명을 변경하는 기능
- Method : Patch
- URL : /api/tasks/{id}
- Parameter : id (@PathVariabl), HTML form (@ModelAttribute)
- Response : 단건 응답 정보 (수정된 단건 정보) 
- Status code : 200 OK 
- 기타
	- 해당하는 id를 갖는 일정이 없는 경우 404 NOT_FOUND가 반환
	- 작성 시간은 변경되지 않고, 수정된 시간만 변경이 이루어짐
	- 일정을 등록할 때와 동일한 password 입력시에만, 수정이 가능

**선택한 일정 삭제**
- Method : Patch
- URL : /api/tasks/{id}
- Parameter : id (@PathVariabl), pw (@RequestParam)
- Response : void
- Status code : 200 OK 
- 기타
	- 해당하는 id를 갖는 일정이 없는 경우 404 NOT_FOUND가 반환
	- 해당 id를 갖는 할일이 삭제됨
	- 일정을 등록할 때와 동일한 password 입력시에만, 삭제 가능

**SQL**
DROP TABLE IF EXISTS task;
CREATE TABLE task
(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
     	task TEXT,
     	user VARCHAR(30),
     	pw VARCHAR(50),
     	createdAt DATETIME,
     	modifiedAt DATETIME
);
	