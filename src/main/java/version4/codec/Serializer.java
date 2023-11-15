package version4.codec;

public interface Serializer {
    //序列化  java对象 ->  字节数组
    byte[] serializer(Object obj);

    //反序列化  字节数组  -> java对象
    //根据消息类型的不同（request   response） 转换为不同的java对象
    Object deserializer(byte[] bytes, int messageType);

    //获取当前序列化类型（使用那种工具来进行序列化和反序列化）
    //0：java自带序列化工具  1:json工具
    int getSerializerType();

    static Serializer getSerializerByCode(int code) {
        if (code == serializerType.ObjectSerializer.getCode()) {
            return new objectSerializer();
        } else if (code == serializerType.JSONSerializer.getCode()) {
            return new jsonSerializer();
        } else {
            return null;
        }
    }
}
