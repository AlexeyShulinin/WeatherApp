//import sun.net.www.http.HttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherParser {

    private static HttpURLConnection connection;

   public static void main(String[] args){
        /*//String url = "api.openweathermap.org/data/2.5/weather?q={Minsk}&appid={b53326a51f0e88541b3e496bfacfdd85}";

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer("[");
        try {
            String cityMinsk = "Minsk";
            String key = "b53326a51f0e88541b3e496bfacfdd85";
            String requestString = "http://api.openweathermap.org/data/2.5/weather?q="+ cityMinsk +"&appid="+key;
            URL urlMinsk = new URL(requestString);
            connection = (HttpURLConnection) urlMinsk.openConnection();

            //Request setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            System.out.println(status);

            if (status > 299){
                //Using reader for error message
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
                responseContent.append("]");
            }

           // System.out.println(responseContent.toString());

            parse(responseContent.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }*/

        String[] cities = new String[4];
        cities[0] = "Minsk";
        cities[1] = "Hrodna";
        cities[2] = "Vitebsk";
        cities[3] = "Mahilyow";
        String key = "b53326a51f0e88541b3e496bfacfdd85";
       System.out.println("City     Coordinates     Temperature     WindSpeed     Pressure     Humimdity");
        for(int i = 0;i < cities.length;i++) {
            String requestString = "http://api.openweathermap.org/data/2.5/weather?q=" + cities[i] + "&appid=" + key;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestString)).build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(WeatherParser::parse)
                    //.thenAccept(System.out::println)
                    .join();
        }

    }
    public static String parse(String responseBody){
        JSONObject obj = new JSONObject(responseBody);
        CurrentWeatherInformation weatherInformation = new CurrentWeatherInformation();

        weatherInformation.setCoordinates(obj.getJSONObject("coord").getDouble("lon"),obj.getJSONObject("coord").getDouble("lat"));

        weatherInformation.setWeather(obj.getJSONArray("weather").getJSONObject(0).getInt("id"),
                obj.getJSONArray("weather").getJSONObject(0).getString("main"),
                obj.getJSONArray("weather").getJSONObject(0).getString("description"));

        weatherInformation.setTemperature(obj.getJSONObject("main").getDouble("temp") - 273.15,
                obj.getJSONObject("main").getDouble("feels_like") - 273.15,
                obj.getJSONObject("main").getDouble("temp_min") - 273.15,
                obj.getJSONObject("main").getDouble("temp_max") - 273.15);

        weatherInformation.setMainInformation(obj.getJSONObject("main").getInt("pressure"),
                obj.getJSONObject("main").getInt("humidity"),
                0,
                obj.getJSONObject("clouds").getInt("all"));

        weatherInformation.setCity(obj.getString("name"));

        weatherInformation.setWind(obj.getJSONObject("wind").getInt("speed"),
                obj.getJSONObject("wind").getInt("deg"),
                0);

        System.out.println(weatherInformation.getCity()+"    "
                +weatherInformation.getCoordinates().getLongitude()+"|"+weatherInformation.getCoordinates().getLatitude()+"       "
                +weatherInformation.getTemperature().getTemperature()+"           "
                +weatherInformation.getWind().getSpeed()+"             "
                +weatherInformation.getMainInformation().getPressure()+"           "
                +weatherInformation.getMainInformation().getHumidity());
        return null;
    }
}
