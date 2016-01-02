package myFrameU.statistics.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 统计
 * 		流量统计
 * 			1、今日访问PV
 * 			2、今日访问UV
 * 			3、今日访问PV/UV
 * 			4、今日购买转化率。成交的U/总U
 * 		销售统计
 * 			1、今日成交额
 * 			2、今日成交量
 * 			3、今日成交人数
 * 			4、今日客单价 ，成交金额/成交人数
 *
 */
public class Statistics   implements Serializable{
	private int id;
	private String whoclassName;
	private int whoId;
	private String whoName;
	
	
	private int flow_pv;
	private int flow_uv;
	private float flow_pvCuv;
	private float flow_tradeUCallU;
	
	
	private float sale_tradeAllPrice;
	private int sale_tradeCount;
	private int sale_tradeUserCount;
	private float sale_tradePriceCtradeUserCount;
	
	
	
	private Date relDate;
	
	
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWhoclassName() {
		return whoclassName;
	}
	public void setWhoclassName(String whoclassName) {
		this.whoclassName = whoclassName;
	}
	
	public int getWhoId() {
		return whoId;
	}
	public void setWhoId(int whoId) {
		this.whoId = whoId;
	}
	public String getWhoName() {
		return whoName;
	}
	public void setWhoName(String whoName) {
		this.whoName = whoName;
	}
	public int getFlow_pv() {
		return flow_pv;
	}
	public void setFlow_pv(int flow_pv) {
		this.flow_pv = flow_pv;
	}
	public int getFlow_uv() {
		return flow_uv;
	}
	public void setFlow_uv(int flow_uv) {
		this.flow_uv = flow_uv;
	}
	public float getFlow_pvCuv() {
		return flow_pvCuv;
	}
	public void setFlow_pvCuv(float flow_pvCuv) {
		this.flow_pvCuv = flow_pvCuv;
	}
	public float getFlow_tradeUCallU() {
		return flow_tradeUCallU;
	}
	public void setFlow_tradeUCallU(float flow_tradeUCallU) {
		this.flow_tradeUCallU = flow_tradeUCallU;
	}
	public float getSale_tradeAllPrice() {
		return sale_tradeAllPrice;
	}
	public void setSale_tradeAllPrice(float sale_tradeAllPrice) {
		this.sale_tradeAllPrice = sale_tradeAllPrice;
	}
	public int getSale_tradeCount() {
		return sale_tradeCount;
	}
	public void setSale_tradeCount(int sale_tradeCount) {
		this.sale_tradeCount = sale_tradeCount;
	}
	public int getSale_tradeUserCount() {
		return sale_tradeUserCount;
	}
	public void setSale_tradeUserCount(int sale_tradeUserCount) {
		this.sale_tradeUserCount = sale_tradeUserCount;
	}
	public float getSale_tradePriceCtradeUserCount() {
		return sale_tradePriceCtradeUserCount;
	}
	public void setSale_tradePriceCtradeUserCount(
			float sale_tradePriceCtradeUserCount) {
		this.sale_tradePriceCtradeUserCount = sale_tradePriceCtradeUserCount;
	}
	public Date getRelDate() {
		return relDate;
	}
	public void setRelDate(Date relDate) {
		this.relDate = relDate;
	}
	
	
	
	
	
	
	
	
	
	
}
