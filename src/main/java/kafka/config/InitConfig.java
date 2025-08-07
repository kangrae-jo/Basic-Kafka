package kafka.config;

import jakarta.annotation.PostConstruct;
import kafka.member.entity.Member;
import kafka.member.entity.Role;
import kafka.member.repository.MemberRepository;
import kafka.menu.entity.Menu;
import kafka.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class InitConfig {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;

    @PostConstruct
    void registerInit() {
        String name = "test";
        String password = "1234";

        Member tester = Member.builder()
                .name(name)
                .password(passwordEncoder.encode(password))
                .role(Role.ADMIN)
                .build();

        memberRepository.save(tester);
    }

    @PostConstruct
    void menuInit() {
        Menu menu1 = Menu.builder()
                .name("치즈버거")
                .price(2300)
                .description("고소한 치즈와 패티의 조화")
                .available(true)
                .build();

        Menu menu2 = Menu.builder()
                .name("불고기버거")
                .price(4200)
                .description("달콤한 불고기 소스")
                .available(true)
                .build();

        menuRepository.save(menu1);
        menuRepository.save(menu2);
    }
    
}
