package version5.test.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class myDecode extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {

        //获取消息格式
        short messageType = byteBuf.readShort();
        //获取序列化方式
        short serializerType = byteBuf.readShort();
        //获取序列化消息长度
        int len = byteBuf.readInt();
        //创建接收序列化消息的byte数组
        byte[] bytes = new byte[len];
        byteBuf.readBytes(bytes);
        //根据序列化方式 获取序列化工具
        serializer tool = serializer.getSerializerToolByCode(serializerType);
        //反序列化byte数组
        Object res = tool.DeSerializer(bytes, messageType);
        //将结果添加进list中
        list.add(res);
    }
}
