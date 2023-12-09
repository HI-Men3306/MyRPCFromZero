package version5.codec;

public interface serializer {
    public final static int SERIALIZER_TYPE_OBJECT = 0;//序列化方式为原生java
    public final static int SERIALIZER_TYPE_JSON = 1;//序列化方式为json
    public final static int MESSAGE_TYPE_REQUEST = 0;//消息类型为request
    public final static int MESSAGE_TYPE_RESPONSE = 1;//消息类型为response

    //序列化
    byte[] Serializer(Object target);
    //反序列化
    Object DeSerializer(byte[] bytes,int messageType);
    //获取序列化方式
    int getSerializerType();

    //获取序列化对象
    public static serializer getInstanceByCode(int code){
        if(code == SERIALIZER_TYPE_OBJECT){
            return new objectSerializer();
        }else if(code == SERIALIZER_TYPE_JSON){
            return new jsonSerializer();
        }else{
            throw new IllegalArgumentException("暂不支持当前序列化方式！");
        }
    }

}
