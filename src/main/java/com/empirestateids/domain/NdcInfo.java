package com.empirestateids.domain;

import com.nimbusds.jose.shaded.gson.annotations.SerializedName;


public class NdcInfo {

		@SerializedName("ndc11")
		String ndc11;

		@SerializedName("conceptName")
		String conceptName;

		@SerializedName("tty")
		String tty;

		@SerializedName("conceptStatus")
		String conceptStatus;

		@SerializedName("status")
		String status;

		@SerializedName("rxcui")
		String rxcui;

		public String getNdc11() {
			return ndc11;
		}

		public void setNdc11(String ndc11) {
			this.ndc11 = ndc11;
		}

		public String getConceptName() {
			return conceptName;
		}

		public void setConceptName(String conceptName) {
			this.conceptName = conceptName;
		}

		public String getTty() {
			return tty;
		}

		public void setTty(String tty) {
			this.tty = tty;
		}

		public String getConceptStatus() {
			return conceptStatus;
		}

		public void setConceptStatus(String conceptStatus) {
			this.conceptStatus = conceptStatus;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getRxcui() {
			return rxcui;
		}

		public void setRxcui(String rxcui) {
			this.rxcui = rxcui;
		}

		@Override
		public NdcInfo clone() {

			NdcInfo info = new NdcInfo();
			info.setNdc11(this.ndc11);
			info.setConceptName(this.conceptName);
			info.setTty(this.tty);
			info.setConceptStatus(this.conceptStatus);
			info.setStatus(this.status);
			info.setRxcui(this.rxcui);
			return info;
		}

		@Override
		public String toString() {
			return "NdcInfo [ndc11=" + ndc11 + ", conceptName=" + conceptName + ", tty=" + tty + ", conceptStatus="
					+ conceptStatus + ", status=" + status + ", rxcui=" + rxcui + "]";
		}	
}
