package com.techelevator.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.Landmark;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@Component
public class GooglePlacesService {

    private String apiUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json?key=";
    private String key = "AIzaSyD-FoV_A8s-yXMebNAb6ulZO3kvd6dY8tg";
//   key original private String key = "AIzaSyBawfx8Z8v5BBpahbC1Q8UVzOOdZpA3p54";
    private String query = "&query=philadelphia+";
    private String params = "&fields=name,photos,formatted_address,types,opening_hours,formatted_phone_number,website,rating,reviews";
    private String photoUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=1000&key=";
    private String detailsUrl= "https://maps.googleapis.com/maps/api/place/details/json?key=";


    public Landmark getApiResults(String searchQuery){
        Landmark result = new Landmark();

        String url = this.apiUrl + this.key + this.query + searchQuery + this.params;

        HttpEntity<String> httpEntity = new HttpEntity<>("");
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
//        System.out.println(response.getBody());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;

        try {
            jsonNode = objectMapper.readTree(response.getBody());
            JsonNode apiResults = jsonNode.path("results");

            for (int i = 0; i < apiResults.size(); i++) {


                String name = apiResults.path(i).path("name").asText();
                String photo = this.photoUrl + this.key + "&photo_reference=" + apiResults.path(i).path("photos").path(0).path("photo_reference").asText();
                //TODO
                String googleId = "&place_id=" + apiResults.path(i).path("place_id").asText();
                String descriptionCall = this.detailsUrl + this.key + "&fields=editorial_summary,opening_hours" + googleId;
                //System.out.println(descriptionCall);
                    ResponseEntity<String> getDescription = restTemplate.exchange(descriptionCall, HttpMethod.GET, httpEntity, String.class);
                    String descriptionResult = objectMapper.readTree(getDescription.getBody()).path("result").path("editorial_summary").path("overview").asText();
                //System.out.println(descriptionResult);
                String description = descriptionResult;
                String address = apiResults.path(i).path("formatted_address").asText();
                    JsonNode hoursListFromCall = objectMapper.readTree(getDescription.getBody()).path("result").path("opening_hours").path("weekday_text");
                    List<String> hoursList = new ArrayList<>();
                    for (int j = 0; j < hoursListFromCall.size(); j++) {
                        hoursList.add(hoursListFromCall.path(j).asText());
                }
//                System.out.println(hoursList.toString());
                String hoursOfOperation = hoursList.toString().replace("[","").replace("]","");
                String venueType = apiResults.path(i).path("types").path(0).asText();
//                System.out.println(hoursOfOperation);


                Landmark landmark = new Landmark(name, photo, description, address, hoursOfOperation, venueType);

                result = landmark;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }
}
