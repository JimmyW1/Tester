package com.boc.aidl.pboc;

import android.os.Parcel;
import android.os.Parcelable;

public class AidEntry implements Parcelable{
	
	private int index;
	private byte[] aid;
	private String name;
	
	public AidEntry(int index,byte[] aid,String name){
		this.index = index;
		this.aid = aid;
		this.name = name;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(index);
		if(aid != null){
			parcel.writeInt(aid.length);
			parcel.writeByteArray(aid);
		}else{
			parcel.writeInt(-1);
		}
		parcel.writeString(name);
		
	}
	public static final Creator<AidEntry> CREATOR = new Creator<AidEntry>() {

		@Override
		public AidEntry createFromParcel(Parcel source) {
			int index = source.readInt();
			int aidLen = source.readInt();
			byte[] aid = null;
			if(aidLen>0){
				aid = new byte[aidLen];
				source.readByteArray(aid);
			}
			String aidName = source.readString();
			return new AidEntry(index,aid,aidName);
		}

		@Override
		public AidEntry[] newArray(int size) {
			return new AidEntry[size];
		}
	};

	public int getIndex() {
		return index;
	}

	public byte[] getAid() {
		return aid;
	}

	public String getName() {
		return name;
	}

	public static Creator<AidEntry> getCreator() {
		return CREATOR;
	}
	

}
