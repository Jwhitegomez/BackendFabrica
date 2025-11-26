package com.telconovaP7F22025.demo.util;

import org.h2.api.Trigger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuditTriggerTest {

    private AuditTrigger trigger;

    @BeforeEach
    void setup() {
        trigger = new AuditTrigger();
    }

    @Test
    void testNoStateChange_NoInsertPerformed() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);

        when(conn.prepareStatement(anyString())).thenReturn(stmt);

        Object[] oldRow = {1L, "Juan", "123", "Fibra", "ALTA", "PENDIENTE"};
        Object[] newRow = {1L, "Juan", "123", "Fibra", "ALTA", "PENDIENTE"}; // mismo estado

        trigger.fire(conn, oldRow, newRow);

        // Nunca debe intentar insertar
        verify(conn, never()).prepareStatement(anyString());
    }

    @Test
    void testStateChange_InsertsAuditRecord() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);

        when(conn.prepareStatement(anyString())).thenReturn(stmt);

        Object[] oldRow = {1L, "Juan", "123", "Fibra", "ALTA", "PENDIENTE"};
        Object[] newRow = {1L, "Juan", "123", "Fibra", "ALTA", "FINALIZADA"}; // estado cambió

        trigger.fire(conn, oldRow, newRow);

        verify(conn).prepareStatement("INSERT INTO auditoria_ordenes (id_orden, estado_anterior, estado_nuevo) VALUES (?, ?, ?)");

        verify(stmt).setLong(1, 1L);
        verify(stmt).setString(2, "PENDIENTE");
        verify(stmt).setString(3, "FINALIZADA");
        verify(stmt).executeUpdate();
    }

    @Test
    void testNullRows_NoAction() throws Exception {
        Connection conn = mock(Connection.class);

        trigger.fire(conn, null, null);

        verify(conn, never()).prepareStatement(anyString());
    }

    @Test
    void testEmptyMethods() throws SQLException {
        trigger.init(null, null, null, null, false, Trigger.INSERT);
        trigger.close();
        trigger.remove();

        // No exception expected
        assertTrue(true);
    }
}
