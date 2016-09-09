package cn.mycommons.tools.plugin.databindingconvert.model;

/**
 * KeyValue <br/>
 * Created by xiaqiulei on 2016-09-09.
 */
public class KeyValue {

    public final String key;

    public final String value;

    public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "KeyValue{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}