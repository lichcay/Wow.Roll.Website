package wow.roll.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EpgpLogViewShow implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8437164739045854641L;

	@Id
	private int cclass;

	@Column
	private String charactername;

	@Column
	private String type;

	@Column
	private String reason;

	@Column
	private String itemid;

	@Column
	private String bonusids;

	@Column
	private String amount;

	@Column
	private String timestamp;

	public int getCclass() {
		return cclass;
	}

	public void setCclass(int cclass) {
		this.cclass = cclass;
	}

	public String getCharactername() {
		return charactername;
	}

	public void setCharactername(String charactername) {
		this.charactername = charactername;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getBonusids() {
		return bonusids;
	}

	public void setBonusids(String bonusids) {
		this.bonusids = bonusids;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
