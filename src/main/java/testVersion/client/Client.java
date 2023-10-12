package testVersion.client;

import testVersion.common.Student;
import testVersion.service.studentService;

public class Client {
    public static void main(String[] args) {
        clientProxy clientProxy = new clientProxy("127.0.0.1", 8899);
        //获取代理对象
        studentService service = (studentService) clientProxy.getProxy(studentService.class);
        service.eat("香蕉");
        service.insertData(Student.builder().id(12).username("小黄").sex(true).build());
    }
}
