package ddss.catalog;

import ddss.catalog.domain.CatalogRecord;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CatalogIntegrationTests extends IntegrationTests {

    @Autowired
    private DdssCatalogTestProps tprops;

    @Test
    @FlywayTest
    public void create_record_by_id_with_status_created() {
        // arrange
        String testUrl = tprops.getUrlRecord() + tprops.getUrlCreate();
        CatalogRecord record = new CatalogRecord(
                tprops.getRecIdForCreate(), tprops.getRecAboutForCreate(), tprops.getRecProtoSchemeForCreate(),
                LocalDateTime.now());
        HttpEntity<CatalogRecord> request = new HttpEntity<>(record, new HttpHeaders());

        // act
        ResponseEntity<CatalogRecord> response = restTemplate
                .withBasicAuth(tprops.getUsername(), tprops.getPassword())
                .exchange(testUrl, HttpMethod.POST, request, CatalogRecord.class);
        CatalogRecord result = response.getBody();

        // assert
        assert result != null;
        assertEquals(tprops.getRecIdForCreate(), result.getId());
        assertEquals(tprops.getRecAboutForCreate(), result.getAbout());
        assertEquals(tprops.getRecProtoSchemeForCreate(), result.getProtoScheme());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @FlywayTest
    public void get_record_by_id_with_status_ok() {
        // arrange
        String testUrl = tprops.getUrlRecord() + "/" + tprops.getRecId();
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());

        // act
        ResponseEntity<CatalogRecord> response = restTemplate
                .withBasicAuth(tprops.getUsername(), tprops.getPassword())
                .exchange(testUrl, HttpMethod.GET, request, CatalogRecord.class);
        CatalogRecord result = response.getBody();

        // assert
        assert result != null;
        assertEquals(tprops.getRecId(), result.getId());
        assertEquals(tprops.getRecAbout(), result.getAbout());
        assertEquals(tprops.getRecProtoScheme(), result.getProtoScheme());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @FlywayTest
    public void get_record_by_id_with_status_not_found() {
        // arrange
        String testUrl = tprops.getUrlRecord() + "/" + tprops.getRecIdForNotFound();
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());

        // act
        ResponseEntity<CatalogRecord> response = restTemplate
                .withBasicAuth(tprops.getUsername(), tprops.getPassword())
                .exchange(testUrl, HttpMethod.GET, request, CatalogRecord.class);

        // assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @FlywayTest
    public void delete_record_with_status_forbidden() {
        // arrange
        String testUrl = tprops.getUrlRecord() + "/" + tprops.getRecId();
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());

        // act
        ResponseEntity<CatalogRecord> response = restTemplate
                .withBasicAuth(tprops.getUsername(), tprops.getPassword())
                .exchange(testUrl, HttpMethod.DELETE, request, CatalogRecord.class);

        // assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @FlywayTest
    public void delete_record_with_status_no_content() {
        // arrange
        String testUrl = tprops.getUrlRecord() + "/" + tprops.getRecIdForForbidden();
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());

        // act
        ResponseEntity<CatalogRecord> response = restTemplate
                .withBasicAuth(tprops.getUsername(), tprops.getPassword())
                .exchange(testUrl, HttpMethod.DELETE, request, CatalogRecord.class);

        // assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @FlywayTest
    public void delete_record_with_status_not_found() {
        // arrange
        String testUrl = tprops.getUrlRecord() + "/" + tprops.getRecIdForNotFound();
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());

        // act
        ResponseEntity<CatalogRecord> response = restTemplate
                .withBasicAuth(tprops.getUsername(), tprops.getPassword())
                .exchange(testUrl, HttpMethod.DELETE, request, CatalogRecord.class);

        // assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
