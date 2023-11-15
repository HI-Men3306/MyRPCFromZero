package version4.client;

import com.alibaba.fastjson.JSON;

public class test {
    public static void main(String[] args) throws NoSuchMethodException {
        /*User user = User.builder().username("张三").id(100).sex('男').build();
        userService service = new userServiceImp();
        Method insertUserMethod = service.getClass().getMethod("selectUserById", Integer.class);
        //Method insertUserMethod = service.getClass().getMethod("selectBlog", Integer.class);

        RPCRequest request = RPCRequest.builder()
                .interfaceName(insertUserMethod.getDeclaringClass().getName())
                .methodName(insertUserMethod.getName())
                .paramsType(insertUserMethod.getParameterTypes())
                // 注意：
                // 调用不同的方法 传入的参数也要对应起来
                .params(new Object[]{12})
                .build();

        jsonSerializer json = new jsonSerializer();
        byte[] result = json.serializer(request);
        for (byte b : result) {
            System.out.print(b);
        }
        System.out.println();
        System.out.println("序列化结束");
        Object deserializer = json.deserializer(result, 0);
        System.out.println(deserializer);
        System.out.println("结束");*/

        Integer a = 1;
        byte[] bytes = JSON.toJSONBytes(a);
        Integer res = JSON.parseObject(bytes, a.getClass());
        System.out.println(res.getClass() + " " + a.getClass());

    }
}
