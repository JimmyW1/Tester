package com.boc.aidl.pboc;

public class PBOCParamOper {
	/**
	 * aid和公钥操作
	 */
	private static final int AID_OPERATE_OFFSET = 0x200;
	/**
	 * 添加
	 */
	public static final int ADD = AID_OPERATE_OFFSET + 1;
	/**
	 * 删除
	 */
	public static final int REMOVE = AID_OPERATE_OFFSET + 2;
	/**
	 * 删除全部
	 */
	public static final int REMOVE_ALL = AID_OPERATE_OFFSET + 3;
	
}
