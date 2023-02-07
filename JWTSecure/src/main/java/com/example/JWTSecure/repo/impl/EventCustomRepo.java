package com.example.JWTSecure.repo.impl;

import com.example.JWTSecure.DTO.EventDTO;
import com.example.JWTSecure.DTO.StudentDTO;
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
public class EventCustomRepo {
    @PersistenceContext
    private EntityManager entityManager;

    public List<EventDTO> doSearch(EventDTO eventDTO) {

        StringBuilder sql = new StringBuilder()
                .append("select id, name, image, from_date, to_date\n" +
                        "from event");
        sql.append(" WHERE 1 = 1 ");
        if (eventDTO.getKey_search()!=null) {
            sql.append(" AND (UPPER(name) LIKE CONCAT('%', UPPER(:name), '%') ESCAPE '&') ");
        }

        sql.append(" order by id");

        NativeQuery<EventDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (eventDTO.getKey_search()!=null) {
            query.setParameter("name", "%"+eventDTO.getKey_search()+"%");
        }

        query.addScalar("id", new LongType());
        query.addScalar("name", new StringType());
        query.addScalar("image", new StringType());
        query.addScalar("from_date", new StringType());
        query.addScalar("to_date", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(EventDTO.class));
        if (null != String.valueOf(eventDTO.getPage())) {
            query.setMaxResults(eventDTO.getPageSize());
            query.setFirstResult(((eventDTO.getPage() - 1) * eventDTO.getPageSize()));
        }
        return query.list();
    }

    public List<EventDTO> getTotal(EventDTO eventDTO) {

        StringBuilder sql = new StringBuilder()
                .append("select id, name, image, from_date, to_date\n" +
                        "from event");
        sql.append(" WHERE 1 = 1 ");
        if (eventDTO.getKey_search()!=null) {
            sql.append(" AND (UPPER(name) LIKE CONCAT('%', UPPER(:name), '%') ESCAPE '&') ");
        }

        sql.append(" order by id");

        NativeQuery<EventDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (eventDTO.getKey_search()!=null) {
            query.setParameter("name", "%"+eventDTO.getKey_search()+"%");
        }

        query.addScalar("id", new LongType());
        query.addScalar("name", new StringType());
        query.addScalar("image", new StringType());
        query.addScalar("from_date", new StringType());
        query.addScalar("to_date", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(EventDTO.class));
        query.list();
        return query.list();
    }
}
