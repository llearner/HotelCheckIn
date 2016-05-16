package cn.edu.xmu.ultraci.hotelcheckin.server.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 对“初始化”请求的响应
 * 
 * @author LuoXin
 *
 */
public class InitDTO extends BaseDTO implements Serializable {
	private static final long serialVersionUID = -4763259596966441943L;

	private Upgrade upgrade;
	private String announcement;
	private List<Advertisement> ads;

	public InitDTO() {
		upgrade = new Upgrade();
		ads = new ArrayList<Advertisement>();
	}

	public Upgrade getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(Upgrade upgrade) {
		this.upgrade = upgrade;
	}

	public void setUpgrade(Integer version, Long size, String url, String md5) {
		Upgrade upgrade = new Upgrade();
		upgrade.setVersion(version);
		upgrade.setSize(size);
		upgrade.setUrl(url);
		upgrade.setMd5(md5);
		setUpgrade(upgrade);
	}

	public String getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}

	public List<Advertisement> getAds() {
		return ads;
	}

	public void setAds(List<Advertisement> ads) {
		this.ads = ads;
	}

	public void addAd(String url, String md5) {
		Advertisement ad = new Advertisement();
		ad.setUrl(url);
		ad.setMd5(md5);
		ads.add(ad);
	}

	public class Upgrade implements Serializable {
		private static final long serialVersionUID = -2281405341776197322L;

		private Integer version;
		private Long size;
		private String url;
		private String md5;

		public Integer getVersion() {
			return version;
		}

		public void setVersion(Integer version) {
			this.version = version;
		}

		public Long getSize() {
			return size;
		}

		public void setSize(Long size) {
			this.size = size;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getMd5() {
			return md5;
		}

		public void setMd5(String md5) {
			this.md5 = md5;
		}
	}

	public class Advertisement implements Serializable {

		private static final long serialVersionUID = 598973986516570093L;

		private String url;
		private String md5;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getMd5() {
			return md5;
		}

		public void setMd5(String md5) {
			this.md5 = md5;
		}
	}
}