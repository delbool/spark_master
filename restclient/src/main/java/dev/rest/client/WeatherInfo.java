package dev.rest.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize
public class WeatherInfo {

	public final class Coord {
		private double lon;
		private double lat;

		public double getLon() {
			return lon;
		}

		public void setLon(double lon) {
			this.lon = lon;
		}

		public double getLat() {
			return lat;
		}

		public void setLat(double lat) {
			this.lat = lat;
		}

		@Override
		public String toString() {
			return "Coord [lon=" + lon + ", lat=" + lat + "]";
		}

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public final class Weather {
		private int id;
		private String main;
		private String description;
		private String icon;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getMain() {
			return main;
		}

		public void setMain(String main) {
			this.main = main;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		@Override
		public String toString() {
			return "Weather [id=" + id + ", main=" + main + ", description=" + description + ", icon=" + icon + "]";
		}

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public final class Main {
		private double temp;
		private double pressure;
		private double humidity;
		private double temp_min;
		private double temp_max;
		private double sea_level;
		private double grnd_level;

		public double getTemp() {
			return temp;
		}

		public void setTemp(double temp) {
			this.temp = temp;
		}

		public double getPressure() {
			return pressure;
		}

		public void setPressure(double pressure) {
			this.pressure = pressure;
		}

		public double getHumidity() {
			return humidity;
		}

		public void setHumidity(double humidity) {
			this.humidity = humidity;
		}

		public double getTemp_min() {
			return temp_min;
		}

		public void setTemp_min(double temp_min) {
			this.temp_min = temp_min;
		}

		public double getTemp_max() {
			return temp_max;
		}

		public void setTemp_max(double temp_max) {
			this.temp_max = temp_max;
		}

		public double getSea_level() {
			return sea_level;
		}

		public void setSea_level(double sea_level) {
			this.sea_level = sea_level;
		}

		public double getGrnd_level() {
			return grnd_level;
		}

		public void setGrnd_level(double grnd_level) {
			this.grnd_level = grnd_level;
		}

		@Override
		public String toString() {
			return "Main [temp=" + temp + ", pressure=" + pressure + ", humidity=" + humidity + ", temp_min=" + temp_min
					+ ", temp_max=" + temp_max + ", sea_level=" + sea_level + ", grnd_level=" + grnd_level + "]";
		}

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public final class Wind {
		private double speed;
		private double deg;

		public double getSpeed() {
			return speed;
		}

		public void setSpeed(double speed) {
			this.speed = speed;
		}

		public double getDeg() {
			return deg;
		}

		public void setDeg(double deg) {
			this.deg = deg;
		}

		@Override
		public String toString() {
			return "Wind [speed=" + speed + ", deg=" + deg + "]";
		}

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public final class Clouds {
		private int all;

		public int getAll() {
			return all;
		}

		public void setAll(int all) {
			this.all = all;
		}

		@Override
		public String toString() {
			return "Clouds [all=" + all + "]";
		}

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public final class Sys {
		private double message;
		private String country;
		private int sunrise;
		private int sunset;

		public double getMessage() {
			return message;
		}

		public void setMessage(double message) {
			this.message = message;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public int getSunrise() {
			return sunrise;
		}

		public void setSunrise(int sunrise) {
			this.sunrise = sunrise;
		}

		public int getSunset() {
			return sunset;
		}

		public void setSunset(int sunset) {
			this.sunset = sunset;
		}

		@Override
		public String toString() {
			return "Sys [message=" + message + ", country=" + country + ", sunrise=" + sunrise + ", sunset=" + sunset
					+ "]";
		}

	}

	private String base;
	private long dt;
	private int id;
	private String name;
	private int cod;

	// objects
	private Coord coord;
	private Weather weather;
	private Main main;
	private Wind wind;
	private Clouds clouds;
	private Sys sys;

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public long getDt() {
		return dt;
	}

	public void setDt(long dt) {
		this.dt = dt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	// getter/setter for inner class instances
	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public Weather getWeather() {
		return weather;
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public Wind getWind() {
		return wind;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}

	public Clouds getClouds() {
		return clouds;
	}

	public void setClouds(Clouds clouds) {
		this.clouds = clouds;
	}

	public Sys getSys() {
		return sys;
	}

	public void setSys(Sys sys) {
		this.sys = sys;
	}

	@Override
	public String toString() {
		return "WeatherInfo [base=" + base + ", dt=" + dt + ", id=" + id + ", name=" + name + ", cod=" + cod
				+ ", coord=" + coord + ", weather=" + weather + ", main=" + main + ", wind=" + wind + ", clouds="
				+ clouds + ", sys=" + sys + "]";
	}

}
