package version5.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import lombok.Data;
import version5.common.request;
import version5.common.response;

@AllArgsConstructor
@Data
public class myEncode extends MessageToByteEncoder {
    private serializer serializerTool;
    @Override
    protected void encode(ChannelHandlerContext ctx, Object target, ByteBuf byteBuf) throws Exception {

        //自定义消息协议格式： 2bit消息类型 2bit序列化格式 4bit消息长度 N bit序列化数据

        //写入序列化消息格式
        if(target instanceof request){//request请求
            byteBuf.writeShort(serializer.MESSAGE_TYPE_REQUEST);
        }else if(target instanceof response){//response响应
            byteBuf.writeShort(serializer.MESSAGE_TYPE_RESPONSE);
        }
        //写入序列化方式
        byteBuf.writeShort(serializerTool.getSerializerType());

        //获取序列化后的bit数据
        byte[] bytes = serializerTool.Serializer(target);

        //写入序列化数据长度
        byteBuf.writeInt(bytes.length);

        //写入序列化数据
        byteBuf.writeBytes(bytes);
    }
}
