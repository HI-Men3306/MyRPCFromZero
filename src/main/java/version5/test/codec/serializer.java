package version5.test.codec;

public interface serializer {
    public static final int SERIALIZER_TYPE_OBJECT = 0;
    public static final int SERIALIZER_TYPE_JSON = 1;
    public static final int MESSAGE_TYPE_REQUEST = 0;
    public static final int MESSAGE_TYPE_RESPONSE = 1;
    byte[] Serializer(Object message);
    Object DeSerializer(byte[] bytes,int MessageType);
    Integer getSerializerType();

    //根据序列化方式 获取序列化对象
    public static serializer getSerializerToolByCode(int code){
        if(code == SERIALIZER_TYPE_OBJECT){
            return new objectSerializer();
        }else if(code == SERIALIZER_TYPE_JSON){
            return new jsonSerializer();
        }else{
            throw new IllegalArgumentException("暂不支持此种序列化方式");
        }
    }

}

