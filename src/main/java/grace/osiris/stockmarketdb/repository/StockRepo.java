package grace.osiris.stockmarketdb.repository;

import grace.osiris.stockmarketdb.models.Stock;
import org.springframework.data.repository.CrudRepository;

public interface StockRepo extends CrudRepository<Stock, Long>{
    Stock findStockByCode(String partialString);
    Iterable<Stock> findAllByQuantityGreaterThan(int partialInt);
    Iterable<Stock> findAllByQuantityLessThan(int partialInt);
}
