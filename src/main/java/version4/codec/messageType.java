package version4.codec;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum messageType {
    REQUEST(0),RESPONSE(1);
    private int code;
    public int getCode() {
        return code;
    }
}
