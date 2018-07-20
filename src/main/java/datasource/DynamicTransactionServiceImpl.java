package datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import util.ReflectHelper;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class DynamicTransactionServiceImpl implements DynamicTransactionService{

    @Autowired
    private DynamicDataSource dynamicDataSource;

    private ConcurrentMap<DataSource, PlatformTransactionManager> dataSourceTransactionManagers = new ConcurrentHashMap<DataSource, PlatformTransactionManager>();

    private ConcurrentMap<String, PlatformTransactionManager> platformTransactionManagers = new ConcurrentHashMap<String, PlatformTransactionManager>();

    @Autowired
    public void setPlatformTransactionManagers(Map<String, PlatformTransactionManager> platformTransactionManagers) {
        for (Map.Entry entry : platformTransactionManagers.entrySet()) {
            try {
                PlatformTransactionManager transactionManager = (PlatformTransactionManager) entry.getValue();
                DataSource dataSource = (DataSource) ReflectHelper.getValueByFieldName((transactionManager), "dataSource");
                if (dataSource != null) {
                    this.dataSourceTransactionManagers.put(dataSource, transactionManager);
                }
            } catch (NoSuchFieldException e) {
            } catch (IllegalAccessException e) {
            }
        }
        platformTransactionManagers.putAll(platformTransactionManagers);
    }

    @Override
    public PlatformTransactionManager getTransactionManager() {
        DataSource dataSource = dynamicDataSource.determineTargetDataSource();
        PlatformTransactionManager transactionManager = dataSourceTransactionManagers.get(dataSource);
        return transactionManager;
    }

    @Override
    public PlatformTransactionManager getTransactionManager(String TransactionManagerName) {
        PlatformTransactionManager transactionManager = platformTransactionManagers.get(TransactionManagerName);
        return transactionManager;
    }
}
