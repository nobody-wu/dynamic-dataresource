package datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

/**
 * 扩展Spring的AbstractRoutingDataSource抽象类:（该类充当了DataSource的路由中介, 能有在运行时, 根据某种key值来动态切换到真正的DataSource上。）
 */
public class DynamicDataSource extends AbstractRoutingDataSource {


    @Override
    protected DataSource determineTargetDataSource() {
        return super.determineTargetDataSource();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicSwitcherUtil.getDataSourceKey();
    }
}