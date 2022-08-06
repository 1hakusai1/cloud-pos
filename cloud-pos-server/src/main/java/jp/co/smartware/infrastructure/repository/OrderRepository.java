package jp.co.smartware.infrastructure.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jp.co.smartware.domain.order.Order;
import jp.co.smartware.domain.order.OrderStatus;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

    // ElementCollections要素のbulke deleteが出来ないので、一つづつ消すようにする。
    // see https://hibernate.atlassian.net/browse/HHH-5529
    @Override
    public long deleteAll() {
        long count = 0;
        List<Order> orders = listAll();
        for (Order order : orders) {
            delete(order);
            count++;
        }
        return count;
    }

    public List<Order> listWaiting() {
        return list("status", OrderStatus.WAITING);
    }

}
