package version4.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

//自定义的编码器（属于处理器的一种）  当数据沿着netty管道传输时，会先经过处理器的处理再进行传输
//而我们这里实现的处理器为 使用不同的自定义反序列化方式将字节流数组转换为java对象
public class myDecode extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //读取消息类型
        short mesType = byteBuf.readShort();
        //判断消息类型是否合法
        if(mesType != messageType.REQUEST.getCode() && mesType != messageType.RESPONSE.getCode()){
            throw new IllegalArgumentException("暂不支持此种消息类型");
        }
        //读取序列化方式
        short serializerType = byteBuf.readShort();
        //读取数据长度
        int len = byteBuf.readInt();
        //读取数据本体
        byte[] bytes = new byte[len];
        byteBuf.readBytes(bytes);
        //使用对应的序列化工具 将字节流数组反序列化为java对象
        Serializer serializerTool = Serializer.getSerializerByCode(serializerType);
        Object result = serializerTool.deserializer(bytes,mesType);
        //将反序列化之后的结果添加到存储结果的list中
        list.add(result);
    }
}
