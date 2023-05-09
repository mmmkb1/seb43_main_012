package com.codestates.seb43_main_012.member.controller;

import com.codestates.seb43_main_012.member.dto.MemberDto;
import com.codestates.seb43_main_012.member.service.MemberService;
import com.codestates.seb43_main_012.member.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<MemberDto> signUp(@RequestBody MemberDto memberDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.signup(memberDto));
    }

    @PostMapping("/login")
    public void login(@RequestBody MemberDto memberDto, HttpServletResponse response) throws IOException {
        MemberDto loggedInMember = memberService.login(memberDto);
        String token = jwtUtil.generateToken(loggedInMember.getUsername());
        response.setHeader("Authorization", "Bearer " + token);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<MemberDto>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id){
        memberService.deleteMember(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
