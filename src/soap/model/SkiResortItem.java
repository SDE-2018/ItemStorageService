package soap.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;


/**
 * Entity respresenting ski resorts.
 * 
 * @author ivan
 *
 */
@Entity
public class SkiResortItem implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int resortId;
	
	private String name;
	private String officialWebsite;
	private String region;
	private String skiMapUrl; //image url of the ski resort map
	private String lat;
	private String lng;
	
	private Date lastUpdated;
	
	/**
	 * Number of lift at the resort.
	 */
	private int liftCount;
	
	/**
	 * Height of the highest mountain, meters.
	 */
	private int top;
	
	/**
	 * Distance of the longest run, meters.
	 */
	private int longestRun;
	
	private boolean nightSkiing;
	
	private boolean terrainPark;

	public SkiResortItem() {};
	
	public SkiResortItem(int id, String name, int liftCount, int top,  
			String website, boolean nightSkiing,
			String skiMapUrl, Date lastUpdated) {
		super();
		this.resortId = id;
		this.name = name;
		this.liftCount = liftCount;
		this.top = top;
		this.officialWebsite = website;
		this.nightSkiing = nightSkiing;
		this.skiMapUrl = skiMapUrl;
		this.lastUpdated = lastUpdated;
	}
	
	
	
	public SkiResortItem(int resortId, String name, String officialWebsite,
			String region, String skiMapUrl, Date lastUpdated, int liftCount,
			int top, String lat, String lng, int longestRun, 
			boolean nightSkiing, boolean terrainPark) {
		super();
		this.resortId = resortId;
		this.name = name;
		this.officialWebsite = officialWebsite;
		this.region = region;
		this.skiMapUrl = skiMapUrl;
		this.lastUpdated = lastUpdated;
		this.liftCount = liftCount;
		this.top = top;
		this.lat = lat;
		this.lng = lng;
		this.longestRun = longestRun;
		this.nightSkiing = nightSkiing;
		this.terrainPark = terrainPark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLiftCount() {
		return liftCount;
	}

	public void setLiftCount(int liftCount) {
		this.liftCount = liftCount;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public String getWebsite() {
		return officialWebsite;
	}

	public void setWebsite(String website) {
		this.officialWebsite = website;
	}

	public boolean isNightSkiing() {
		return nightSkiing;
	}

	public void setNightSkiing(boolean nightSkiing) {
		this.nightSkiing = nightSkiing;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public int getId() {
		return resortId;
	}

	public void setId(int id) {
		this.resortId = id;
	}

	public int getResortId() {
		return resortId;
	}

	public void setResortId(int resortId) {
		this.resortId = resortId;
	}

	public String getOfficialWebsite() {
		return officialWebsite;
	}

	public void setOfficialWebsite(String officialWebsite) {
		this.officialWebsite = officialWebsite;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSkiMapUrl() {
		return skiMapUrl;
	}

	public void setSkiMapUrl(String skiMapUrl) {
		this.skiMapUrl = skiMapUrl;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public int getLongestRun() {
		return longestRun;
	}

	public void setLongestRun(int longestRun) {
		this.longestRun = longestRun;
	}

	public boolean isTerrainPark() {
		return terrainPark;
	}

	public void setTerrainPark(boolean terrainPark) {
		this.terrainPark = terrainPark;
	}
	
	@Override
	public String toString() {
		String res = "";
		res += "<b>" + name + "</b>\n"; // name
		if ( officialWebsite != null && !officialWebsite.isEmpty()) {
			res += "<a href=\"" + officialWebsite + "\">" + officialWebsite + "</a>\n";
		}
		if (liftCount != 0) {
			res += "<b>Lift count:</b>" + Integer.toString(liftCount) + "\n";
		}
		if (longestRun != 0) {
			res += "<b>Longest run:</b>" + Integer.toString(longestRun) + " meters\n";
		}
		if (top != 0) {
			res += "<b>Top:</b>" + Integer.toString(top) + "m\n";
		}
		if (terrainPark) {
			res += "<b>Has Terrain Park!</b>\n";
		} 
		if (nightSkiing) {
			res += "<b>Night skiing is available!</b>\n";
		}
		res += "<a href=\"" + skiMapUrl + "\">Map</a>";
		return res;
	}
	
	
}
