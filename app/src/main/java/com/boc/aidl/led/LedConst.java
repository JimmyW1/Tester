package com.boc.aidl.led;

public class LedConst {
	
	public static class LedOperation{
		//熄灭
		public static final int TURN_OFF=0x00;
		//点亮
		public static final int TURN_ON=0x01;
		//闪烁
		public static final int BLINK=0x02;
		
	}
	public static class LedColor{
		//蓝灯
		public static final int BLUE_LIGHT=0x01;
		//绿灯
		public static final int GREEN_LIGHT=0x02;
		//黄灯
		public static final int YELLOW_LIGHT=0x04;
		//红灯
		public static final int RED_LIGHT=0x08;
	}
}
