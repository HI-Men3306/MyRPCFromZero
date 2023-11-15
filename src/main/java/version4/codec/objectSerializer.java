package version4.codec;

import java.io.*;

public class objectSerializer implements Serializer{
    @Override
    public byte[] serializer(Object obj) {
        //下面为使用java工具将java对象转换为字节流的固定套路
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public Object deserializer(byte[] bytes, int messageType) {
        //同样的以下也为使用java工具将字节流对象转换为java对象的固定流程
        Object obj = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public int getSerializerType() {
        return OBJECTTOOL;
    }
}
