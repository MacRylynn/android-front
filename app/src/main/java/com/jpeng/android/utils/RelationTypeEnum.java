package com.jpeng.android.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RelationTypeEnum
 * @Description
 * @Author: lijiao73
 * @Date: 2019/11/29 0029 下午 2:24
 */
public enum RelationTypeEnum {
    SELF("SELF", "自己"),
    PARENTS("PARENTS", "父母"),
    SPOUNSE("SPOUNSE", "配偶"),
    RELATIVE("RELATIVE", "亲戚"),
    CHILDREN("CHILDREN", "子女"),
    OTHERS("OTHERS", "其他"),
    ;
    private String code;
    private String desc;

    RelationTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    private static Map<String, RelationTypeEnum> map = new HashMap<String, RelationTypeEnum>();
    static {
        RelationTypeEnum[] values = values();
        for (int i = 0; i < values.length; i++) {
            RelationTypeEnum temp = values[i];
            map.put(temp.getCode(), temp);
        }
    }

    public static RelationTypeEnum getEnumByCode(String code){
        return map.get(code);
    }
}
