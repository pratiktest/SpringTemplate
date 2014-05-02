SpringTemplate
==============
Basic String/Hibernate/Jersey Project to get up and running

**Creating the backend**:

We basically used the *JAX-RS* archetype to start with 
getting a JAX-RS archetype is pretty easy
mvn archetype generate:*-DarchetypeCatalog=http://download.java.net/maven/2*
The above archetype has the jersey implementation which generates the WAR

The next thing we use is *Spring*
In src/main/resources we create a bean location file called *beanLocation.xml*
We configure the *datasource* and *session factory* and set up the *Spring automatic component scan*

**datasource**
<bean id="dataSource" 
         class="org.springframework.jdbc.datasource.DriverManagerDataSource">
	<property name="driverClassName" value="com.mysql.jdbc.Driver" />
	<property name="url" value="jdbc:mysql://localhost:3306/spring" />
	<property name="username" value="pratik" />
	<property name="password" value="password" />
</bean>
This is going to add datasource bean to the context and we will be using it later to set up a database connection

**session Factory**
<bean id="sessionFactory" 
class="org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean">
 
    <property name="dataSource">
      <ref bean="dataSource"/>
    </property>
 
    <property name="hibernateProperties">
       <props>
         <prop key="hibernate.dialect">org.hibernate.dialect.MySQLDialect</prop>
         <prop key="hibernate.show_sql">true</prop>
       </props>
    </property>
 
    <property name="annotatedClasses">
	<list>
	<value>com.rest.example.customer.model.Customer</value>
	</list>
    </property>
</bean>

