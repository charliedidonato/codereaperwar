package com.empirestateids.domain;

import java.util.List;
import com.nimbusds.jose.shaded.gson.annotations.SerializedName;


public class NdcInfoList {

	@SerializedName("ndcInfo")
	List<NdcInfo> ndcInfo;

	public void setNdcInfo(List<NdcInfo> ndcInfo) {
		this.ndcInfo = ndcInfo;
	}

	public List<NdcInfo> getNdcInfo() {
		return ndcInfo;
	}
	

}

