package br.com.cardoso.controller;

import br.com.cardoso.entity.MovieDetails;
import br.com.cardoso.service.DynamoDbOperationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController {

    private final DynamoDbOperationService dynamoDbOperationService;

    public MovieController(DynamoDbOperationService dynamoDbOperationService) {
        this.dynamoDbOperationService = dynamoDbOperationService;
    }


    @PostMapping("/movie")
    public MovieDetails saveMovie(@RequestBody MovieDetails movieDetails) {
        return dynamoDbOperationService.saveData(movieDetails);
    }

    @PutMapping("/movie")
    public MovieDetails updateMovie(@RequestBody MovieDetails movieDetails) {
        return dynamoDbOperationService.updateData(movieDetails);
    }

    @GetMapping("/movie/{id}")
    public MovieDetails findById(@PathVariable("id") String id) {
        return dynamoDbOperationService.findById(id);
    }

    @DeleteMapping("/movie/{id}")
    public void deleteById(@PathVariable("id") String id) {
        dynamoDbOperationService.deleteById(id);
    }

    @GetMapping("/movie")
    public List<MovieDetails> getMovieListByGenre(@RequestParam("genre") String genre) {
        return dynamoDbOperationService.scanDataByGenre(genre);
    }
}
