# Spring Boot - Multiple JPA implementation
## Goal
Integrate two different Databases in one spring boot project.
## Planning
To keep the DBs separated we will create two packages `db1` and `db2`. Inside those packages we will place DAOs and entity objects.
```text
root
    db1 
        entity
            ProductEntity1.java
            ProductRepository1.java
        DB1Config.java
    db2 
        entity
            ProductEntity2.java
            ProductRepository2.java
        DB2Config.java
    Application.java
```
## DBConfig
First we will create a property for each database (you can also merge them to one file, but this way it is easier cleaner)

[db1-config.properties](src/main/resources/db1-config.properties):
```properties 

db1.datasource.jdbcUrl=jdbc:h2:mem:db1
db1.datasource.username=SA
db1.datasource.password=
db1.datasource.data=db1.sql
db1.hibernate.hbm2ddl.auto=create
```
The [db2-config.properties](src/main/resources/db2-config.properties) are the same as `db1-config.properties`but we replace each `db1` with `db2`.

Now let's create our DB1Config.java we will need to:
- define a datasource based on our properties
- create an EntityManager for the datasource
- create a TransactionManager for the EntityManager

```java
@Configuration
@PropertySource("classpath:db1-config.properties") // import created properties
@EnableJpaRepositories(
        basePackages = "com.vscoding.jpa.db1.entity", // define package path where our entities lies
        // define entityManagerFactoryRef/transactionManagerRef which will be used by DAOs inside the base package
        entityManagerFactoryRef = "db1EntityManagerFactory", 
        transactionManagerRef = "db1TransactionManager"
)
public class DB1Config {
    /*
         TODO:
            - define a datasource based on our properties
            - create an EntityManager for the datasource
            - create a TransactionManager for the EntityManager           
    */
}
```

### Datasource
To create a DataSource we can use the `prefix` mechanic from `@ConfigurationProperties`, this will read all properties with given prefix and inject their values in the bean.

```java
public class DB1Config {
    
  @Bean
  @Primary // We will mark the first DataSource as Primary, this makes this DataSource the default for all DAO's without specific datasource.
  @ConfigurationProperties(prefix = "db1.datasource")
  public DataSource db1DataSource() {
    return DataSourceBuilder.create().build();
  }
}
```
### EntityManager / TransactionManager
```java
public class DB1Config {
  // DataSource
  
  
  @Bean
  public FactoryBean<EntityManagerFactory> db1EntityManagerFactory(
          @Qualifier("db1DataSource") DataSource db1DataSource, //Inject prior created DataSource
          Environment env){
      
    var em = new LocalContainerEntityManagerFactoryBean();
    var va = new HibernateJpaVendorAdapter();
    var properties = new HashMap<String,Object>();

    properties.put("hibernate.hbm2ddl.auto", env.getProperty("db1.hibernate.hbm2ddl.auto")); //added, so we can select creation strategy for each DB
    em.setDataSource(db1DataSource);
    em.setPackagesToScan("com.vscoding.jpa.db1.entity"); //Packages of our entities
    em.setJpaVendorAdapter(va);
    em.setJpaPropertyMap(properties);

    return em;
  }

  @Bean
  public PlatformTransactionManager db1TransactionManager(@Qualifier("db1EntityManagerFactory") FactoryBean<EntityManagerFactory> db1EntityManagerFactory) throws Exception {
    var tm = new JpaTransactionManager();

    tm.setEntityManagerFactory(db1EntityManagerFactory.getObject());

    return tm;
  }
}
```

We can copy now the config and create `DB2Config.java` we need to:
- rename all `db1` beans to `db2`
- remove `@Primary` annotation from the datasource

After this is done we can create entities and repositories for each database separately.

## Custom repository 

Let's have a look, how we can create a custom repository calls with multi-database approach.

```java
@Repository
@Transactional(transactionManager = "db2TransactionManager") // we have to specify the exact transactionManager we want to use
public class CustomRepositoryImpl implements CustomRepository {
  private final EntityManager entityManager;

  public CustomRepositoryImpl(
          @Qualifier("db2EntityManagerFactory") EntityManager entityManager // also specify the exact entityManager
  ) {
    this.entityManager = entityManager;
  }

  public List<ProductEntity2> customQuery() {
    var query = entityManager.createQuery("SELECT p FROM ProductEntity2 p WHERE p.name='special'", ProductEntity2.class);

    return query.getResultList();
  }
}
```

If you want to embed your custom repo in your spring-data (interface) repo, you can do it by extending form the interface of your custom repo.

```java
public interface ProductRepository2 extends CrudRepository<ProductEntity2,Integer>, CustomRepository {
}
```
**IMPORTANT**: the implementation have to be called <interfaceName>Impl if you use some other name (f.e. `CustomRepositoryOracle.java`), you will get an error.

[Spring Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.custom-implementations)
# Conclusion
We have learned, how to configure two (or more) different repositories in one spring-boot project and how to extend the default implementation with custom hibernate queries.
