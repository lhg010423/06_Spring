// 목록으로 버튼 동작
const goToList = document.querySelector("#goToList");

goToList.addEventListener("click", () => {
    location.href = "/"; // 메인 페이지 요청
    // 자바스크립트의 윈도우 객체 중에 하나다
})

// 완료 여부 변경 버튼 동작
const completeBtn = document.querySelector(".complete-btn");

completeBtn.addEventListener("click", (e) => {

    //              요소.dataset
    const todoNo = e.target.dataset.todoNo;

    // console.log(todoNo);
    
    // Y <-> N 변경

    // 값이 변경될수 있어서 let 씀
    let complete = e.target.innerText; // 기존 완료 여부 값 얻어오기

    complete = (complete === 'Y') ? 'N' : 'Y';

    // 완료 여부 수정 요청하기(location은 get요청)
    location.href
        = `/todo/changeComplete?todoNo=${todoNo}&complete=${complete}`; // 리터럴 템플릿 : ``
    // 기존에 ""를 쓰면 뒤에 + 로 이어붙이는게 귀찮아서 ``를 씀
})



//-----------------------------------------------------------------------

// 수정 버튼 클릭 시
const updateBtn = document.querySelector("#updateBtn");

updateBtn.addEventListener("click", e => {

    // data-todo-no 얻어오기
    const todoNo = e.target.dataset.todoNo;

    location.href = `/todo/update?todoNo=${todoNo}`;


});



//-----------------------------------------------------------------------

// 삭제 버튼 클릭 시
const deleteBtn = document.querySelector("#deleteBtn");

deleteBtn.addEventListener("click", e => {

    if(confirm("삭제 하시겠습니까?")) {
        location.href = `/todo/delete?todoNo=${e.target.dataset.todoNo}`;

    }

});