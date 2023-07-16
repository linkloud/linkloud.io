package io.linkloud.api.global.utils;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

// QueryDsl 정렬 기준 생성용 클래스
@Component
public class QueryDslUtils {
    // pageable에서 sort 객체로 OrderSpecifier를 생성하여 반환.
    public OrderSpecifier[] getAllOrderSpecifiers(Pageable pageable, Path path) {
        List<OrderSpecifier> orders = convertToDslOrder(pageable, path);
        return orders.toArray(OrderSpecifier[]::new);
    }

    // Sort 객체에 Order가 존재할 때 QueryDSL Order로 변환
    private static List<OrderSpecifier> convertToDslOrder(Pageable pageable, Path path) {
        List<OrderSpecifier> orders = new ArrayList<>();
        if (!pageable.getSort().isEmpty()) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                OrderSpecifier<?> orderBy
                    = createOrderSpecifier(direction, path, order.getProperty());
                orders.add(orderBy);
            }
        }
        return orders;
    }

    private static OrderSpecifier<?> createOrderSpecifier(Order order, Path<?> parent, String fieldName) {
        // count 메소드 컬럼을 기준으로 할 때의 OrderSpecifier
        if(fieldName.equals("quantity") || fieldName.equals("popularity")) {
            NumberPath<Long> aliasQuantity = Expressions.numberPath(Long.class, fieldName);

            return new OrderSpecifier(order, aliasQuantity);
        }

        // 일반 컬럼을 기준으로 할때의 OrderSpecifier
        Path<Object> fieldPath = Expressions.path(Object.class, parent, fieldName);

        return new OrderSpecifier(order, fieldPath);
    }
}