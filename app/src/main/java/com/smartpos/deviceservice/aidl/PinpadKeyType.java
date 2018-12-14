package com.smartpos.deviceservice.aidl;

public class PinpadKeyType{
	
	//0: MASTER KEY
	public static final int MASTERKEY = 0;
	
	//1:MAC key
	public static final int MACKEY = 1;
	
	// 2:PIN key
	public static final int PINKEY = 2;
	
	//3:TDK key
	public static final int TDKEY = 3;

	//4:(SM4)MASTER KEY
	public static final int SM_MASTERKEY = 4;

	//5:(SM4)MAC key
	public static final int SM_MACKEY = 5;

	//6:(SM4)PIN key
	public static final int SM_PINKEY = 6;

	//7:(SM4)TDK key
	public static final int SM_TDKEY = 7;
}
