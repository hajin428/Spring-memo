package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.service.MemoService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/memos")
public class MemoController {

    private final MemoService memoService;
    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }


    // 메모 작성
    @PostMapping
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        //서비스 클래스의 creatMemo 메서드 호출, 사용할 데이터는 requestDto 넣어줌
        //creatMemo에서 실행한 값을 바로 리턴
        return memoService.createMemo(requestDto);
    }

    //메모 전체 조회
    @GetMapping
    public List<MemoResponseDto> getMemos() {
        return memoService.getMemos();
    }

    //메모 수정
    @PutMapping("/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        return memoService.updateMemo(id, requestDto);
    }

    //메모 삭제
    @DeleteMapping("/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        return memoService.deleteMemo(id);
    }
}