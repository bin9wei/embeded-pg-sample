package org.sample;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class EmbeddedPostgresTest {

    @Test
    public void testEmbeddedPg() throws Exception
    {
        try (EmbeddedPostgres pg = EmbeddedPostgres.start();
             Connection c = pg.getPostgresDatabase().getConnection()) {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT 1");
            assertTrue(rs.next());
            assertEquals(1, rs.getInt(1));
            assertFalse(rs.next());
        }
    }
}
