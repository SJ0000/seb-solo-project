package me.sj.sebsoloproject.api.v1;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sj.sebsoloproject.entity.CompanyLocation;
import me.sj.sebsoloproject.entity.CompanyType;
import me.sj.sebsoloproject.entity.Member;
import me.sj.sebsoloproject.entity.MemberResponse;
import me.sj.sebsoloproject.repository.CompanyRepository;
import me.sj.sebsoloproject.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 전체 회원 조회
 * 특정 조건에 맞는 회원 조회(조건 : 지역, 업종)
 */

@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;

    @GetMapping
    @Transactional
    public List<MemberResponse> members(@RequestParam(required = false) String type, @RequestParam(required = false) String location) {

        log.info("members type={}, location={}",type,location);

        List<Member> members;
        if (type != null && location != null) {
            CompanyType companyType = companyRepository.findCompanyTypeByName(type);
            CompanyLocation companylocation = companyRepository.findCompanyLocationByName(location);
            members = memberRepository.findAllByCompanyTypeAndCompanyLocation(companyType, companylocation);
        } else if (type != null) {
            CompanyType companyType = companyRepository.findCompanyTypeByName(type);
            members = memberRepository.findAllByCompanyType(companyType);
        } else if (location != null) {
            CompanyLocation companylocation = companyRepository.findCompanyLocationByName(location);
            members = memberRepository.findAllByCompanyLocation(companylocation);
        } else {
            members = memberRepository.findAll();
        }

        return members.stream().map(MemberResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/init")
    @Transactional
    public String init(){
        CompanyType manufacturing = new CompanyType("제조업");
        CompanyType distribution = new CompanyType("유통업");
        CompanyType it = new CompanyType("IT");
        companyRepository.save(manufacturing, distribution, it);

        CompanyLocation seoul = new CompanyLocation("서울");
        CompanyLocation busan = new CompanyLocation("부산");
        companyRepository.save(seoul, busan);

        memberRepository.save(new Member("member1", "1111", Member.Gender.FEMALE, "Company01", manufacturing, seoul));
        memberRepository.save(new Member("member2", "2222", Member.Gender.MALE, "Company02", distribution, seoul));
        memberRepository.save(new Member("member3", "3333", Member.Gender.MALE, "Company03", it, seoul));
        memberRepository.save(new Member("member4", "4444", Member.Gender.FEMALE, "Company04", it, busan));
        memberRepository.save(new Member("member5", "5555", Member.Gender.MALE, "Company05", manufacturing, busan));

        return "ok";
    }

}
