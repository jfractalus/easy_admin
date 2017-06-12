package com.volia.eadmin;

import com.volia.eadmin.core.meta.ColumnInfo;
import com.volia.eadmin.core.meta.ViewTypeField;
import org.junit.Test;

public class ViewMetaInfoTest {

    @Test
    public void viewMetaInfoToJson(){
        /*
        Gson gson = new Gson();
        ViewMetaInfo info = new ViewMetaInfo();
        info.getAvailableOperation().add(CrudOperation.create);
        info.getFields().add(buildFieldInfo("code", "Code column", "PSE0222", TypeField.TEXT));
        info.getFields().add(buildFieldInfo("value", "Value column", "Code description", TypeField.TEXT));

        String json = gson.toJson(info);
        System.out.println(json);
        */
    }

    private ColumnInfo buildFieldInfo(String nativeName, String name, Object value, ViewTypeField viewTypeField) {
        return ColumnInfo.builder()
                .nativeName(nativeName)
                .name(name)
                .length(10)
                .nullable(false)
                .type(viewTypeField.name())
                .build();
    }


    /*
      JSON example:

        {
            "title":"Some entity",
            "availableOperation":["create"],
            "fields":[
                 {
                    "name":"Code column",
                    "nullable":false,
                    "unique":false,
                    "richText":false,
                    "length":10,
                    "type":"TEXT",
                    "nativeName":"code",
                    "value":"PSE0222"
                 },
                 {
                    "name":"Value column",
                    "nullable":false,
                    "unique":false,
                    "richText":false,
                    "length":10,
                    "type":"TEXT",
                    "nativeName":"value",
                    "value":"Code description"
                 }
            ],
            "requestMappingPrefix":"message"
        }
    */
}
