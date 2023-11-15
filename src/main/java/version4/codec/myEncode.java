package version4.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import version4.common.RPCRequest;
import version4.common.RPCResponse;

//自定义的编码器（属于处理器的一种）  当数据沿着netty管道传输时，会先经过处理器的处理再进行传输
//而我们这里实现的处理器为 使用不同的自定义序列化方式将java对象序列化为字节流对象
public class myEncode extends MessageToByteEncoder {
    private Serializer serializer;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object message, ByteBuf byteBuf) throws Exception {
        //判断要序列化的消息类型 并写入2byte的消息类型
        if(message instanceof RPCRequest){
            byteBuf.writeShort(messageType.REQUEST.getCode());
        }else if(message instanceof RPCResponse){
            byteBuf.writeShort(messageType.RESPONSE.getCode());
        }else{
            throw new IllegalArgumentException("暂时不支持此种消息格式");
        }
        //写入2byte的序列化方式
        byteBuf.writeShort(serializer.getSerializerType());
        //获取序列化之后的字节流数组
        byte[] afterSerializer = this.serializer.serializer(message);
        //写入4byte序列化后的消息长度
        byteBuf.writeInt(afterSerializer.length);
        //写入序列化后的数据
        byteBuf.writeBytes(afterSerializer);
    }
}
