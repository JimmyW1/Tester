package com.boc.aidl.pboc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 联机交易后的参数设置入口
 */
public class OnlineResultData implements Parcelable {

	private byte[] ic_data;// 取自银联8583规范55域值
	private String responseCode;// 取自银联8583规范39域值
	private String authCode;// 取自银联8583规范38域值

	public OnlineResultData(byte[] ic_data,String responseCode,String authCode) {
		this.ic_data = ic_data;
		this.responseCode = responseCode;
		this.authCode = authCode;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(ic_data);
		dest.writeString(responseCode);
		dest.writeString(authCode);
	}

	public static final Creator<OnlineResultData> CREATOR = new Creator<OnlineResultData>() {

		@Override
		public OnlineResultData createFromParcel(Parcel source) {
			byte[] ic_data = (byte[]) source.readValue(byte[].class.getClassLoader());
			String responseCode = source.readString();
			String authCode = source.readString();
			return new OnlineResultData(ic_data,responseCode,authCode);
		}

		@Override
		public OnlineResultData[] newArray(int size) {
			return new OnlineResultData[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	public byte[] getIc_data() {
		return ic_data;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public String getAuthCode() {
		return authCode;
	}

	public static Creator<OnlineResultData> getCreator() {
		return CREATOR;
	}

}
