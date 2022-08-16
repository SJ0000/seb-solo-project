package me.sj.sebsoloproject.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MemberResponse {

    private Long id;
    private String name;
    private String gender;
    private String companyName;
    private String companyType;
    private String companyLocation;

    public MemberResponse(Member member){
        this.id = member.getId();
        this.name = member.getName();
        this.gender = member.getSex().name();
        this.companyName = member.getCompanyName();
        this.companyType = member.getCompanyType().getName();
        this.companyLocation = member.getCompanyLocation().getName();
    }

}
