package it.lefosse.object;

public class DistrictPop {
	
	private String district;
	private int population;
	
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public int getPopulation() {
		return population;
	}
	public void setPopulation(int population) {
		this.population = population;
	}
	public DistrictPop(String district, int population) {
		this.district = district;
		this.population = population;
	}
	
	

}
