package com.cs.android.task;

import java.util.Map;

/**
 * 独立任务请求操作接口
 * @author james
 *
 */
public interface Task
{
	//执行动作,并且返回结果
	Map<String, Object> execute() throws Exception;
	//得到调用参数
	Object getParam();

	void setParams(Map<String, Object> params);



//    public void setUrl(String url);

//    public void setPost(boolean isPost);
}
