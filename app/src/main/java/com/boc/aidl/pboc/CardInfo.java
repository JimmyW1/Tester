package com.boc.aidl.pboc;

import android.os.Parcel;
import android.os.Parcelable;

public class CardInfo implements Parcelable{
	private String balance;//电子现金余额
	
	public CardInfo(String balance){
		this.balance = balance;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(balance);
	}
	
	public static final Creator<CardInfo> CREATOR = new Creator<CardInfo>() {

		@Override
		public CardInfo createFromParcel(Parcel source) {
			String balance = source.readString();
			return new CardInfo(balance);
		}

		@Override
		public CardInfo[] newArray(int size) {
			return new CardInfo[size];
		}
	};

	public String getBalance() {
		return balance;
	}
	
}
