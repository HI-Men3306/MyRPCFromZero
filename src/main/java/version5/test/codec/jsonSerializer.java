package version5.test.codec;

import com.alibaba.fastjson.JSON;
import version5.common.request;
import version5.common.response;

public class jsonSerializer implements serializer{
    @Override
    public byte[] Serializer(Object message) {
        return JSON.toJSONBytes(message);
    }

    @Override
    public Object DeSerializer(byte[] bytes,int MessageType) {
        //根据不同的类型转化response
        if(MessageType == MESSAGE_TYPE_REQUEST){//request消息
            request req = JSON.parseObject(bytes, request.class);
            //对request中的参数进行二次转换
            Object[] params = req.getParams();
            Class<?>[] OriType = req.getParamsType();
            //创建新的object数组用来替换参数
            Object[] after = new Object[params.length];
            for(int i = 0;i < params.length;i++){
                Object cur = params[i];//获取当前的参数
                Class<?> curType = cur.getClass();
                Class<?> Ori = OriType[i];
                if(!curType.isAssignableFrom(Ori)){//当前参数和原参数不一致 进行二次转换
                    after[i] = JSON.toJavaObject((JSON) cur,Ori);
                }else{//一致 不需要转换
                    after[i] = cur;
                }
            }
            req.setParams(after);
            return req;

        }else if(MessageType == MESSAGE_TYPE_RESPONSE){//response消息
            response res = JSON.parseObject(bytes, response.class);
            Class<?> curType = res.getData().getClass();
            Class<?> OriginType = res.getDataType();
            if(!curType.isAssignableFrom(OriginType)){
                res.setData(JSON.toJavaObject((JSON) res.getData(),OriginType));
            }
            return res;
        }else{
            throw new IllegalArgumentException("暂时不支持此种类似");
        }
    }

    @Override
    public Integer getSerializerType() {
        return SERIALIZER_TYPE_JSON;
    }
}
