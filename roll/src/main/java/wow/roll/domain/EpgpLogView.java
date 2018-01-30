package wow.roll.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EpgpLogView implements Serializable {

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
	private String gear;

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

	public String getGear() {
		return gear;
	}

	public void setGear(String gear) {
		this.gear = gear;
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

	public EpgpLogView(int cclass, String charactername, String type, String reason, String gear, String amount, String timestamp) {
		super();
		this.charactername = charactername;
		this.cclass = cclass;
		this.type = type;
		this.reason = reason;
		this.gear = gear;
		this.amount = amount;
		this.timestamp = timestamp;
	}
}
