package ddss.catalog;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@Configuration
@PropertySource("classpath:application.properties")
public class DdssCatalogProps {

    @Value("${ddss.admin.username}")
    private String adminUsername;
    @Value("${ddss.admin.password}")
    private String adminPassword;

    @Value("${ddss.url.storage.available_megabytes}")
    private String availableMegabytesUrl;

    @Value("${ddss.storage.min_available_megabytes}")
    private long minAvailableMegabytes;
}
