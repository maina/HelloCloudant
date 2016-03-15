package io.example.ona.hellocloudant.io.example.ona.hellocloudant.services;

import java.util.List;

/**
 * A {@code ReplicationListener} that sets a latch when it's told the
 * replication has finished.
 */

public interface ConnectorCallback {
    void callback(String dname); // would be in any signature
    void listdbs(List<String> dbs);
}
