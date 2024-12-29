package com.example.demo.model;

import java.util.List;

public class Form {
    private Long id;
    private String name;
    private boolean published;
    private List<Field> fields;

    // Default constructor
    public Form() {
    }

    // All-args constructor
    public Form(Long id, String name, boolean published, List<Field> fields) {
        this.id = id;
        this.name = name;
        this.published = published;
        this.fields = fields;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
