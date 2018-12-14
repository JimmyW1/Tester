package com.boc.aidl.iccard;

public class ICCardConst {

	public static class ICCardSlot {
		public static final int IC1 = 0x00;
		public static final int SAM1 = 0x01;
	}

	public static class ICCardType {
		public static final int CPUCARD = 0x00;
		public static final int AT24CXX = 0x01;
		public static final int SLE44X2 = 0x02;
		public static final int SLE44X8 = 0x03;
		public static final int AT88SC102 = 0x04;
		public static final int AT88SC1604 = 0x05;
		public static final int AT88SC1608 = 0x06;
		public static final int ISO7816 = 0x07;
		public static final int AT88SC153 = 0x08;
	}

	public static class ICCardSlotState {
		/**
		 * 未插卡
		 */
		public static final int NO_CARD = 0x00;
		/**
		 * 卡已经插入
		 */
		public static final int CARD_INSERTED = 0x01;
	}
}
