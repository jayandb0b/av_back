package com.sw.av.domain.util;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class GenericSpecification<T> implements Specification<T> {

    private static final long serialVersionUID = 1900581010229669687L;

    private List<SearchCriteria> list;

    public GenericSpecification() {
        this.list = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        //create a new predicate list
        List<Predicate> predicates = new ArrayList<>();

        //add criteria to predicates
        for (SearchCriteria criteria : list) {
            if (criteria.getOperation().equals(SearchOperation.GREATER_THAN))
            {
                predicates.add(builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
            }
            else if (criteria.getOperation().equals(SearchOperation.LESS_THAN))
            {
                predicates.add(builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
            }
            else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL))
            {
                predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
            }
            else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL))
            {
                predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
            }
            else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL))
            {
                predicates.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
            }
            else if (criteria.getOperation().equals(SearchOperation.EQUAL))
            {
                predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
            }
            else if (criteria.getOperation().equals(SearchOperation.LIKE))
            {
                predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),"%" + criteria.getValue().toString().toLowerCase() + "%"));
            }
            else if (criteria.getOperation().equals(SearchOperation.STARTS_LIKE))
            {
                predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), criteria.getValue().toString().toLowerCase() + "%"));
            }
            else if (criteria.getOperation().equals(SearchOperation.ENDS_LIKE))
            {
                predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase()));
            }
            else if (criteria.getOperation().equals(SearchOperation.EQUAL_LOWERCASE))
            {
                predicates.add(builder.equal(builder.lower(root.get(criteria.getKey())), criteria.getValue().toString().toLowerCase()));
            }
            else if (criteria.getOperation().equals(SearchOperation.IS_NULL))
            {
                predicates.add(builder.isNull(root.get(criteria.getKey())));
            }
            else if (criteria.getOperation().equals(SearchOperation.IS_NOT_NULL))
            {
                predicates.add(builder.isNotNull(root.get(criteria.getKey())));
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}