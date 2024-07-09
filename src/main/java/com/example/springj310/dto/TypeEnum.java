package com.example.springj310.dto;

public enum TypeEnum {
    MALE("Юридическое лицо"),
    FEMALE("Физическое лицо");

    private String type;

    TypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static TypeEnum getType(String type){
        if(type==null) return null;
        for(TypeEnum typeEnum : TypeEnum.values()){
            if(typeEnum.type.equals(type)) return typeEnum;
        }
        return null;
    }
}
