package jp.co.smartware.infrastructure.repository;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jp.co.smartware.domain.product.JANCode;
import jp.co.smartware.domain.stock.Stock;

@ApplicationScoped
public class StockRepository implements PanacheRepositoryBase<Stock, JANCode> {

}
