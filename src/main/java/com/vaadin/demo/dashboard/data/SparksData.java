package com.vaadin.demo.dashboard.data;

import java.lang.management.ManagementFactory;


public class SparksData {
    
    public static long availableProcessors() {
        return ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors();
    }
    
    public static double systemLoadAverage() {
        return ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage();
    }

    public static void main(String[] args) {
        
        System.out.println(availableProcessors());
        System.out.println(systemLoadAverage());
    }

}
