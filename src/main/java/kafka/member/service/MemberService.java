package kafka.member.service;

import kafka.jwt.JwtProvider;
import kafka.member.entity.Member;
import kafka.member.entity.Role;
import kafka.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public void register(String name, String password) {
        if (memberRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("[ERROR] 이미 사용 중인 이름입니다.");
        }

        Member member = Member.builder()
                .name(name)
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .build();

        memberRepository.save(member);
    }

    public String login(String name, String password) {
        Member member = memberRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("[ERROR] 비밀번호가 일치하지 않습니다.");
        }

        return jwtProvider.createToken(
                member.getId(),
                member.getName(),
                member.getRole().name()
        );
    }

}
