package com.empirestateids.domain;

import java.util.Objects;

import com.fasterxml.jackson.annotation.*;

public class ProfileDetail {

	private boolean profileMnemonicUsed;
    private String mnemonic;
    private boolean mnemonicSupplied;
    private long hitCount;
    private boolean favorite;

    @JsonProperty("ProfileMnemonicUsed")
    public boolean getProfileMnemonicUsed() { return profileMnemonicUsed; }
    @JsonProperty("ProfileMnemonicUsed")
    public void setProfileMnemonicUsed(boolean value) { this.profileMnemonicUsed = value; }

    @JsonProperty("Mnemonic")
    public String getMnemonic() { return mnemonic; }
    @JsonProperty("Mnemonic")
    public void setMnemonic(String value) { this.mnemonic = value; }

    @JsonProperty("MnemonicSupplied")
    public boolean getMnemonicSupplied() { return mnemonicSupplied; }
    @JsonProperty("MnemonicSupplied")
    public void setMnemonicSupplied(boolean value) { this.mnemonicSupplied = value; }

    @JsonProperty("HitCount")
    public long getHitCount() { return hitCount; }
    @JsonProperty("HitCount")
    public void setHitCount(long value) { this.hitCount = value; }

    @JsonProperty("Favorite")
    public boolean getFavorite() { return favorite; }
    @JsonProperty("Favorite")
    public void setFavorite(boolean value) { this.favorite = value; }

    @Override
	public String toString() {
		return "ProfileDetail [profileMnemonicUsed=" + profileMnemonicUsed + ", mnemonic=" + mnemonic
				+ ", mnemonicSupplied=" + mnemonicSupplied + ", hitCount=" + hitCount + ", favorite=" + favorite + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(favorite, hitCount, mnemonic, mnemonicSupplied, profileMnemonicUsed);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProfileDetail other = (ProfileDetail) obj;
		return favorite == other.favorite && hitCount == other.hitCount && Objects.equals(mnemonic, other.mnemonic)
				&& mnemonicSupplied == other.mnemonicSupplied && profileMnemonicUsed == other.profileMnemonicUsed;
	}
}
