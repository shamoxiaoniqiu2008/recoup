package test; 
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import persistence.UserInfoMapper;
import domain.UserInfo;
 
public class TestMapper { 
	
    static SqlSessionFactory sqlSessionFactory = null; 
    static { 
       sqlSessionFactory = MyBatisUtil.getSqlSessionFactory(); 
    } 
 
    @Test 
    public void testAdd() { 
       SqlSession sqlSession = sqlSessionFactory.openSession(); 
       try { 
           UserInfoMapper userMapper = sqlSession.getMapper(UserInfoMapper.class); 
           
           UserInfo user = new UserInfo(); 
           user.setUserName("tom");
           userMapper.insert(user); 
           
           sqlSession.commit();//这里一定要提交，不然数据进不去数据库中 
           
       } finally { 
           sqlSession.close(); 
       } 
    } 
 
//    @Test 
//    public void getUser() { 
//       SqlSession sqlSession = sqlSessionFactory.openSession(); 
//       try { 
//           UserInfoMapper userMapper = sqlSession.getMapper(UserInfoMapper.class); 
//           UserInfo user = userMapper.getUser("jun"); 
//           System.out.println("name: "+user.getName()+"|age: "+user.getAge()); 
//       } finally { 
//           sqlSession.close(); 
//       } 
//    } 
 
}