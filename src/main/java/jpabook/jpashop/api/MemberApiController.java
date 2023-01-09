package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Validated Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Validated CreateMemberRequest request) {
        log.info("saveMemberV2 Requested!");
        Member member = Member.fromName(request.getName());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id, @RequestBody @Validated UpdateMemberRequest request) {
        log.info("updateMemberV2 Requested!");
        memberService.update(id, request.getName());
        Member member = memberService.findOne(id);
        return new UpdateMemberResponse(member.getId(), member.getName());
    }



    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static class UpdateMemberRequest {
        private String name;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static class CreateMemberRequest {
        @NotBlank
        private String name;
    }

    @Getter
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }


}
