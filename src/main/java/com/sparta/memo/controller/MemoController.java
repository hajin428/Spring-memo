package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();

    //메모 작성하기
    @PostMapping ("/memos")
    public MemoResponseDto createMemo (@RequestBody MemoRequestDto requestDto){
        //RequestDto >> Entity
        //클라이언트로부터 받아온 body 데이터를 memo 객체로
        Memo memo = new Memo(requestDto);

        //Memo Max ID Check
        //메모리스트가 0보다 크면(메모가 1개라도 있으면) 가장 큰 값에 1을 더해줌(거기에 작성됨) 아니라면 1 넣어주기
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 :1;
        memo.setId(maxId);

        //DB 저장
        memoList.put(memo.getId(), memo);

        //Entity >> ResponseDto
        MemoResponseDto memoresponseDto = new MemoResponseDto(memo);
        return memoresponseDto;
    }


    //메모 조회하기
    @GetMapping ("/memos")
    public List<MemoResponseDto> getMemos(){
        //Map을 List로 바꾸기
        List<MemoResponseDto> responseList = memoList.values().stream()
                .map(MemoResponseDto::new).toList();
        return responseList;
    }

    //메모 수정
    @PutMapping ("/memos/{id}")
    //id는 @PathVariable로 받아오기, 업데이트할 내용은 @RequestBody로 받아온 JSON 데이터를 Dto로 받아주기
    public Long updateMemo (@PathVariable Long id, @RequestBody MemoRequestDto requestDto){
        //해당 메모가 DB에 존재하는지 확인
        //List.containsKey : Map의 자료 구조에서 key 부분에(Long) 해당하는 자료가 있는지 확인하는 것
        if(memoList.containsKey(id)){
            //해당 메모가 DB에 존재하는지 확인
            Memo memo = memoList.get(id);
            memo.update(requestDto);
            return id;

        } else {
            //일치하는 아이디가 없을 떄
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    //메모 삭제
    @DeleteMapping("/memos/{id}")
    public Long deleteMemo (@PathVariable Long id){
        //해당 메모가 DB에 존재하는지 확인
        if(memoList.containsKey(id)){
            memoList.remove(id);
            return id;
        } else {
            //일치하는 아이디가 없을 떄
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

















}
