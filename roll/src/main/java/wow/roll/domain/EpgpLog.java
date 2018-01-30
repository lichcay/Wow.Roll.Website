package wow.roll.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roll_epgplog")
public class EpgpLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8437164739045854641L;

	@Id
	private String id;

	@Column
	private String memberid;

	@Column
	private String timestamp;

	@Column
	private String type;

	@Column
	private String reason;

	@Column
	private String gear;

	@Column
	private String amount;

	@Column
	private String createtime;

	@Column
	private String edittime;

	@Column
	private int removetag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
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

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getEdittime() {
		return edittime;
	}

	public void setEdittime(String edittime) {
		this.edittime = edittime;
	}

	public int getRemovetag() {
		return removetag;
	}

	public void setRemovetag(int removetag) {
		this.removetag = removetag;
	}
}
