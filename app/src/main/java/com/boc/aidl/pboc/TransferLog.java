package com.boc.aidl.pboc;

import android.os.Parcel;
import android.os.Parcelable;

public class TransferLog implements Parcelable{
	/**
	 * 交易日期
	 */
	private byte[] tradeDate;
	/**
	 * 交易时间
	 */
	private byte[] tradeTime;
	/**
	 * 交易金额
	 */
	private byte[] tradeAmount;
	/**
	 * 其他金额
	 */
	private byte[] otherAmount;
	/**
	 * 国家代码
	 */
	private byte[] countryCode;
	/**
	 * 金额代码
	 */
	private byte[] currencyCode;
	/**
	 * 商户名称
	 */
	private byte[] merchantName;
	/**
	 * 交易类型
	 */
	private byte[] tradeType;
	
	/**
	 * 交易计数器
	 */
	private byte[] transCount;
	/**
	 * 圈存前余额
	 */
	private byte[] blanceOld;
	
	/**
	 * 圈存后余额
	 */
	private byte[] blanceNew;
	
	public TransferLog(byte[] tradeDate,byte[] tradeTime,byte[] tradeAmount,byte[] otherAmount,byte[] countryCode,byte[] currencyCode,byte[] merchantName,byte[] tradeType,byte[] transCount,byte[] blanceOld,byte[] blanceNew){
		this.tradeDate = tradeDate;
		this.tradeTime = tradeTime;
		this.tradeAmount = tradeAmount;
		this.otherAmount = otherAmount;
		this.countryCode = countryCode;
		this.currencyCode = currencyCode;
		this.merchantName = merchantName;
		this.tradeType = tradeType;
		this.transCount = transCount;
		this.blanceOld = blanceOld;
		this.blanceNew = blanceNew;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(tradeDate);
		dest.writeValue(tradeTime);
		dest.writeValue(tradeAmount);
		dest.writeValue(otherAmount);
		dest.writeValue(countryCode);
		dest.writeValue(currencyCode);
		dest.writeValue(merchantName);
		dest.writeValue(tradeType);
		dest.writeValue(transCount);
		dest.writeValue(blanceOld);
		dest.writeValue(blanceNew);
	}
	
	public static final Creator<TransferLog> CREATOR = new Creator<TransferLog>() {

		@Override
		public TransferLog createFromParcel(Parcel source) {
			byte[] tradeDate = (byte[]) source.readValue(byte[].class.getClassLoader());
			byte[] tradeTime = (byte[]) source.readValue(byte[].class.getClassLoader());
			byte[] tradeAmount = (byte[]) source.readValue(byte[].class.getClassLoader());
			byte[] otherAmount = (byte[]) source.readValue(byte[].class.getClassLoader());
			byte[] countryCode = (byte[]) source.readValue(byte[].class.getClassLoader());
			byte[] currencyCode = (byte[]) source.readValue(byte[].class.getClassLoader());
			byte[] merchantName = (byte[]) source.readValue(byte[].class.getClassLoader());
			byte[] tradeType = (byte[]) source.readValue(byte[].class.getClassLoader());
			byte[] transCount = (byte[]) source.readValue(byte[].class.getClassLoader());
			byte[] blanceOld = (byte[]) source.readValue(byte[].class.getClassLoader());
			byte[] blanceNew = (byte[]) source.readValue(byte[].class.getClassLoader());
			return new TransferLog(tradeDate,tradeTime,tradeAmount,otherAmount,countryCode,currencyCode,merchantName,tradeType,transCount,blanceOld,blanceNew);
		}

		@Override
		public TransferLog[] newArray(int size) {
			return new TransferLog[size];
		}
	};

	public byte[] getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(byte[] tradeDate) {
		this.tradeDate = tradeDate;
	}

	public byte[] getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(byte[] tradeTime) {
		this.tradeTime = tradeTime;
	}

	public byte[] getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(byte[] tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public byte[] getOtherAmount() {
		return otherAmount;
	}

	public void setOtherAmount(byte[] otherAmount) {
		this.otherAmount = otherAmount;
	}

	public byte[] getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(byte[] countryCode) {
		this.countryCode = countryCode;
	}

	public byte[] getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(byte[] currencyCode) {
		this.currencyCode = currencyCode;
	}

	public byte[] getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(byte[] merchantName) {
		this.merchantName = merchantName;
	}

	public byte[] getTradeType() {
		return tradeType;
	}

	public void setTradeType(byte[] tradeType) {
		this.tradeType = tradeType;
	}

	public byte[] getTransCount() {
		return transCount;
	}

	public void setTransCount(byte[] transCount) {
		this.transCount = transCount;
	}

	public byte[] getBlanceOld() {
		return blanceOld;
	}

	public void setBlanceOld(byte[] blanceOld) {
		this.blanceOld = blanceOld;
	}

	public byte[] getBlanceNew() {
		return blanceNew;
	}

	public void setBlanceNew(byte[] blanceNew) {
		this.blanceNew = blanceNew;
	}
	
	

}
