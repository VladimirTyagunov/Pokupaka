package com.pokupaka.backend.data.entity;


public enum Status {

    NOT_STARTED(0,"Not started"),
    IN_PROGRESS(1,"In Progress"),
    COMPLETED(2,"Completed");

    private int id;
    private String value;

    Status(int id,String value) {
        this.id = id;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Status findByStrValue(String strValue){
            for(Status st : values()){
                if(st.getValue().equals(strValue)){
                    return st;
                }
            }
            return null;
    }

}
