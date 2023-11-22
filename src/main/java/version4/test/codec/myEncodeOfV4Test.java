package version4.test.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import version4.test.common.RequestOfV4Test;
import version4.test.common.ResponseOfV4Test;

@AllArgsConstructor
public class myEncodeOfV4Test extends MessageToByteEncoder {
    serializer serializerTool;
    @Override
    protected void encode(ChannelHandlerContext ctx, Object input, ByteBuf byteBuf) throws Exception {

        System.out.println("进入序列化处理器");

        //自定义的消息格式为 2bit的消息类型  2bit序列化方式  4bit的序列化消息长度  ？bit的序列化之后的数据类型

        //填入消息类型
        if(input instanceof RequestOfV4Test){
            byteBuf.writeShort(serializer.MESSAGE_TYPE_REQUEST);
        }else if(input instanceof ResponseOfV4Test){
            byteBuf.writeShort(serializer.MESSAGE_TYPE_RESPONSE);
        }else {
            throw new IllegalArgumentException("暂不支持此种消息格式");
        }

        //填入序列化方式
        byteBuf.writeShort(serializerTool.getSerializerType());


        //获取序列化之后的byte数组
        byte[] res = serializerTool.to_serializer(input);
        //填入序列化数据长度
        byteBuf.writeInt(res.length);

        System.out.println("序列化的数据长度为：" + res.length);

        //写入序列化的数据
        byteBuf.writeBytes(res);
    }
}
