package grace.osiris.stockmarketdb.repository;

import grace.osiris.stockmarketdb.models.Sales;
import org.springframework.data.repository.CrudRepository;

public interface SalesRepo extends CrudRepository<Sales, Long>{

}
