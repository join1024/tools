package com.join.tools.http;

/**
 * @author ：Join
 * @date ：Created in 2019/6/24 21:33
 * @modified By：
 */
public class ByteResponse {
	private String contentType;

	private long contentLength;

	private byte[] bytes;

	public ByteResponse(String contentType, long contentLength, byte[] bytes) {
		this.contentType = contentType;
		this.contentLength = contentLength;
		this.bytes = bytes;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
}
