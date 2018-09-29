package com.cs.upgrade.entity;

import java.io.Serializable;

public class Upgrade implements Serializable
{

	/**
	 * weifei
	 */
	private static final long serialVersionUID = 1L;

	public static final int Upgrade_ID = 1;// 应用id
	public static final int NO_Upgrde = 0;// 不升级状态
	public static final int SHOW_UPGRDE = 1;// 显示升级按钮
	public static final int ING_UPGRDE = 2;// 显示升级中

	private int id;
	private String name;
	private int version_code;
	private String version_name;
	private String url;
	private int size;
	private String content;
	private int upgrdeFlag;

	public int getUpgrdeFlag()
	{
		return upgrdeFlag;
	}

	public void setUpgrdeFlag(int upgrdeFlag)
	{
		this.upgrdeFlag = upgrdeFlag;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getVersion_code()
	{
		return version_code;
	}

	public void setVersion_code(int version_code)
	{
		this.version_code = version_code;
	}

	public String getVersion_name()
	{
		return version_name;
	}

	public void setVersion_name(String version_name)
	{
		this.version_name = version_name;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

}
