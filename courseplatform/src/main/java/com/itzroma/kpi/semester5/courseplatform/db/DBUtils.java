package com.itzroma.kpi.semester5.courseplatform.db;

import java.util.logging.Logger;

public class DBUtils {

    private static final Logger log = Logger.getLogger(DBUtils.class.getName());

    private DBUtils() {
    }

    public static void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception ex) {
                log.severe(ex.getMessage());
            }
        }
    }
}
