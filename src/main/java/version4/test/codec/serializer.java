package version4.test.codec;

public interface serializer {
    public final static int SERIALIZER_TYPE_OBJECT = 0;//序列化方式为原生java
    public final static int SERIALIZER_TYPE_JSON = 1;//序列化方式为json
    public final static int MESSAGE_TYPE_REQUEST = 0;//消息类型为request
    public final static int MESSAGE_TYPE_RESPONSE = 1;//消息类型为response

    //序列化 java->Json
    byte[] to_serializer(Object obj);

    //反序列化  json->java   根据不同的消息类型进行转换
    Object deserializer(byte[] bytes,int messageType);

    //获取当前序列化方式
    Integer getSerializerType();

    //根据不同的序列化方式 获取不同的序列化工具
    static serializer getSerializerByCode(int code){
        if(code == SERIALIZER_TYPE_OBJECT){
            return new objectSerializer();
        }else if(code == SERIALIZER_TYPE_JSON){
            return new jsonSerializer();
        }
        throw new RuntimeException("暂不支持该种序列化方式");
    }

}
