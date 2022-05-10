package com.bjpowernode.test;

import com.bjpowernode.mapper.UsersMapper;
import com.bjpowernode.pojo.Users;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.javassist.tools.Dump;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 */
public class MyTest {

    SqlSession sqlSession;
    //动态代理对象
    UsersMapper uMapper;
    //日期的格式化刷子
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void openSqlSession() throws IOException {
        //1.读取核心配置文件
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建工厂对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.取出sqlSession
        sqlSession = factory.openSession();
        //4.取出动态代理的对象,完成接口中方法的调用,实则是调用xml文件中相的标签的功能
        uMapper = sqlSession.getMapper(UsersMapper.class);
    }

    @After
    public void closeSqlSession(){
        sqlSession.close();
    }

    @Test
    public void testGetAll(){

        System.out.println(uMapper.getClass());
        //就是在调用接口的方法,mybatis框架已经为我们把功能代理出来了.
        List<Users> list = uMapper.getAll();
        list.forEach(users -> System.out.println(users));
    }

    @Test
    public void testUUID(){
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString().replace("-","").substring(20));
    }

    @Test
    public void testGetByCondition() throws ParseException {
        Users u = new Users();
        u.setBirth(sf.parse("2022-10-30"));
        List<Users> list = uMapper.getByCondition(u);
        list.forEach(users -> System.out.println(users));
    }

    @Test
    public void testUpdateSet(){
        Users u = new Users();
        u.setId(1);
        u.setUsername("煞笔");
        u.setAddress("美国");
        int i = uMapper.updateBySet(u);
        sqlSession.commit();
        System.out.println(i);
    }

    @Test
    public void testGetByIds(){
        Integer array[] = {1,3};
        List<Users> list = uMapper.getByIds(array);
        list.forEach(users -> System.out.println(users));
    }

    @Test
    public void testDeleteBatch(){
        Integer array[] = {4,5};
        int i = uMapper.deleteBatch(array);
        sqlSession.commit();
        System.out.println(i);
    }

    @Test
    public void testInsertBatch() throws ParseException {
        Users u1 = new Users("aa",sf.parse("2022-11-11"),"1","白云区");
        Users u2 = new Users("bb",sf.parse("2022-11-11"),"1","白云区1");
        Users u3 = new Users("cc",sf.parse("2022-11-11"),"1","白云区2");
        List<Users> list = new ArrayList<>();
        list.add(u1);
        list.add(u2);
        list.add(u3);
        int i = uMapper.insertBatch(list);
        sqlSession.commit();
        System.out.println(i);
    }

    @Test
    public void testGetBirth() throws ParseException {
        Date begin = sf.parse("2022-11-01");
        Date end = sf.parse("2022-11-30");
        List<Users> list = uMapper.getByBirthday(begin, end);
        list.forEach(users -> System.out.println(users));
    }

    @Test
    public void testGetByMap() throws ParseException {
        Date begin = sf.parse("2022-11-01");
        Date end = sf.parse("2022-11-30");
        Map map = new HashMap<>();
        map.put("birthdayBegin",begin);
        map.put("birthdayEnd",end);
        List<Users> list = uMapper.getByMap(map);
        list.forEach(users -> System.out.println( ));
    }

    @Test
    public void testReturnMapOne(){
        Map map = uMapper.getReturnMap(1);
        System.out.println(map);
        System.out.println(map.get("username"));
        System.out.println(map.get("address"));
    }

    @Test
    public void testReturnMapMul(){
        List<Map> list = uMapper.getMulMap();
        list.forEach(map -> System.out.println(map));
    }

}
