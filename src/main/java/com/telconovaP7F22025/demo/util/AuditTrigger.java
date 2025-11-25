package com.telconovaP7F22025.demo.util;

import org.h2.api.Trigger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuditTrigger implements Trigger {

    @Override
    public void init(Connection conn, String schemaName, String triggerName, String tableName, boolean before, int type) {
        // Inicialización no requerida
    }

    @Override
    public void fire(Connection conn, Object[] oldRow, Object[] newRow) throws SQLException {
        // newRow contiene las columnas en el orden de creación de la tabla work_orders
        // Índices aproximados: 0:id, 1:name, 2:phone, 3:tipo, 4:prio, 5:estado
        
        if (oldRow != null && newRow != null) {
            String oldState = (String) oldRow[5]; // Columna estado
            String newState = (String) newRow[5]; // Columna estado
            
            // Si el estado cambió, guardamos auditoría
            if (!oldState.equals(newState)) {
                try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO auditoria_ordenes (id_orden, estado_anterior, estado_nuevo) VALUES (?, ?, ?)")) {
                    stmt.setLong(1, (Long) newRow[0]); // id_order
                    stmt.setString(2, oldState);
                    stmt.setString(3, newState);
                    stmt.executeUpdate();
                }
            }
        }
    }

    @Override
    public void close() {}
    @Override
    public void remove() {}
}
