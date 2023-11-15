package version4.codec;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum serializerType {
    ObjectSerializer(0), JSONSerializer(1);
    private int code;
    public int getCode() {
        return code;
    }
}
