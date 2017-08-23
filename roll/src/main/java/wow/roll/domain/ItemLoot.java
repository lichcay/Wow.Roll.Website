package wow.roll.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "roll_itemloots")
public class ItemLoot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8437164739045854641L;

	@Id
	private String id;

	@Column
	private String charactername;

	@Column
	private String loottimestamp;

	@Column
	private String itemid;

	@Column
	private String context;

	@Column
	private String bonuslists;

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

	public String getCharactername() {
		return charactername;
	}

	public void setCharactername(String charactername) {
		this.charactername = charactername;
	}

	public String getLoottimestamp() {
		return loottimestamp;
	}

	public void setLoottimestamp(String loottimestamp) {
		this.loottimestamp = loottimestamp;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getBonuslists() {
		return bonuslists;
	}

	public void setBonuslists(String bonuslists) {
		this.bonuslists = bonuslists;
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

	@Override
	public boolean equals(Object obj) {
		boolean result = StringUtils.equals(((ItemLoot) obj).loottimestamp + ((ItemLoot) obj).charactername + ((ItemLoot) obj).itemid,
				this.loottimestamp + this.charactername + this.itemid);
		return result;
	}

	@Override
	public int hashCode() {
		return (loottimestamp + charactername + itemid).hashCode();
	}

}
