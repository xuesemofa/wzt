package org.consume.com.heatattribute.model;

/**
 * @name 换热站属性集合model
 * @table heat_attribute_table
 */
public class AttributeModel {
    //    id
    private String uuid;
    //    属性名称
    private String names;
    //    顺序
    private Integer orders;

    public AttributeModel() {
        super();
    }

    public AttributeModel(String uuid, String names, Integer orders) {
        this.uuid = uuid;
        this.names = names;
        this.orders = orders;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "AttributeModel{" +
                "uuid='" + uuid + '\'' +
                ", names='" + names + '\'' +
                ", orders=" + orders +
                '}';
    }
}
