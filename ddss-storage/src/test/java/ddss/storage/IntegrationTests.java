package ddss.storage;

import org.flywaydb.test.FlywayTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(classes = DdssStorageApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
public abstract class IntegrationTests {

    private static final PostgreSQLContainer POSTGRES_CONTAINER;

    static {
        POSTGRES_CONTAINER = (PostgreSQLContainer) (new PostgreSQLContainer("postgres:14.2")
                .withUsername("testcontainersroot")
                .withPassword("testcontainersqwerty")
                .withReuse(true));
        POSTGRES_CONTAINER.start();
    }

    @Autowired
    public TestRestTemplate restTemplate;

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
    }
}