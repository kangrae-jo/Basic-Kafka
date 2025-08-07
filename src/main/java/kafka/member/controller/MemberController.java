package kafka.member.controller;

import kafka.member.dto.MemberDTO;
import kafka.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam("name") String name,
                                      @RequestParam("password") String password) {
        try {
            memberService.register(name, password);
            return ResponseEntity.ok("[INFO] 회원가입 완료");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 로그인
    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam("name") String name,
                                   @RequestParam("password") String password) {
        try {
            String token = memberService.login(name, password);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 본인 이름 반환 api (jwt 확인용)
    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal MemberDTO member) {
        return ResponseEntity.ok(member);
    }

}
