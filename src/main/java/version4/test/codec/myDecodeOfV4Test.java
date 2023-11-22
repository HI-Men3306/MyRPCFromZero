package version4.test.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class myDecodeOfV4Test extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {

        System.out.println("进入反序列化处理器");

        //读取消息类型
        short messageType = byteBuf.readShort();
        if(messageType != serializer.MESSAGE_TYPE_REQUEST && messageType != serializer.MESSAGE_TYPE_RESPONSE){
            throw new IllegalArgumentException("暂不支持当前消息类型");
        }
        //读取序列化方式
        short serializerType = byteBuf.readShort();
        //读取序列化长度
        int dataLength = byteBuf.readInt();
        //读取序列化的数据
        byte[] res = new byte[dataLength];//存放读取结果
        byteBuf.readBytes(res);

        //根据序列化所使用的方式 进行反序列化
        serializer deserializerTool = serializer.getSerializerByCode(serializerType);
        //反序列化byte数组 获得对应的java对象
        Object resJavaObject = deserializerTool.deserializer(res, messageType);
        //将反序列化的java对象添加到list中 形成管道中传输的数据
        list.add(resJavaObject);
    }
}
