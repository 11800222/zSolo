package spring.txManage;

import mybatis.SqlSession.T_SqlSessionTemplate;
import mybatis.mappers.TdSystemLogMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author: chenguanda
 * @date: 2019-05-20 09:07
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Propagation {



    @Test
    public void Rollback() throws Exception {

    }


    static Logger logger = LoggerFactory.getLogger(T_SqlSessionTemplate.class);

    public static TdSystemLogMapper tdSystemLogMapper;

    public static SqlSessionTemplate sqlSessionTemplate;


    @Autowired
    public void setTdSystemLogMapper(TdSystemLogMapper tdSystemLogMapper) {
        T_SqlSessionTemplate.tdSystemLogMapper = tdSystemLogMapper;
    }

    @Autowired
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        T_SqlSessionTemplate.sqlSessionTemplate = sqlSessionTemplate;
    }

}


