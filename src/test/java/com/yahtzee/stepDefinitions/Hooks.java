package com.yahtzee.stepDefinitions;


import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;

import static io.restassured.RestAssured.*;

public class Hooks {




   @Before
public void setUp(){
       baseURI = "http://localhost:8080" ;

   }









}
