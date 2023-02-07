package com.example.JWTSecure.repo.impl;

import com.example.JWTSecure.DTO.StudentDTO;
import com.example.JWTSecure.DTO.ViolateDTO;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ViolateCustomRepo {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ViolateDTO> doSearch(ViolateDTO violateDTO) {

        StringBuilder sql = new StringBuilder()
                .append("select name, image, fullname as violated_name, email, phone\n" +
                        "from violate join student s on violate.student_post_id = s.id\n" +
                        "join users u on s.user_id = u.id");
        sql.append(" WHERE 1 = 1 ");
        if (violateDTO.getKey_search()!=null) {
            sql.append(" AND (UPPER(fullname) LIKE CONCAT('%', UPPER(:full_name), '%') ESCAPE '&') ");
        }

        sql.append(" order by s.id");

        NativeQuery<ViolateDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (violateDTO.getKey_search()!=null) {
            query.setParameter("full_name", "%"+violateDTO.getKey_search()+"%");
        }

        query.addScalar("name", new StringType());
        query.addScalar("image", new StringType());
        query.addScalar("violated_name", new StringType());
        query.addScalar("email", new StringType());
        query.addScalar("phone", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(ViolateDTO.class));
        if (null != String.valueOf(violateDTO.getPage())) {
            query.setMaxResults(violateDTO.getPageSize());
            query.setFirstResult(((violateDTO.getPage() - 1) * violateDTO.getPageSize()));
        }
        return query.list();
    }

    public List<ViolateDTO> getTotal(ViolateDTO violateDTO) {

        StringBuilder sql = new StringBuilder()
                .append("select name, image, fullname as violated_name, email, phone\n" +
                        "from violate join student s on violate.student_post_id = s.id\n" +
                        "join users u on s.user_id = u.id");
        sql.append(" WHERE 1 = 1 ");
        if (violateDTO.getKey_search()!=null) {
            sql.append(" AND (UPPER(fullname) LIKE CONCAT('%', UPPER(:full_name), '%') ESCAPE '&') ");
        }

        sql.append(" order by s.id");

        NativeQuery<ViolateDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (violateDTO.getKey_search()!=null) {
            query.setParameter("full_name", "%"+violateDTO.getKey_search()+"%");
        }

        query.addScalar("name", new StringType());
        query.addScalar("image", new StringType());
        query.addScalar("violated_name", new StringType());
        query.addScalar("email", new StringType());
        query.addScalar("phone", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(ViolateDTO.class));

        return query.list();
    }
}
