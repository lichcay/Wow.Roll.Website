package wow.roll.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roll_bnaccount")
public class BnAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8437164739045854641L;

	@Id
	private String id;

	@Column
	private String battletag;

	@Column
	private String btid;

	@Column
	private String defaultcharacterid;

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

	public String getBattletag() {
		return battletag;
	}

	public void setBattletag(String battletag) {
		this.battletag = battletag;
	}

	public String getBtid() {
		return btid;
	}

	public void setBtid(String btid) {
		this.btid = btid;
	}

	public String getDefaultcharacterid() {
		return defaultcharacterid;
	}

	public void setDefaultcharacterid(String defaultcharacterid) {
		this.defaultcharacterid = defaultcharacterid;
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
