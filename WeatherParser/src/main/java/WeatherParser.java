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
            String city = "Minsk";
            String key = "b53326a51f0e88541b3e496bfacfdd85";
            String requestString = "http://api.openweathermap.org/data/2.5/weather?q="+ city +"&appid="+key;
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

        String city = "Minsk";
        String key = "b53326a51f0e88541b3e496bfacfdd85";
        String requestString = "http://api.openweathermap.org/data/2.5/weather?q="+ city +"&appid="+key;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestString)).build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                //.thenApply(WeatherParser::parse)
                .thenAccept(System.out::println)
                .join();

    }
    public static String parse(String responseBody){
        String jsonResponseBody = "[" + responseBody + "]";
        JSONArray albums = new JSONArray(jsonResponseBody);
        for (int i = 0; i < albums.length(); i++){
            JSONObject album = albums.getJSONObject(i);
            String weatherID = album.getString("name");
            System.out.println(weatherID);
        }
        return null;
    }
}
