package com.vogella.android.temperatureconverter;

/**
 * Created by Byku on 11.04.2016.
 */
public class ConverterUtil {
    //converts to celsius
    public static float convertFahrenheitToCelsius(float fahrenheit){
        return ((fahrenheit-32)*5/9);
    }

    //converts to fahrenheit
    public static float convertCelsiusToFahrenheit(float celsius){
        return ((celsius*9)/5)+32;
    }
}
