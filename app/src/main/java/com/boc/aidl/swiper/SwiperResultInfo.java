package com.boc.aidl.swiper;



import android.os.Parcel;
import android.os.Parcelable;

/**
 * 刷卡结果对象
 * 
 */
public class SwiperResultInfo  implements Parcelable{
	
	private int swipRslt;
	private String acctNo;
	private String firstTrackData;
	private String secondTrackData;
	private String thirdTrackData;
	private String validDate;
	private String serviceCode;
	private String ksn;
	private String extInfo;
	
	public static final Creator<SwiperResultInfo> CREATOR = new Creator<SwiperResultInfo>() {

		@Override
		public SwiperResultInfo createFromParcel(Parcel source) {
			String firstTrackData = source.readString();
			String secondTrackData = source.readString();
			String thirdTrackData = source.readString();
			String acctNo = source.readString();
			String validDate=source.readString();
			String serviceCode=source.readString();
			String ksn=source.readString();
			String extInfo=source.readString();
			int swipRslt = source.readInt();

			return new SwiperResultInfo(swipRslt, acctNo, firstTrackData, secondTrackData, thirdTrackData,validDate,serviceCode,ksn,extInfo);
		}

		@Override
		public SwiperResultInfo[] newArray(int size) {
			return new SwiperResultInfo[size];
		}
	};
	public SwiperResultInfo(int swipRslt, String acctNo,String firstTrackData,String secondTrackData,String thirdTrackData,String validDate,String serviceCode,String ksn,String extInfo) {

		this.swipRslt = swipRslt;
		this.acctNo = acctNo;
		this.firstTrackData = firstTrackData;
		this.secondTrackData = secondTrackData;
		this.thirdTrackData = thirdTrackData;
		this.extInfo=extInfo;
		this.ksn=ksn;
		this.serviceCode=serviceCode;
		this.validDate=validDate;
	}


	/**
	 * 获得一磁道数据
	 * <p>
	 * 
	 * @since ver1.0
	 * @return
	 */
	public String getFirstTrackData() {
		return firstTrackData;
	}

	/**
	 * 获得二磁道数据
	 * @return
	 */
	public String getSecondTrackData() {
		return secondTrackData;
	}

	/**
	 * 获得三磁道数据
	 * @return
	 */
	public String getThirdTrackData() {
		return thirdTrackData;
	}

	/**
	 * 刷卡结果
	 * @return
	 */
	public int getSwipRslt() {
		return swipRslt;
	}

	/**
	 * 账号信息
	 * @return
	 */
	public String getAcctNo() {
		return acctNo;
	}
	/**
	 * 获得卡号有效期
	 * 
	 * @return
	 */
	public String getValidDate() {
		return validDate;
	}

	/**
	 * 获得服务代码
	 * 
	 * @return
	 */
	public String getServiceCode() {
		return serviceCode;
	}

	/**
	 * 获得设备的ksn号
	 * 
	 * @return
	 */
	public String getKsn() {
		return ksn;
	}

	/**
	 * 获得扩展信息
	 * 
	 * @return
	 */
	public String getExtInfo() {
		return extInfo;
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
		dest.writeString(acctNo);
		dest.writeString(validDate);
		dest.writeString(serviceCode);
		dest.writeString(ksn);
		dest.writeString(extInfo);
		dest.writeInt(swipRslt);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("swipRslt(");
		sb.append("rsltType:" + swipRslt + ",");
		sb.append("acct:" + (acctNo == null ? "null" : acctNo) + ",");
		sb.append(",");
		sb.append("firstTrackData:" + (firstTrackData == null ? "null" :firstTrackData) + ",");
		sb.append("secondTrackData:" + (secondTrackData == null ? "null" : secondTrackData) + ",");
		sb.append("thirdTrackData:" + (thirdTrackData == null ? "null" :thirdTrackData)+ ",");
		sb.append(")");
		return sb.toString();
	}


}
