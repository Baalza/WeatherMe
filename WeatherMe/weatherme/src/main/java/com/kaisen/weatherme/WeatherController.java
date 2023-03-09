package com.kaisen.weatherme;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Controller
public class WeatherController {
    Weather weather = new Weather();
  /*@RequestMapping("/home")
    public String home(){
        return "index";
    }*/
    /*@GetMapping("/weather")
    public String rediect(){
        return "forward:/";
    }*/
    @GetMapping("/")
    //@RequestMapping("")
    public   ModelAndView addWork(@RequestParam(required=false,name="city") String city) {
        ModelAndView mav = new ModelAndView("index.html");
        weather.setCountry("");
        if(city != null){
        //System.out.println(city);
        //https://api.openweathermap.org/data/2.5/weather?q=&units=metric&appid=8299ccea1c76916a244d89c3049ae33f
        String api = "https://api.openweathermap.org/data/2.5/weather?q=&units=metric&appid=8299ccea1c76916a244d89c3049ae33f";
        String appApi [] = api.split("q=");
        //System.out.println(appApi[0]+" "+appApi[1]);
        StringBuilder assemble = new StringBuilder();
        assemble.append(appApi[0]).append("q=").append(city).append(appApi[1]);
        api = assemble.toString();
        //System.out.println(assemble);
        RestTemplate restTemplate = new RestTemplate();
        try{
        Object grid = restTemplate.getForObject(api, Object.class);
        //System.out.println(grid.toString());
        
        JsonObject data = new Gson().fromJson(grid.toString().replaceAll(" ", "-"), JsonObject.class);
        //System.out.println(data);
        JsonArray temp = data .get("-weather").getAsJsonArray();
        for(JsonElement element : temp){
            JsonObject object = element.getAsJsonObject();
            String main = object.getAsJsonObject().get("-main").getAsString().toLowerCase();
            if(main.charAt(main.length()-1) == 's')
               main = main.substring(0, main.length()-1);
            weather.setWeather(main);
            weather.setDesc(object.getAsJsonObject().get("-description").getAsString().replaceAll("-", " ").toUpperCase());
        }
        JsonElement temp2 = data .get("-main");
            JsonObject object = temp2.getAsJsonObject();
            weather.setGradi(object.getAsJsonObject().get("temp").getAsString());
            weather.setPerc(object.getAsJsonObject().get("-feels_like").getAsString());
            weather.setHumidity(object.getAsJsonObject().get("-humidity").getAsString());
        
        temp2 = data .get("-wind");
            object = temp2.getAsJsonObject();
            weather.setWind(object.getAsJsonObject().get("speed").getAsString());
        String appCity[] = city.split(",");
        weather.setCity(appCity[0].toUpperCase());
        if(appCity.length == 2){
        weather.setCountry(","+appCity[1].toUpperCase());
        System.out.println(weather.getCountry());
        }
        weather.setCod(200);
        System.out.println(weather.getCity());
        System.out.println(weather.getCountry());
        System.out.println(weather.getGradi());
        System.out.println(weather.getPerc());
        System.out.println(weather.getWeather());
        System.out.println(weather.getDesc());
        System.out.println(weather.getHumidity());
        System.out.println(weather.getWind());
        System.out.println(weather.getCod());
    }catch(Exception e){
        System.out.println(e);
        weather.setCod(404);
        weather.setCity("");
        weather.setCountry("");
        weather.setDesc("");
        weather.setGradi("");
        weather.setHumidity("");
        weather.setPerc("");
        weather.setWeather("");
        weather.setWind("");
        System.out.println(weather.getCity());
        System.out.println(weather.getCountry());
        System.out.println(weather.getGradi());
        System.out.println(weather.getPerc());
        System.out.println(weather.getWeather());
        System.out.println(weather.getDesc());
        System.out.println(weather.getHumidity());
        System.out.println(weather.getWind());
        System.out.println(weather.getCod());
    }
       
        mav.addObject("weather", weather);
       
        return mav;
    }
        //rediect();
        mav.addObject("weather", new Weather());
        return mav;
        }

    }

