package org.sample;

import io.zonky.test.db.postgres.embedded.FlywayPreparer;
import io.zonky.test.db.postgres.junit5.EmbeddedPostgresExtension;
import io.zonky.test.db.postgres.junit5.PreparedDbExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlywayPreparerTest {
    @RegisterExtension
    public PreparedDbExtension db = EmbeddedPostgresExtension.preparedDatabase(FlywayPreparer.forClasspathLocation("db/flyway"));

    @Test
    public void testFlywayHistory() throws Exception {
        try (Connection c = db.getTestDatabase().getConnection();
             Statement s = c.createStatement()) {
            ResultSet rs = s.executeQuery("SELECT 1 FROM public.flyway_schema_history where version='2' and script='V2__create_company_data.sql'");
            rs.next();
            assertEquals(1, rs.getInt(1));
        }
    }

    @Test
    public void testTablesMade() throws Exception {
        try (Connection c = db.getTestDatabase().getConnection();
             Statement s = c.createStatement()) {
            ResultSet rs = s.executeQuery("SELECT 1 FROM information_schema.tables where table_name='company'");
            rs.next();
            assertEquals(1, rs.getInt(1));
        }
    }

    @Test
    public void testDataInserted() throws Exception {
        try (Connection c = db.getTestDatabase().getConnection();
             Statement s = c.createStatement()) {
            ResultSet rs = s.executeQuery("SELECT count(1) FROM company");
            rs.next();
            assertEquals(5, rs.getInt(1));
        }
    }
}
