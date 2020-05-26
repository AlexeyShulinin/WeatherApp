public class CurrentWeatherInformation {
    private String city;
    private MainInformation mainInformation;
    private Temperature temperature;
    private Weather weather;
    private Сoordinates coordinates;
    private Wind wind;

    CurrentWeatherInformation(){
        this.mainInformation = new MainInformation();
        this.temperature = new Temperature();
        this.weather = new Weather();
        this.coordinates = new Сoordinates();
        this.wind = new Wind();
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public MainInformation getMainInformation() {
        return mainInformation;
    }

    public void setMainInformation(int humidity,int pressure,int visibility,int cloudiness) {
        this.mainInformation.setHumidity(humidity);
        this.mainInformation.setPressure(pressure);
        this.mainInformation.setVisibility(visibility);
        this.mainInformation.setCloudiness(cloudiness);
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature,double feelTemperature,double temperatureMin,double temperatureMax) {
        this.temperature.setTemperature(temperature);
        this.temperature.setFeelTemperature(feelTemperature);
        this.temperature.setTemperatureMin(temperatureMin);
        this.temperature.setTemperatureMax(temperatureMax);
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(int ID,String parameters,String description) {
        this.weather.setID(ID);
        this.weather.setParameters(parameters);
        this.weather.setDescription(description);
    }

    public Сoordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double longitude,double latitude) {
        this.coordinates.setLongitude(longitude);
        this.coordinates.setLatitude(latitude);
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(int speed,int degrees,int gust) {
        this.wind.setSpeed(speed);
        this.wind.setDegrees(degrees);
        this.wind.setGust(gust);
    }

    public void printWeather(){
        System.out.println("");
    }
}
