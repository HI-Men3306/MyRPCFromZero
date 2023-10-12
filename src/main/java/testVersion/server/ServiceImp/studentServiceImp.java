package testVersion.server.ServiceImp;

import testVersion.common.Student;
import testVersion.service.studentService;

public class studentServiceImp implements studentService {
    @Override
    public void eat(String name) {
        System.out.println("学生吃了" + name);
    }

    @Override
    public void insertData(Student student) {
        System.out.println("插入成功数据");
    }
}
