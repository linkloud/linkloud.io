package io.linkloud.api.global.Utils;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

// QueryDsl 정렬 기준 생성용 클래스
public class QueryDslUtils {

    public static List<OrderSpecifier> getAllOrderSpecifiers(Pageable pageable, Path path) {
        List<OrderSpecifier> ORDERS = new ArrayList<>();
        if (!pageable.getSort().isEmpty()) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                OrderSpecifier<?> orderBy
                    = getSortedColumn(direction, path, order.getProperty());
                ORDERS.add(orderBy);
            }
        }
        return ORDERS;
    }

    public static OrderSpecifier<?> getSortedColumn(Order order, Path<?> parent, String fieldName) {
        if(fieldName.equals("quantity") || fieldName.equals("popularity")) {
            NumberPath<Long> aliasQuantity = Expressions.numberPath(Long.class, fieldName);

            return new OrderSpecifier(order, aliasQuantity);
        }
        Path<Object> fieldPath = Expressions.path(Object.class, parent, fieldName);

        return new OrderSpecifier(order, fieldPath);
    }
}