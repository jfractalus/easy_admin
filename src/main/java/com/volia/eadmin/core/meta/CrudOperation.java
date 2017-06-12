package com.volia.eadmin.core.meta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum CrudOperation {
    create("C"),
    read("R"),
    update("U"),
    delete("D"),
    visible("V");

    private String value;
    private static Map<String, CrudOperation> container = new HashMap();

    static {
        Arrays.stream(values()).forEach(crud -> container.put(crud.getValue(), crud));
    }

    CrudOperation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CrudOperation getByValue(String value){
        CrudOperation result = container.get(value);
        if(result == null){
            throw new UnsupportedOperationException("Incorrect crud operation");
        }
        return result;
    }
}
