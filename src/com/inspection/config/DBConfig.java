package com.inspection.config;

public final class DBConfig {
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/inspection_db?useSSL=false&serverTimezone=UTC";
    public static final String DB_USER = "inspection_user";
    public static final String DB_PASSWORD = "inspection_password";

    public static final String ADMIN_USER = "inspection_admin";
    public static final String ADMIN_PASSWORD = "admin_password";

    private DBConfig() {
    }
}
