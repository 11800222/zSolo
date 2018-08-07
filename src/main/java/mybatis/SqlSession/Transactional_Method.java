/*******************************************************************************
 * Project Key : 
 * Create on 2018年8月7日 下午6:34:19
 * Copyright (c) 2004 - 2014. 拉卡拉支付有限公司版权所有. 京ICP备12007612号
 * 注意：本内容仅限于拉卡拉支付有限公司内部传阅，禁止外泄以及用于其他的商业目的
 ******************************************************************************/
package mybatis.SqlSession;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * <P>TODO</P>
 * @author 陈冠达   2018年8月7日 下午6:34:19  
 */
@Component
class Transactional_Method {
	@Transactional
	public void Query() throws Exception {
		T_SqlSessionTemplate.tdSystemLogMapper.selectByPrimaryKey("1212");
	}

}