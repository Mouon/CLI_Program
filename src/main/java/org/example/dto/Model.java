package org.example.dto;

public class Model {
    private String targetUri; // 목표 뷰
    private Object attribute; // 담을 객체

    public Model(String targetUri, Object attribute) {
        this.targetUri = targetUri;
        this.attribute = attribute;
    }

    public String getUri() {
        return targetUri;
    }

    public Object getAttribute() {
        return attribute;
    }

}
