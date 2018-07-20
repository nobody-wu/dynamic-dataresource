package datasource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Component
public class DynamicSqlSessionDaoSupport extends DaoSupport {

    @Autowired
    private DynamicDataSource dynamicDataSource;

    private ConcurrentMap<DataSource, SqlSession> sqlSessions = new ConcurrentHashMap<DataSource, SqlSession>();

    private ConcurrentMap<DataSource, SqlSessionFactory> targetSqlSessionFactorys = new ConcurrentHashMap<DataSource, SqlSessionFactory>();


    public SqlSession getSqlSession() {
        DataSource dataSource = dynamicDataSource.determineTargetDataSource();
        SqlSession sqlSession = sqlSessions.get(dataSource);
        if (sqlSession != null) {
            return sqlSession;
        } else {
            SqlSessionFactory sqlSessionFactory = targetSqlSessionFactorys.get(dataSource);
            if (sqlSessionFactory != null) {
                sqlSession = new SqlSessionTemplate(sqlSessionFactory);
                sqlSessions.put(dataSource, sqlSession);
            }
        }
        return sqlSession;
    }


    @Override
    protected void checkDaoConfig() {
//        do nothing;
    }

    @Autowired
    public void setTargetSqlSessionFactorys(Map<String, SqlSessionFactory> targetSqlSessionFactorys) {
        for (Map.Entry entry : targetSqlSessionFactorys.entrySet()) {
            SqlSessionFactory sqlSessionFactory = ((SqlSessionFactory) entry.getValue());
            DataSource dataSource = ((SqlSessionFactory) entry.getValue()).getConfiguration().getEnvironment().getDataSource();
            if (dataSource != null) {
                this.targetSqlSessionFactorys.put(dataSource, sqlSessionFactory);
            }
        }
    }
}