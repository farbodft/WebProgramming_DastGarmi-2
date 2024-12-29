package com.example.demo.model;

public class Field {
    private long fieldId;
    private String name;
    private String label;
    private String type;
    private String defaultValue;

    // Default constructor
    public Field() {
    }

    // All-args constructor
    public Field(long fieldId, String name, String label, String type, String defaultValue) {
        this.fieldId = fieldId;
        this.name = name;
        this.label = label;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    // Getters and setters
    public long getFieldId() {
        return fieldId;
    }

    public void setFieldId(long fieldId) {
        this.fieldId = fieldId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
