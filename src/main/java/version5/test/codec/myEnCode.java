package version5.test.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import version5.common.request;
import version5.common.response;

//自定义netty处理器  对数据进行自定义序列化
@AllArgsConstructor
public class myEnCode extends MessageToByteEncoder {
    private serializer serializerTool;
    @Override
    protected void encode(ChannelHandlerContext ctx, Object input, ByteBuf byteBuf) throws Exception {

        //2 消息格式  2 序列化方式 4消息长度  消息实体

        //写入消息格式
        if(input instanceof request){
            byteBuf.writeShort(serializer.MESSAGE_TYPE_REQUEST);
        }else if(input instanceof response){
            byteBuf.writeShort(serializer.MESSAGE_TYPE_RESPONSE);
        }

        //获取序列化格式
        Integer serializerType = serializerTool.getSerializerType();
        byteBuf.writeShort(serializerType);

        //序列化数据
        byte[] serializer = serializerTool.Serializer(input);
        //获取序列化长度
        int length = serializer.length;

        //写入序列化长度
        byteBuf.writeInt(length);
        //写入消息实体
        byteBuf.writeBytes(serializer);
    }
}
