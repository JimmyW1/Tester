package com.boc.aidl.rfcard;

public class RFCardConst {
	public static class RFCardType {
		/**
		 * A卡
		 */
		public static final int ACARD = 0x01;
		/**
		 * B卡
		 */
		public static final int BCARD = 0x02;
		/**
		 * M1卡
		 */
		public static final int M1CARD = 0x04;
	}

	public static class RFKeyMode {
		public static final int KEYA_0X60 = 0x00;
		public static final int KEYA_0X00 = 0x01;
		public static final int KEYB_0X61 = 0x02;
		public static final int KEYB_0X01 = 0x03;
	}

	
}
