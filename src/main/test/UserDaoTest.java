import com.service.base;

import java.util.ArrayList;
import java.util.List;

public class UserDaoTest {
    public static void main(String[] args){
        base test = new base();
        String string = "10001+10002+10003+10004+10005";
        String string1 = "10001+10004+10005";
        List<Long> read = new ArrayList<Long>();
        List<Long> write = new ArrayList<Long>();
        read = test.stringToLongList(string);
        write = test.stringToLongList(string1);
        List<Long> readOnly = test.findReadOnly(read,write);
        for(int i=0;i<readOnly.size();i++){
            System.out.println(readOnly.get(i));
        }
        System.out.println(readOnly.size());
        //System.out.println(test.isLongBelongToList((long)10001,list));

        /*String s1 = test.longListToString(list);
        System.out.println(s1);*/
    }
}
/*

import com.dao.SpaceDao;
import com.dao.UserDao;
import com.entities.UserPO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.InputStream;
import java.util.List;

public class UserDaoTest {
    private static Logger logger = Logger.getLogger(UserDaoTest.class);

    public static void main(String[] args) {
        //testMybatis();
        testSpringMybatis();
    }

    private static void testSpringMybatis(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-mybatis.xml");
        SqlSessionFactory factory = (SqlSessionFactory) context.getBean("sqlSessionFactory");
        //获得会话对象
        SqlSession session = factory.openSession(true);
        try {
            //通过MyBatis实现接口UserDAO，返回实例
            UserDao userDao = session.getMapper(UserDao.class);
            List<UserPO> userPOS = userDao.getAllUsers();
            logger.info(userPOS);
            System.out.println(userPOS.get(0).getName());
        } finally {
            session.close();
        }

    }

    */
/*private static void testMybatis(){
        System.out.println("hi");
        // 获得Mybatis配置文件流
        InputStream config = UserDaoTest.class.getClassLoader().getResourceAsStream("mybatis/mybatis.xml");
        // 创建sql会话工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(config);
        //获得会话对象
        SqlSession session = factory.openSession(true);
        try {
            //通过MyBatis实现接口UserDAO，返回实例
            UserDao userDao = session.getMapper(UserDao.class);
            List<UserPO> userPOS = userDao.getAllUsers();
            logger.info(userPOS);
            userPOS.forEach(userPO-> logger.info(userPO.getId()+","+userPO.getName()));
        } finally {
            session.close();
        }
    }*//*

}
*/
