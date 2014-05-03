SpringTemplate
==============
Basic String/Hibernate/Jersey Project to get up and running

**Creating the backend**:

We basically used the ***JAX-RS archetype*** to start with 
getting a JAX-RS archetype is pretty easy
mvn archetype generate:***-DarchetypeCatalog=http://download.java.net/maven/2***
The above archetype has the jersey implementation which generates the WAR

The next thing we use is ***Spring***
In src/main/resources we create a bean location file called ***beanLocation.xml***
We configure the ***datasource*** and ***session factory*** and set up the ***Spring automatic component scan***

**datasource**


<bean id="dataSource" 
class="org.springframework.jdbc.datasource.DriverManagerDataSource">

	<property name="driverClassName" value="com.mysql.jdbc.Driver" />
	<property name="url" value="jdbc:mysql://localhost:3306/spring" />
	<property name="username" value="pratik" />
	<property name="password" value="password" />
</bean>
This is going to add datasource bean to the context and we will be using it later to set up a database connection

**session Factory** (*This will be autowired in code*)

```
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
```

Put all the above beans in beanlocation.xml
Now to enable component scanning
 add this to beanLocation.xml
 
```
 <context:component-scan 
		base-package="com.rest.example" />
```

####create customer business object
**package structure for hibernate**

customer.bo -> CustomerBo, CustomerBoImpl

customer.dao -> CustomerDao, CustomerDaoImpl

customer.model -> Customer

We also need to autowire a session factory

SessionFactory is singleton which implements the factory design pattern. It loads the hibernate.cfg.xml file. It contains mappings for database.

to autowire a session factory do the following
public abstract class CustomHibernateDaoSupport extends 



```
public abstract class CustomHibernateDaoSupport extends HibernateDaoSupport
{    
    @Autowired
    public void anyMethodName(SessionFactory sessionFactory)
    {
        setSessionFactory(sessionFactory);
    }
}
```
Note the session factory contains all the information in its properties to communicate to respective database

We are using the MySQLDialect to communicate to database
so we have the drivers automatically included

We have put the beanlocation.xml in src/main/resources/spring/config/beanlocation.xml

**web.xml**

The main thing to configure now is the web.xml. We need to tell the servlet where is the beanlocation file so it can enable auto component scanning

*We have to create a listener to tell where the beanlocation.xml file is*

```
<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>/WEB-INF/classes/spring/config/BeanLocations.xml</param-value>
	</context-param>
```

Note the resources folder in maven will get transformed to classes/.… in WEB-INF. for example resources/spring/config will be WEB-INF/classes/spring/config in maven

of course we also create a basic servlet and a servlet mapping in our file

```
<servlet>
        <servlet-name>Jersey Web Application</servlet-name>
        <servlet-class>com.sun.jersey.spi.container.servlet.ServletContainer</servlet-class>
        <init-param>
            <param-name>com.sun.jersey.config.property.packages</param-name>
            <param-value>com.rest.example</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>Jersey Web Application</servlet-name>
        <url-pattern>/webresources/*</url-pattern>
    </servlet-mapping>
   ```
   
   <span style = "color:red">
   Note we cannot use component scanning on the controller since jersey container holds them and so it conflicts with the Spring container. Basically we cannot advice the controller functions since they will not be present in the Spring context 
   </span>
   
   **CustomerBo the service object**
   
   We can put beans in spring context using component, repository, service annotations. Lets see customerBo as a service which offers us the abstraction and serves as a good service pointcut
  
  ``` 
   @Service("customerBo")
public class CustomerBoImpl implements CustomerBo {

	@Autowired
	CustomerDao customerDao;
	
	
	public void save(Customer customer) {
		customerDao.save(customer);
		
	}

	public void update(Customer customer) {
		customerDao.update(customer);
		
	}

	public void delete(Customer customer) {
		customerDao.delete(customer);
		
	}

	public Customer findByCustomerId(int customerId) {
		return customerDao.findByCustomerId(customerId);
	}

}

```

customerDao the repository

customerDao will have the native code to directly talk to database and issue queries. The basics are provided to us by the hibernateTemplate

```
@Repository("customerDao")
public class CustomerDaoImpl extends CustomHibernateDaoSupport implements CustomerDao {

	public void save(Customer customer) {
		getHibernateTemplate().save(customer);
		
	}

	public void update(Customer customer) {
		getHibernateTemplate().update(customer);
		
	}

	public void delete(Customer customer) {
		getHibernateTemplate().delete(customer);
		
	}

	public Customer findByCustomerId(int customerId) {
		List<?> list = getHibernateTemplate().find("from Customer where customerId=?", customerId);
		return (Customer)(list.get(0));
	}

}
```
Note we have named the repository as customerDao which is implementation of customerDao. This is autowired in customerBo

***For servlets we need the WebApplication context***

We need to inject the servlet context which holds information about where the beanLocation is in webApplication context

We can then use this context to extract beans from the context

```
@Component
@Path("/Customer")
public class CustomerResource {
	
	final int TEST_CUSTOMER_ID = 1;
	final String TEST_CUSTOMER_NAME = "pratik";
	
	@Context ServletContext servletContext;
    
    @GET 
    @Produces("application/json")
    @Path("/insert")
    public Response insertCustomer()  {
    	
    	Customer customer = new Customer();

	    	WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	    	
	    	CustomerBo customerBo = (CustomerBo)appContext.getBean("customerBo");
	    	
	    	Customer customerInTable  = customerBo.findByCustomerId(3);
	    	if(customerInTable != null){
	    		customerBo.delete(customerInTable);
	    	}
	    	
	    	//customer.setId(TEST_CUSTOMER_ID);
	    	customer.setName(TEST_CUSTOMER_NAME);
	    	
	    	customerBo.save(customer);
  
	    	
        return ResponseHelper.constructResponse(customer);
    }
}
```

<span style = "color:red">Note the @Component is redundant here since this is a jersey controller and will not get registered in spring context. I have just kept it here to remind me of my mistake. Anyways it dosent do anything </span>

#### The customer model itself ####

The customer Pojo which we will emit should implement Serializable. It should have proper jackson annotations to serialize and deserialize. Also proper hibernate annotations to map to columns in database

```
@XmlRootElement
@Entity
@Table(name = "Customer")
public class Customer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customerId", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "name", unique = true, nullable = false, length = 10)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
```

####advice for exception handling

We can advice methods in bo for dao exceptions so that all exception handling for database related stuff is done in one place.

***we can only advice beans that are in spring context. Since dao is in spring context we can advice the methods present in the dao***

```
package com.rest.example.util;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import com.rest.example.enums.AckValue;

@Component
@EnableAspectJAutoProxy
@Aspect
public class ExceptionHandler {
	
	@AfterThrowing(pointcut = "execution(* com.rest.example.customer.bo.*.*(..))", throwing = "e")
	public void handleBoException(final JoinPoint jp, final Exception e){
		
		Logger logger = LoggerFactory.getLogger(jp.getClass());
		logger.debug("Exception occured in "+ jp.getSourceLocation().toString() );
		
		ResponseHelper.throwWebApplicationException(Status.INTERNAL_SERVER_ERROR, 
				"Database Exception", "ErrorCode", AckValue.FAILURE.getValue(), 
				jp.getClass().getName(), "", MediaType.APPLICATION_JSON_TYPE);
		
	}
	
	

}
```
