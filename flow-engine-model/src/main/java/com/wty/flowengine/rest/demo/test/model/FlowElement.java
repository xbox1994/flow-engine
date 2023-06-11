package com.wty.flowengine.rest.demo.test.model;

public abstract class FlowElement {

    /**
     * identifier
     */
    protected String id;

    /**
     * 名称
     */
    protected String name;

    protected String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "FlowElement{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
