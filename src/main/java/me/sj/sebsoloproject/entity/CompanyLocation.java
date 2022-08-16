package me.sj.sebsoloproject.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyLocation {
    @Id @GeneratedValue
    private Long id;
    private String name;

    public CompanyLocation(String name) {
        this.name = name;
    }
}
