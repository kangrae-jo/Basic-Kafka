package kafka.config;

import jakarta.annotation.PostConstruct;
import kafka.member.entity.Member;
import kafka.member.entity.Role;
import kafka.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class InitConfig {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    void init() {
        String name = "test";
        String password = "1234";

        Member tester = Member.builder()
                .name(name)
                .password(passwordEncoder.encode(password))
                .role(Role.ADMIN)
                .build();

        memberRepository.save(tester);
    }

}
