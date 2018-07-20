### 2017-07-20 

v1.0.0：
    添加多数据源
    
    使用方法：
        1、在应用项目中，引用jar
        2、扫描dynamic-datasource.xml资源文件
        3、创建bean
        ```
            <bean id="dynamicDataSource" class="datasource.DynamicDataSource">
                <property name="targetDataSources">
                    <map key-type="java.lang.String">
                        <entry key="dataSource" value-ref="dataSource"/>
                    </map>
                </property>
                <!--<property name="defaultTargetDataSource" ref="dataSource"/>-->
            </bean>
        ```
![动态数据源架构图](http://omkt629ja.bkt.clouddn.com/dynamic-dataresource.jpg)

注意：打jar的时候由于resources路径写成resource,导致资源一直打不到jar中
