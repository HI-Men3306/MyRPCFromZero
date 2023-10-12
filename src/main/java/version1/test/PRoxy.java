package version1.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class PRoxy {
    public static void main(String[] args) {
        Student student = (Student) Proxy.newProxyInstance(
                Student.class.getClassLoader(),
                new Class[]{Student.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("调用的方法是" + method.getName());
                        if(method.getName().equals("Eat")){
                            System.out.println("吃了" + args[0]);
                        }else if (method.getName().equals("Write")){
                            System.out.println("写作为" + args[0]);
                        }
                        return null;
                    }
                }
        );
        student.Eat("小红包");
        student.Eat("猪头");
        student.Eat("饺子");
        student.Write("我的区长父亲");
    }
    interface Student{
        void Eat(String name);
        void Write(String name);
    }
}
