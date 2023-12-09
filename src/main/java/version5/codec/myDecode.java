package version5.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class myDecode extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {

        //读取消息类型
        short messageType = byteBuf.readShort();
        //读取序列化方式
        short serializerType = byteBuf.readShort();
        //读取序列化bit长度
        int len = byteBuf.readInt();
        //读取序列化数据
        byte[] res = new byte[len];
        byteBuf.readBytes(res);
        //获取反序列化工具
        serializer serializerTool = serializer.getInstanceByCode(serializerType);
        //反序列化数据
        Object message = serializerTool.DeSerializer(res, messageType);
        //将数据添加到list中
        list.add(message);
    }
}
