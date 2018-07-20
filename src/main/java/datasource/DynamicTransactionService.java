package datasource;

import org.springframework.transaction.PlatformTransactionManager;

public interface DynamicTransactionService {

    PlatformTransactionManager getTransactionManager();

    PlatformTransactionManager getTransactionManager(String TransactionManagerName);

}
