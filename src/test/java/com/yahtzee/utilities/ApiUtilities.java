package com.yahtzee.utilities;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class ApiUtilities {


    public static int wordToInt(String word) {
        switch (word) {
            case "one":
                return 1;
            case "two":
                return 2;
            case "three":
                return 3;
            case "four":
                return 4;
            case "five":
                return 5;
            case "six":
                return 6;
            default:
                throw new IllegalArgumentException("Invalid word representation: " + word);
        }
    }

    public static int dotsToInt(String dots) {
        switch (dots) {
            case ".":
                return 1;
            case "..":
                return 2;
            case "...":
                return 3;
            case "....":
                return 4;
            case ".....":
                return 5;
            case "......":
                return 6;
            default:
                throw new IllegalArgumentException("Invalid dots representation: " + dots);
        }
    }

}