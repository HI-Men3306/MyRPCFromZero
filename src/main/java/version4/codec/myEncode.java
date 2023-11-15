package version4.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import version4.common.RPCRequest;
import version4.common.RPCResponse;

//自定义的编码器（属于处理器的一种）  当数据沿着netty管道传输时，会先经过处理器的处理再进行传输
//而我们这里实现的处理器为 使用不同的自定义序列化方式将java对象序列化为字节流对象
@AllArgsConstructor
public class myEncode extends MessageToByteEncoder {
    private Serializer serializer;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object message, ByteBuf byteBuf) throws Exception {

        System.out.println();
        System.out.println("---------------------------------进入序列化处理器---------------------------------");
        System.out.println();
        System.out.println("序列化前的数据为" + message.toString());

        //判断要序列化的消息类型 并写入2byte的消息类型
        if(message instanceof RPCRequest){//request  ==>  0
            byteBuf.writeShort(messageType.REQUEST.getCode());
            System.out.println("序列化的消息类型为request");
        }else if(message instanceof RPCResponse){//response  ==>  1
            byteBuf.writeShort(messageType.RESPONSE.getCode());
            System.out.println("序列化的消息类型为response");
        }else{
            throw new IllegalArgumentException("暂时不支持此种消息格式");
        }
        //写入2byte的序列化方式
        byteBuf.writeShort(serializer.getSerializerType());

        System.out.println("序列化的工具为" + serializer.getSerializerType());

        //获取序列化之后的字节流数组
        byte[] afterSerializer = serializer.serializer(message);
        //写入4byte序列化后的消息长度
        byteBuf.writeInt(afterSerializer.length);

        System.out.println("序列化之后的数据长度为" + afterSerializer.length);

        //写入序列化后的数据
        byteBuf.writeBytes(afterSerializer);

    }
}
