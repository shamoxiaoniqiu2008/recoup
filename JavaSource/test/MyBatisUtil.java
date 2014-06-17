package test; 
 
import java.io.IOException; 
import java.io.Reader; 
 
import org.apache.ibatis.io.Resources; 
import org.apache.ibatis.session.SqlSessionFactory; 
import org.apache.ibatis.session.SqlSessionFactoryBuilder; 
 
public class MyBatisUtil  { 
	
    private  final static SqlSessionFactory sqlSessionFactory; 
    
    static { 
       String resource = "mybatis-config.xml"; 
       Reader reader = null; 
       try { 
           reader = Resources.getResourceAsReader(resource); 
       } catch (IOException e) { 
           System.out.println(e.getMessage()); 
 
       } 
       /*
        * 在单独的mybatis中，sqlSessionFactory是由sqlSessionFactoryBuilder创建的
        * 而在mybatis-spring整合中，sqlSessionFacotry是由sqlSeesionFactoryBean创建的
        * 注意此处的区别
        */
       sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader); 
    } 
 
    public static SqlSessionFactory getSqlSessionFactory() { 
       return sqlSessionFactory; 
    } 
    
 
}
