package version5.codec;

import com.alibaba.fastjson.JSON;
import version5.common.request;
import version5.common.response;

public class jsonSerializer implements serializer{
    @Override
    public byte[] Serializer(Object target) {
        byte[] bytes = JSON.toJSONBytes(target);
        return bytes;
    }

    @Override
    public Object DeSerializer(byte[] bytes, int messageType) {
        if(messageType == MESSAGE_TYPE_REQUEST){//解析为request消息
            //转换为request消息
            request Request = JSON.parseObject(bytes,request.class);
            //对request中的params重新进行类型转换
            Object[] params = Request.getParams();
            Class<?>[] OriParamsType = Request.getParamsType();
            Object[] newParams = new Object[params.length];
            for(int i = 0;i < params.length;i++){
                //根据原参数类型 对反序列化之后的参数 进行类型转换
                Object curParam = params[i];//获取当前参数
                Class<?> curParamType = curParam.getClass();//获取当前参数类型
                Class<?> OriginParamType = OriParamsType[i];//获取原参数类型
                if(!curParamType.isAssignableFrom(OriginParamType)){//当前参数类型和原参数类型不一致 进行转换
                    newParams[i] = JSON.toJavaObject((JSON) curParam,OriginParamType);
                }else{//一致 不需要转换
                    newParams[i] = curParam;
                }
            }
            //对request的params重新进行赋值
            Request.setParams(newParams);
            //返回反序列化结果
            return Request;
        }else if(messageType == MESSAGE_TYPE_RESPONSE){//解析为response消息
            //转换为response消息
            response Response = JSON.parseObject(bytes, response.class);
            //对response中的data数据进行转换
            if(!Response.getData().getClass().isAssignableFrom(Response.getDataType())){//不一致
                Response.setData(JSON.toJavaObject((JSON) Response.getData(),Response.getDataType()));
            }
            return Response;
        }else{
            throw new IllegalArgumentException("暂不支持当前消息类型!");
        }
    }

    @Override
    public int getSerializerType() {
        return SERIALIZER_TYPE_JSON;
    }
}
