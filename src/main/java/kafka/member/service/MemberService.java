package kafka.member.service;

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

}
