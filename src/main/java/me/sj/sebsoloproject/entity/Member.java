package me.sj.sebsoloproject.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender sex;

    private String companyName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company_type")
    private CompanyType companyType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company_location")
    private CompanyLocation companyLocation;

    public Member(String name, String password, Gender sex, String companyName, CompanyType companyType, CompanyLocation companyLocation) {
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.companyName = companyName;
        this.companyType = companyType;
        this.companyLocation = companyLocation;
    }

    public enum Gender{
        MALE, FEMALE
    }
}
