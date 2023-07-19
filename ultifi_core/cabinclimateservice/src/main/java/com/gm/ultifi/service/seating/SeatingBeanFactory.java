package com.gm.ultifi.service.seating;

import java.util.HashMap;
import java.util.Map;

public class SeatingBeanFactory {
    private static class BeanFactorySingleton {
        private static final SeatingBeanFactory INSTANCE = new SeatingBeanFactory();
    }

    public static SeatingBeanFactory getInstance() {
        return SeatingBeanFactory.BeanFactorySingleton.INSTANCE;
    }

    // SeatPosition.SeatComponentPosition.percentage_position: 20
    // DriverSeat.
    public static Map<String, Object> beanMap = new HashMap<>();

}
