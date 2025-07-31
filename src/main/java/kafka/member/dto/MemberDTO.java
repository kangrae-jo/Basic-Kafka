package kafka.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberDTO {

    private Long id;
    private String name;
    private String role;

}
