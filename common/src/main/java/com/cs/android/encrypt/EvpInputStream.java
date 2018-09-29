package com.cs.android.encrypt;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class EvpInputStream extends FilterInputStream
{
	static
	{
		System.loadLibrary("cscrypto");
	}
	
	private static final int BUFFER_SIZE = 1024;

	private long ctx = 0;
	
	private byte[] inbuf = new byte[BUFFER_SIZE];
	private byte[] outbuf = null;
	private int index = 0;
	private boolean closed = false;
	
	private String passwd;
	private int doEncrypt;
	/**
	 * 
	 * @param in
	 * @param passwd
	 * @param doEncrypt:1为加密，0为解密
	 */
	public EvpInputStream(InputStream in, String passwd, int doEncrypt)
	{
		super(in);
		this.passwd = passwd;
		this.doEncrypt = doEncrypt;
		
		this.ctx = init(passwd.getBytes(), doEncrypt);
	}
	
	private native long init(byte[] passwd, int doEncrypt);
	private native void cleanup(long ctx);
	private native byte[] update(long ctx, byte[] in, int len);
	private native byte[] doFinal(long ctx);

	@Override
	public int available() throws IOException
	{
		if(ctx == 0)
			return 0;
		
		return super.available();
	}

	@Override
	public void close() throws IOException
	{
		if(ctx == 0)
			return;
		
		cleanup(ctx);
		ctx = 0;
		super.close();
	}
	
	private int copy(byte[] buffer, int offset, int count)
	{
		int outlen = outbuf.length;
		int byteCount = Math.min(count, outlen - index);
		index += byteCount;
		System.arraycopy(outbuf, 0, buffer, offset, byteCount);
		if(index == outlen)
			index = 0;
		
		return byteCount;
	}

	@Override
	public int read(byte[] buffer, int offset, int count) throws IOException
	{
		if(ctx == 0)
			throw new IOException();
		
		if(index != 0)//上次从src读的数据还有一部分没有copy到buffer中
		{
			return copy(buffer, offset, count);
		}
		
		if(closed)//已经结束了，返回关闭
			return -1;
		
		int inlen = super.read(inbuf, offset, Math.min(count, BUFFER_SIZE));
		if(inlen == -1)
		{
			closed = true;
			outbuf = doFinal(ctx);
			return copy(buffer, offset, count);
		}
		
		outbuf = update(ctx, inbuf, inlen);

		return copy(buffer, offset, count);
	}

	@Override
	public int read(byte[] buffer) throws IOException
	{
		return read(buffer, 0, buffer.length);
	}

	@Override
	public synchronized void reset() throws IOException
	{
		super.reset();
		
		if(ctx == 0)
			throw new IOException();
		
		cleanup(ctx);
		this.ctx = 0;
		this.ctx = init(passwd.getBytes(), doEncrypt);
	}
	
	

}
