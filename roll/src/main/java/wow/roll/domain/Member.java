package wow.roll.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "roll_member")
public class Member implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6426335207456892558L;

	@Id
	private String id;

	@Column
	private String name;

	@Column
	private int cclass;

	@Column
	private int race;

	@Column
	private int gender;

	@Column
	private int level;

	@Column
	private String thumbnail;

	@Column
	private int rank;

	@Column
	private int epgp;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCclass() {
		return cclass;
	}

	public void setCclass(int cclass) {
		this.cclass = cclass;
	}

	public int getRace() {
		return race;
	}

	public void setRace(int race) {
		this.race = race;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getEpgp() {
		return epgp;
	}

	public void setEpgp(int epgp) {
		this.epgp = epgp;
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
		boolean result = StringUtils.equals(((Member) obj).name, this.name);
		return result;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

}
