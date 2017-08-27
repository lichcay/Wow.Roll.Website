package wow.roll.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang.StringUtils;

@Entity
public class ItemLootView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8437164739045854641L;

	@Id
	private String id;

	@Column
	private String charactername;

	@Column
	private int cclass;

	@Column
	private String loottimestamp;

	@Column
	private String itemid;

	@Column
	private String context;

	@Column
	private String bonuslists;

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

	public int getCclass() {
		return cclass;
	}

	public void setCclass(int cclass) {
		this.cclass = cclass;
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

	@Override
	public boolean equals(Object obj) {
		boolean result = StringUtils.equals(((ItemLootView) obj).loottimestamp + ((ItemLootView) obj).charactername + ((ItemLootView) obj).itemid,
				this.loottimestamp + this.charactername + this.itemid);
		return result;
	}

	@Override
	public int hashCode() {
		return (loottimestamp + charactername + itemid).hashCode();
	}

	public ItemLootView(String id, String charactername, int cclass, String loottimestamp, String itemid, String context, String bonuslists) {
		super();
		this.id = id;
		this.charactername = charactername;
		this.cclass = cclass;
		this.loottimestamp = loottimestamp;
		this.itemid = itemid;
		this.context = context;
		this.bonuslists = bonuslists;
	}

}
