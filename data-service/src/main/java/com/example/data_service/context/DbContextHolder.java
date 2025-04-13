package com.example.data_service.context;

public class DbContextHolder {
    private static final ThreadLocal<DBType> context = new ThreadLocal<>();

    public static void set(DBType dbType) {
        context.set(dbType);
    }

    public static DBType get() {
        return context.get() != null ? context.get() : DBType.PRIMARY;
    }

    public static void clear() {
        context.remove();
    }
}
