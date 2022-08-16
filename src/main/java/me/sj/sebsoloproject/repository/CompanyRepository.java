package me.sj.sebsoloproject.repository;


import lombok.RequiredArgsConstructor;
import me.sj.sebsoloproject.entity.CompanyLocation;
import me.sj.sebsoloproject.entity.CompanyType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CompanyRepository {

    private final EntityManager em;

    public CompanyType findCompanyTypeByName(String name){
        return em.createQuery("select t from CompanyType t where t.name = :name", CompanyType.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public CompanyLocation findCompanyLocationByName(String name){
        return em.createQuery("select l from CompanyLocation l where l.name = :name", CompanyLocation.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public void save(Object... objects){
        for (Object object : objects) {
            em.persist(object);
        }
    }
}