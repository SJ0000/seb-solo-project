package me.sj.sebsoloproject.repository;

import me.sj.sebsoloproject.entity.CompanyLocation;
import me.sj.sebsoloproject.entity.CompanyType;
import me.sj.sebsoloproject.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface MemberRepository extends JpaRepository<Member,Long> {
    List<Member> findAllByCompanyLocation(CompanyLocation location);
    List<Member> findAllByCompanyType(CompanyType location);
    List<Member> findAllByCompanyTypeAndCompanyLocation(CompanyType type, CompanyLocation location);
}
