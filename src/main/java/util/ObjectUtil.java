/*******************************************************************************
 * Project Key : 
 * Create on 2018年7月30日 下午2:54:16
 * Copyright (c) 2004 - 2014. 拉卡拉支付有限公司版权所有. 京ICP备12007612号
 * 注意：本内容仅限于拉卡拉支付有限公司内部传阅，禁止外泄以及用于其他的商业目的
 ******************************************************************************/
package util;

/**
 * <P>TODO</P>
 * 
 * @author 陈冠达 2018年7月30日 下午2:54:16
 */
public class ObjectUtil {
	public static void main(String wd[]) {
	}

	public static String getObjectId(Object o) {
		String org = o.toString();
		return org.substring(org.length() - 4);
	}
}
