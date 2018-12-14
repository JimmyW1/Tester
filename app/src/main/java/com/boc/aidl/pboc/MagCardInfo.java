package com.boc.aidl.pboc;

import android.os.Parcel;
import android.os.Parcelable;

public class MagCardInfo implements Parcelable {

	private String firstTrackData = null; // 一磁道数据
	private String secondTrackData = null; // 二磁道数据
	private String thirdTrackData = null; // 三磁道数据
	private String cardno = null; // 卡号
	private String expiryDate = null; // 卡片有效期
	private String serviceCode = null; // 服务码

	public static final Creator<MagCardInfo> CREATOR = new Creator<MagCardInfo>() {

		@Override
		public MagCardInfo createFromParcel(Parcel source) {
			String firstTrackData = source.readString();
			String secondTrackData = source.readString();
			String thirdTrackData = source.readString();
			String cardno = source.readString();
			String expiryDate = source.readString();
			String serviceCode = source.readString();
			return new MagCardInfo(firstTrackData,secondTrackData,thirdTrackData,cardno,expiryDate,serviceCode);
		}

		@Override
		public MagCardInfo[] newArray(int size) {
			return new MagCardInfo[size];
		}
	};

	public MagCardInfo(String firstTrackData,String secondTrackData, String thirdTrackData,String cardno,String expiryDate,String serviceCode) {
		this.firstTrackData = firstTrackData;
		this.secondTrackData = secondTrackData;
		this.thirdTrackData = thirdTrackData;
		this.cardno = cardno;
		this.expiryDate = expiryDate;
		this.serviceCode = serviceCode;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(firstTrackData);
		dest.writeString(secondTrackData);
		dest.writeString(thirdTrackData);
		dest.writeString(cardno);
		dest.writeString(expiryDate);
		dest.writeString(serviceCode);
	}

	public String getFirstTrackData() {
		return firstTrackData;
	}

	public String getSecondTrackData() {
		return secondTrackData;
	}

	public String getThirdTrackData() {
		return thirdTrackData;
	}

	public String getCardno() {
		return cardno;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public String getServiceCode() {
		return serviceCode;
	}

}
