// console.log("main.js loaded.");

// 쿠키에서 key가 일치하는 value 얻어오기 함수

// 쿠키는 "K=V; K=V; K=V; ..." 형식

// 배열.map(함수) : 배열의 각 요소를 이용해 함수 수행 후
//                  결과 값을 새로운 배열을 만들어서 반환

const getCookie = (key) => {
    // 쿠키 전부 꺼내야 함, 브라우저(클라이언트)에 저장된거니 document를 씀

    // document.cookie = "text" + "=" + "유저일";
    const cookies = document.cookie;
    // console.log(cookies);

    // cookies 문자열을 배열 형태로 변환
    const cookieList = cookies.split("; ") // ["K=V", "K=V"]
                    .map( el => el.split("=") ); // ["K", "V"]...
    // console.log(cookieList);

    // 배열 -> 객체로 변환 (그래야 다루기 쉽다)

    const obj = {}; // 비어있는 객체 선언

    for(let i=0; i < cookieList.length; i++) {
        const k = cookieList[i][0]; // key 값
        const v = cookieList[i][1]; // value 값
        obj[k] = v; // 객체에 추가
    }
    
    //console.log(obj);
    
    return obj[key]; // 매개변수로 전달 받은 key와
                    // obj 객체에 저장된 키가 일치하는 요쇼의 value 값 반환
                    
};

const loginEmail = document.querySelector("#loginForm input[name='memberEmail']");

// 로그인 안된 상태인 경우에 수행
if(loginEmail != null) { // 로그인창의 이메일 입력부분이 화면에 있을 때

    // 쿠키 중 key 값이 "saveId"인 요소의 value 얻어오기
    const saveId = getCookie("saveId"); // undefined 또는 이메일

    // saveId 값이 있을 경우
    if(saveId != undefined) {
        loginEmail.value = saveId; // 쿠키에서 얻어온 값을 input에 value로 세팅
        
        // 아이디 저장 체크박스에 체크 해두기
        document.querySelector("input[name='saveId']").checked = true;

    }
    
}


// 이메일, 비밀번호 미작성 시 로그인 막기
const loginForm = document.querySelector("#loginForm");

const loginPw = document.querySelector("#loginForm input[name='memberPw']");

// #loginForm이 화면에 존재할 때(== 로그인 상태 아닐 때)
if(loginForm != null) {

    // 제출 이벤트 발생 시
    loginForm.addEventListener("submit", e => {

        // 이메일 미작성
        if(loginEmail.value.trim().length === 0) {
            alert("이메일을 작성해주세요!");
            e.preventDefault(); // 기본 이멘트(제출) 막기
            loginEmail.focus(); // 초점 이동
            return;
        }

        // 비밀번호 미작성
        if(loginPw.value.trim().length === 0) {
            alert("비밀번호를 작성해주세요!");
            e.preventDefault(); // 기본 이멘트(제출) 막기
            loginPw.focus(); // 초점 이동
            return;
        }
    });
}


console.log(getCookie("saveId"));
// 쿠키에 값이 없으면 undefined이 나옴

//--------------------------------------------------------------------

/* 빠른 로그인 */
const quickLoginBtns = document.querySelectorAll(".quick-login");
// 배열로 얻어옴

// 매개변수 item, index 2개
quickLoginBtns.forEach( (item, index) => {
    // item : 현재 반복 시 꺼내온 객체
    // index : 현재 반복 중인 인덱스

    // quickLoginBtns 요소인 button 태그 하나씩 꺼내서 이벤트 리스너 추가
    item.addEventListener("click", () => {

        const email = item.innerText; // 버튼에 작성된 이메일 얻어오기

        // location은 get요청이다
        location.href = "/member/quickLogin?memberEmail=" + email;

    })


})



//---------------------------------------------------------

/* 회원 목록 조회(비동기) */

// 조회 버튼
const selectMemberList = document.querySelector("#selectMemberList");

// tbody
const memberList = document.querySelector("#memberList");

// to 요소를 만들고 text 추가 후 반환, 매개변수에 text말고 다른거 써도됨
const createTd = (text) => {
    const td = document.createElement("td");
    td.innerText = text;
    return td; // <td>1</td>  <td>user01@kh.or.kr</td> <td>유저일</td> <td>N</td>
}

// 조회 버튼 클릭 시
selectMemberList.addEventListener("click", () => {

    // 1) 비동기로 회원 목록 조회
    //   (포함된 회원 정보 : 회원번호, 이메일, 닉네임, 탈퇴여부)
    
    // 조회라 전달할 값없음
    fetch("/member/selectMemberList")
    .then(response => response.json()) // JSON.parse(response) / 한번에 파싱하기
    .then(list => {

        // .then(response => response.text())   를 쓰면
        // 아래처럼 해야함 04/16 14:41
        // const data = JSON.parse(list);

        console.log(list);

        // list 바로 이용 -> JS 객체 배열

        //  이전 내용 삭제
        memberList.innerHTML = "";

        // tbody에 들어갈 요소를 만들고 값 세팅 후 추가
        list.forEach( (member, index) => {

            // member : 현재 반복 접근 중인 요소
            // index : 현재 접근중인 인덱스

            // tr 만들어서 그 안에 td 만들고, append 후
            // tr을 tbody 에 append

            // 위 .then(list)는 객체의 리스트 전부 나온다
            const keyList = ['memberNo', 'memberEmain', 'memberNickname', 'memberDelFl'];

            const tr = document.createElement("tr");

            // keyList.forEach(key => tr.append( createTd(member[memberNo])))
            // 이렇게 keyList의 key값을 하나씩 집어넣는다, member객체의 key값에 맞는 value값이 대입된다
            // 그럼 createTd(value) 가 된다
            keyList.forEach(key => tr.append( createTd(member[key])))
            // forEach문이 반복될때 createTd함수가 반복된다

            // tbody 자식으로 tr 추가
            memberList.append(tr);
        })


    })

})




//---------------------------------------------------------------

/* 특정 회원 비밀번호 초기화 */
const resetMemberNo = document.querySelector("#resetMemberNo");
const resetPw = document.querySelector("#resetPw");

resetPw.addEventListener("click", () => {

    // 입력 받은 회원 번호 얻어오기
    const inputNo = resetMemberNo.value;

    if(inputNo.trim().length == 0) {
        alert("회원 번호 입력해주세요");
        return;
    }

    fetch("/member/resetPw", { // 이렇게 안써도됨 다른 방법도 있음
        method : "PUT", // PUT : 수정 요청 방식
        headers : {"Content-Type" : "application/json"},
        body : inputNo
    })
    .then(resp => resp.text())
    .then(result => {
        // result == 컨트롤러로부터 반환받아 TEXT 로 파싱한 값
        // "1", "0"

        if(result > 0 ) {
            alert("초기화 성공!");

        } else {
            alert("해당 회원이 존재하지 않습니다 : ");
        }
    })
})


