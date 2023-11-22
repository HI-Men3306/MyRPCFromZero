package version4.test.codec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import version4.test.common.RequestOfV4Test;
import version4.test.common.ResponseOfV4Test;

public class jsonSerializer implements serializer{
    @Override
    public byte[] to_serializer(Object obj) {
        System.out.println("使用json进行序列化");
        byte[] bytes = JSONObject.toJSONBytes(obj);
        System.out.println("序列化之后的byte长度为: " + bytes.length);
        return bytes;
    }

    @Override
    public Object deserializer(byte[] bytes, int messageType) {
        System.out.println("使用json进行反序列化");
        System.out.println("反序列化前byte的长度为: " + bytes.length);
        //根据不同的消息类型转换为对应的消息
        if(messageType == MESSAGE_TYPE_REQUEST){//request


            //jsonByte转换为request
            RequestOfV4Test request = JSON.parseObject(bytes, RequestOfV4Test.class);

            System.out.println("request   " + request);

            //对request中的参数进行二次转换  因为使用fastjson对 对象类型进行序列化和反序列化 对象中的属性类型在反序列化之后还是json类型  所以需要进行二次类型转换
            Object[] params = request.getParams();//获取参数数组
            Object[] afterParams = new Object[params.length];//存放转换类型之后的参数
            for(int i = 0;i < params.length;i++){
                Object curParam = params[i];//当前参数
                Class<?> curClass = curParam.getClass();//当前参数的数据类型
                Class originClass = request.getParamsType()[i];//参数的原数据类型

                if(!curClass.isAssignableFrom(originClass)){//原参和当前的参数不同 强转为原参
                    afterParams[i] = JSON.toJavaObject((JSONObject) curParam,originClass);
                }else{//当前参数和原参相同
                    afterParams[i] = curParam;
                }
            }
            //将转换之后的参数重新赋值给request
            request.setParams(afterParams);
            System.out.println(request);
            return request;

        } else if (messageType == MESSAGE_TYPE_RESPONSE) {//response
            //jsonByte 转换为 response
            ResponseOfV4Test response = JSON.parseObject(bytes,ResponseOfV4Test.class);
            System.out.println("response   " + response);
            //对respond中的data属性进行类型转换
            Object curData = response.getData();
            Class<?> originDataType = response.getDataType();
            Class<?> curClass = curData.getClass();
            if(!curClass.isAssignableFrom(originDataType)){//当前data类型和原类型不一致
                response.setData(JSON.toJavaObject((JSON) curData,originDataType));
            }
            System.out.println(response);
            return response;
        }else{
            throw new RuntimeException("暂不支持当前类型");
        }
    }

    @Override
    public Integer getSerializerType() {
        return SERIALIZER_TYPE_JSON;
    }
}
