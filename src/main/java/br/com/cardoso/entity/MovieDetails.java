package br.com.cardoso.entity;

import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticTableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.LocalDate;

@DynamoDbBean
public class MovieDetails {

    public MovieDetails(String id, String title, LocalDate year, String genre, String country, Integer duration, String language) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.country = country;
        this.duration = duration;
        this.language = language;
    }

    public MovieDetails() {
    }

    private String id;
    private String title;
    private LocalDate year;
    private String genre;
    private String country;
    private Integer duration;
    private String language;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    @DynamoDbAttribute("title")
    public String getTitle() {
        return title;
    }

    @DynamoDbAttribute("release_year")
    public LocalDate getYear() {
        return year;
    }

    @DynamoDbAttribute("genre")
    public String getGenre() {
        return genre;
    }

    @DynamoDbAttribute("country")
    public String getCountry() {
        return country;
    }

    @DynamoDbAttribute("duration")
    public Integer getDuration() {
        return duration;
    }

    @DynamoDbAttribute("language")
    public String getLanguage() {
        return language;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public static final TableSchema<MovieDetails> SCHEMA =
            StaticTableSchema.builder(MovieDetails.class)
                    .newItemSupplier(MovieDetails::new)
                    .addAttribute(String.class, a -> a.name("id")
                            .getter(MovieDetails::getId)
                            .setter(MovieDetails::setId)
                            .tags(StaticAttributeTags.primaryPartitionKey())
                    )
                    .addAttribute(String.class, a -> a.name("title")
                            .getter(MovieDetails::getTitle)
                            .setter(MovieDetails::setTitle)
                    )
                    .addAttribute(String.class, a -> a.name("release_year")
                            .getter(m -> m.getYear() != null ? m.getYear().toString() : null)
                            .setter((m, val) -> m.setYear(val != null ? java.time.LocalDate.parse(val) : null))
                    )
                    .addAttribute(String.class, a -> a.name("genre")
                            .getter(MovieDetails::getGenre)
                            .setter(MovieDetails::setGenre)
                    )
                    .addAttribute(String.class, a -> a.name("country")
                            .getter(MovieDetails::getCountry)
                            .setter(MovieDetails::setCountry)
                    )
                    .addAttribute(Integer.class, a -> a.name("duration")
                            .getter(MovieDetails::getDuration)
                            .setter(MovieDetails::setDuration)
                    )
                    .addAttribute(String.class, a -> a.name("language")
                            .getter(MovieDetails::getLanguage)
                            .setter(MovieDetails::setLanguage)
                    )
                    .build();

}