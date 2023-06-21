package com.gm.ultifi.base.monitor;

/**
 * TODO: 2023/4/26 框架代码抽离
 */
public interface ConnectionManager {
    void connect();

    void init();

    void stop();
}