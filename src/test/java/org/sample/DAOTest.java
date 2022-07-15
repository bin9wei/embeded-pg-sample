package org.sample;

import io.zonky.test.db.postgres.embedded.FlywayPreparer;
import io.zonky.test.db.postgres.junit5.EmbeddedPostgresExtension;
import io.zonky.test.db.postgres.junit5.PreparedDbExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.*;

class DAOTest {
    @RegisterExtension
    public PreparedDbExtension db = EmbeddedPostgresExtension.preparedDatabase(FlywayPreparer.forClasspathLocation("db/flyway"));

    @Test
    void shouldGetCompanyNameById() {
        DAO underTest = new DAO(db.getTestDatabase());
        String actual = underTest.getCompanyNameById(1);
        assertEquals("Paul", actual);
    }
}