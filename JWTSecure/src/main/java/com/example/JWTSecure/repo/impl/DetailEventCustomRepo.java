package com.example.JWTSecure.repo.impl;

import com.example.JWTSecure.DTO.DetailEventDTO;
import com.example.JWTSecure.DTO.ViolateDTO;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DetailEventCustomRepo {

    @PersistenceContext
    private EntityManager entityManager;

    public List<DetailEventDTO> doSearch(DetailEventDTO detailEventDTO) {

        StringBuilder sql = new StringBuilder()
                .append("select sie.id, fullname, email, phone, address, caption, sie.image, register_date, e.name as event_name\n" +
                        "from student join student_in_event sie on student.id = sie.student_id\n" +
                        "join users u on student.user_id = u.id join event e on sie.event_id = e.id");
        sql.append(" WHERE 1 = 1 ");
        if (detailEventDTO.getKey_search()!=null) {
            sql.append(" AND (UPPER(fullname) LIKE CONCAT('%', UPPER(:full_name), '%') ESCAPE '&') ");
        }

        sql.append(" order by sie.id");

        NativeQuery<DetailEventDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (detailEventDTO.getKey_search()!=null) {
            query.setParameter("full_name", "%"+detailEventDTO.getKey_search()+"%");
        }

        query.addScalar("id", new LongType());
        query.addScalar("fullname", new StringType());
        query.addScalar("email", new StringType());
        query.addScalar("phone", new StringType());
        query.addScalar("address", new StringType());
        query.addScalar("caption", new StringType());
        query.addScalar("image", new StringType());
        query.addScalar("register_date", new StringType());
        query.addScalar("event_name", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(DetailEventDTO.class));
        if (null != String.valueOf(detailEventDTO.getPage())) {
            query.setMaxResults(detailEventDTO.getPageSize());
            query.setFirstResult(((detailEventDTO.getPage() - 1) * detailEventDTO.getPageSize()));
        }
        return query.list();
    }

    public List<DetailEventDTO> getTotal(DetailEventDTO detailEventDTO) {

        StringBuilder sql = new StringBuilder()
                .append("select sie.id, fullname, email, phone, address, caption, sie.image, register_date, e.name as event_name\n" +
                        "from student join student_in_event sie on student.id = sie.student_id\n" +
                        "join users u on student.user_id = u.id join event e on sie.event_id = e.id");
        sql.append(" WHERE 1 = 1 ");
        if (detailEventDTO.getKey_search()!=null) {
            sql.append(" AND (UPPER(fullname) LIKE CONCAT('%', UPPER(:full_name), '%') ESCAPE '&') ");
        }

        sql.append(" order by sie.id");

        NativeQuery<DetailEventDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (detailEventDTO.getKey_search()!=null) {
            query.setParameter("full_name", "%"+detailEventDTO.getKey_search()+"%");
        }

        query.addScalar("id", new LongType());
        query.addScalar("fullname", new StringType());
        query.addScalar("email", new StringType());
        query.addScalar("phone", new StringType());
        query.addScalar("address", new StringType());
        query.addScalar("caption", new StringType());
        query.addScalar("image", new StringType());
        query.addScalar("register_date", new StringType());
        query.addScalar("event_name", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(DetailEventDTO.class));

        return query.list();
    }
}
