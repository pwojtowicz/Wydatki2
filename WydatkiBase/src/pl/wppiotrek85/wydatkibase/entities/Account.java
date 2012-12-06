package pl.wppiotrek85.wydatkibase.entities;

import java.util.Date;

public class Account extends ModelBase {

	public Account() {

	}

	public Account(int id) {
		super.setId(id);
	}

	public Account(int id, String name, Double balance, Boolean isActive) {
		super.setId(id);
		setName(name);
		this.setBalance(balance);
		this.setIsActive(isActive);
	}

	private Date lastActionDate;

	private String name;

	private Double balance;

	private Boolean isActive;

	private Boolean isVisibleForAll;

	private Boolean isSumInGlobalBalance;

	private Byte imageIndex;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Boolean isActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the isVisibleForAll
	 */
	public Boolean isVisibleForAll() {
		return isVisibleForAll;
	}

	/**
	 * @param isVisibleForAll
	 *            the isVisibleForAll to set
	 */
	public void setIsVisibleForAll(Boolean isVisibleForAll) {
		this.isVisibleForAll = isVisibleForAll;
	}

	/**
	 * @return the isSumInGlobalBalance
	 */
	public Boolean isSumInGlobalBalance() {
		return isSumInGlobalBalance;
	}

	/**
	 * @param isSumInGlobalBalance
	 *            the isSumInGlobalBalance to set
	 */
	public void setIsSumInGlobalBalance(Boolean isSumInGlobalBalance) {
		this.isSumInGlobalBalance = isSumInGlobalBalance;
	}

	public Byte getImageIndex() {
		return imageIndex;
	}

	public void setImageIndex(Byte imageIndex) {
		this.imageIndex = imageIndex;
	}

	public Date getLastActionDate() {
		return lastActionDate;
	}

	public void setLastActionDate(Date lastActionDate) {
		this.lastActionDate = lastActionDate;
	}

	// /**
	// * @return the isVisibleForAll
	// */
	// public Boolean getIsVisibleForAll() {
	// return isVisibleForAll;
	// }
	//
	// /**
	// * @param isVisibleForAll
	// * the isVisibleForAll to set
	// */
	// public void setIsVisibleForAll(Boolean isVisibleForAll) {
	// this.isVisibleForAll = isVisibleForAll;
	// }

}
