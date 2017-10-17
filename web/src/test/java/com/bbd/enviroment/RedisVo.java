package com.bbd.enviroment;

/**
 * @author Liuweibo
 * @version Id: RedisVo.java, v0.1 2017/10/17 Liuweibo Exp $$
 */
public class RedisVo {

    private String hostName;

    private boolean usePool;

    private Integer database;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public boolean isUsePool() {
        return usePool;
    }

    public void setUsePool(boolean usePool) {
        this.usePool = usePool;
    }

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }

    @Override
    public String toString() {
        return "RedisVo{" +
                "hostName='" + hostName + '\'' +
                ", usePool=" + usePool +
                ", database=" + database +
                '}';
    }
}
    
    