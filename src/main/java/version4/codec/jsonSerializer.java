package version4.codec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import version4.common.RPCRequest;
import version4.common.RPCResponse;

public class jsonSerializer implements Serializer{
    //序列化  java -> []
    @Override
    public byte[] serializer(Object obj) {
        byte[] bytes = JSON.toJSONBytes(obj);
        return bytes;
    }

    //反序列化 [] -> java
    @Override
    public Object deserializer(byte[] bytes, int mesType) {
        //因为使用json反序列化之后的对象中的参数类型是json类型的 需要将其转换为原类型
        Object data = null;
        if(mesType == messageType.REQUEST.getCode()){//需要反序列化的结果为request请求
            RPCRequest request = JSON.parseObject(bytes,RPCRequest.class);
            //将request中的类型为json的参数转换为原来的参数
            Object[] newParams = new Object[request.getParams().length];
            for (int i = 0;i < newParams.length;i++) {
                //获取对应位置参数原有的参数类型
                Class<?> curClazz = request.getParams()[i].getClass();
                Class<?> OriginalClazz = request.getParamsType()[i];
                //查看当前参数类型是否与原来的参数类型一致
                if(!OriginalClazz.isAssignableFrom(curClazz)){//不一致 将其从json类型转换为原类型
                    newParams[i] = JSON.toJavaObject((JSONObject) request.getParams()[i],OriginalClazz);
                }else{//一致  不进行转换
                    newParams[i] = request.getParams()[i];
                }
                data = request;
            }
        }else if(mesType == messageType.RESPONSE.getCode()){//需要反序列化的结果为response请求
            RPCResponse response = JSON.parseObject(bytes,RPCResponse.class);
            //检查并转换response中的data数据类型
            if(!response.getData().getClass().isAssignableFrom(response.getDataType())){
                //不一致 将json类型转换为原来的参数类型
                response.setData(JSON.toJavaObject((JSONObject) response.getData(),response.getDataType()));
            }
            data = response;
        }else{
            throw new IllegalArgumentException("暂时不支持此种数据类型");
        }
        return data;
    }

    @Override
    public int getSerializerType() {
        return JSONTOOL;
    }
}
