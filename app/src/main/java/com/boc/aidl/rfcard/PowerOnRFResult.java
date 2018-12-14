package com.boc.aidl.rfcard;

import android.os.Parcel;
import android.os.Parcelable;

public class PowerOnRFResult implements Parcelable{
	private int rfcardType;
	private byte[] cardSerialNo;
	private byte[] atqa;
	
	public PowerOnRFResult(int rfcardType,byte[] cardSerialNo,byte[] atqa) {
		this.rfcardType = rfcardType;
		this.cardSerialNo = cardSerialNo;
		this.atqa =atqa;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(rfcardType);
		dest.writeValue(cardSerialNo);
		dest.writeValue(atqa);
	}
	
	public static final Creator<PowerOnRFResult> CREATOR = new Creator<PowerOnRFResult>() {

		@Override
		public PowerOnRFResult createFromParcel(Parcel source) {
			return new PowerOnRFResult(source.readInt(),(byte[]) source.readValue(byte[].class.getClassLoader()),(byte[]) source.readValue(byte[].class.getClassLoader()));
		}

		@Override
		public PowerOnRFResult[] newArray(int size) {
			return new PowerOnRFResult[size];
		}
	};

	public int getRfcardType() {
		return rfcardType;
	}


	public void setRfcardType(int rfcardType) {
		this.rfcardType = rfcardType;
	}


	public byte[] getCardSerialNo() {
		return cardSerialNo;
	}


	public void setCardSerialNo(byte[] cardSerialNo) {
		this.cardSerialNo = cardSerialNo;
	}


	public byte[] getAtqa() {
		return atqa;
	}


	public void setAtqa(byte[] atqa) {
		this.atqa = atqa;
	}
	
	

}
