package version4.codec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import version4.common.RPCRequest;
import version4.common.RPCResponse;


/*public class jsonSerializer implements Serializer{
    //序列化  java -> []
    @Override
    public byte[] serializer(Object obj) {
        byte[] bytes = JSONObject.toJSONBytes(obj);
        return bytes;
    }

    //反序列化 [] -> java
    @Override
    public Object deserializer(byte[] bytes, int mesType) {
        System.out.println();
        Object obj = null;
        // 传输的消息分为request与response
        switch (mesType){
            case 0:
                //将字节数组转换为request请求
                RPCRequest request = JSON.parseObject(bytes, RPCRequest.class);
                // 修bug 参数为空 直接返回
                if(request.getParams() == null) return request;

                //打印参数转换前和转换后的变化
                //可以看到转换前的参数数组中存放的是json对象   {"sex":true,"id":100,"userName":"张三"}
                //转换之后 参数数组中存放的是java对象  User(id=100, userName=张三, sex=true)
                Object[] params = request.getParams();

                //存储request请求对象的参数信息
                Object[] objects = new Object[request.getParams().length];
                // 把json字串转化成对应的对象， fastjson可以读出基本数据类型，不用转化
                for(int i = 0; i < objects.length; i++){
                    //获取参数对应的类型
                    //判断该参数的类型是否和request中存储的参数类型匹配
                    Object param = request.getParams()[i];
                    Class<?> originalType = request.getParamsType()[i];
                    if (!originalType.isAssignableFrom(param.getClass())){
                        //匹配 将参数转化为对应类型的参数 再将其存储进参数数组中
                        //阻塞在这里了
                        System.out.println("不一致  原参数类型为" + originalType + "  参数的类型为" + param.getClass());
                        objects[i] = JSON.toJavaObject((JSONObject)param ,originalType);

                        System.out.println("不一致  改变前" + request.getParams()[i] + " 改变后" + objects[i]);
                    }else{
                        //不匹配 直接将参数放入数组中 不进行参数的转换
                        System.out.println("一致  原参数类型为" + originalType + "  参数的类型为" + param.getClass());
                        objects[i] = request.getParams()[i];
                    }
                }
                request.setParams(objects);

                //将转换之后的参数数组重新填回request中
                obj = request;
                break;
            case 1:
                //将字节数组转换为response响应
                RPCResponse response = JSON.parseObject(bytes, RPCResponse.class);
                Class<?> dataType = response.getDataType();
                //判断response中存储的data数据的数据类型是否和存储的数据类型一致
                if(! dataType.isAssignableFrom(response.getData().getClass())){
                    //一致 类型转换后再重新赋值
                    response.setData(JSONObject.toJavaObject((JSONObject) response.getData(),dataType));
                }
                obj = response;
                break;
            default:
                System.out.println("暂时不支持此种消息");
                throw new RuntimeException();
        }
        return obj;
    }

    @Override
    public int getSerializerType() {
        return serializerType.JSONSerializer.getCode();
    }
}*/


public class jsonSerializer implements Serializer{
    //序列化  java -> []
    @Override
    public byte[] serializer(Object obj) {
        byte[] bytes = JSONObject.toJSONBytes(obj);
        return bytes;
    }

    //反序列化 [] -> java
    @Override
    public Object deserializer(byte[] bytes, int mesType) {

        System.out.println("使用json反序列化");

        //因为使用json反序列化之后的对象中的参数类型是json类型的 需要将其转换为原类型
        if(mesType == messageType.REQUEST.getCode()){//需要反序列化的结果为request请求
            System.out.println("request");

            RPCRequest request = JSON.parseObject(bytes, RPCRequest.class);
            // 修bug 参数为空 直接返回
            if(request.getParams() == null) return request;
            //将request中的类型为json的参数转换为原来的参数
            Object[] newParams = new Object[request.getParams().length];
            for (int i = 0;i < newParams.length;i++) {
                //获取对应位置参数原有的参数类型
                Object curParam = request.getParams()[i];
                Class<?> curClazz = curParam.getClass();
                Class<?> OriginalClazz = request.getParamsType()[i];
                //查看当前参数类型是否与原来的参数类型一致
                if(!OriginalClazz.isAssignableFrom(curClazz)){//不一致 将其从json类型转换为原类型
                    System.out.println("不一致： 原类型" + OriginalClazz + "当前类型" + curClazz);
                    //为什么这里要用toJavaObject?
                    //因为这里的参数类型还是json类型的   json对象转java要使用这个
                    newParams[i] = JSON.toJavaObject((JSONObject) curParam,OriginalClazz);
                    System.out.println("不一致  改变前" + curParam + " 改变后" + newParams[i]);
                }else{//一致  不进行转换
                    System.out.println("一致： 原类型" + OriginalClazz + "当前类型" + curClazz);
                    newParams[i] = request.getParams()[i];
                    System.out.println("一致  改变前" + curParam + " 改变后" + newParams[i]);
                }
                //imp 需要重新写回参数进request中
                request.setParams(newParams);
            }
            return request;
        }else if(mesType == messageType.RESPONSE.getCode()){//需要反序列化的结果为response请求
            System.out.println("response");

            RPCResponse response = JSON.parseObject(bytes,RPCResponse.class);
            //检查并转换response中的data数据类型
            if(!response.getData().getClass().isAssignableFrom(response.getDataType())){
                //不一致 将json类型转换为原来的参数类型
                response.setData(JSONObject.toJavaObject((JSONObject) response.getData(),response.getDataType()));
            }
            return response;
        }else{
            throw new IllegalArgumentException("暂时不支持此种数据类型");
        }
    }

    @Override
    public int getSerializerType() {
        return serializerType.JSONSerializer.getCode();
    }
}
