package com.kaisen.weatherme;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weather {

    private String city="";
    private String country="";
    private String weather="";
    private String gradi="";
    private String desc="";
    private String humidity="";
    private String wind="";
    private String perc="";
    private int cod=0;
}
