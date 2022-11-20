package com.example.kafkatrackingvehicle.constant;

public final class Constant {
    public static final String TOPIC = "vehicle_tracking_01";
    public static final String GROUP_ID = "vehicle_tracking_group_id";
    public static final String BOOTSTRAP_SERVERS = "localhost:9092";
    public static final int MAX_NO_MESSAGE_FOUND_COUNT = 100;

    private Constant() {
    }
}
