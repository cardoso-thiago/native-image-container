package br.com.cardoso.service;

import br.com.cardoso.entity.MovieDetails;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DynamoDbOperationService {

    private final DynamoDbTable<MovieDetails> movieTable;

    public DynamoDbOperationService(DynamoDbEnhancedClient enhancedClient) {
        this.movieTable = enhancedClient.table("movie_details", MovieDetails.SCHEMA);
    }

    public MovieDetails saveData(MovieDetails movieDetails) {
        movieTable.putItem(movieDetails);
        return movieDetails;
    }

    public MovieDetails updateData(MovieDetails movieDetails) {
        movieTable.updateItem(movieDetails);
        return movieDetails;
    }

    public void deleteById(String id) {
        Key key = Key.builder().partitionValue(id).build();
        movieTable.deleteItem(r -> r.key(key));
    }

    public MovieDetails findById(String id) {
        Key key = Key.builder().partitionValue(id).build();
        return movieTable.getItem(r -> r.key(key));
    }

    public List<MovieDetails> scanDataByGenre(String genre) {
        Map<String, AttributeValue> expressionValues = Map.of(":val1", AttributeValue.fromS(genre));
        Expression filterExpression = Expression.builder()
                .expression("genre = :val1")
                .expressionValues(expressionValues)
                .build();

        ScanEnhancedRequest request = ScanEnhancedRequest.builder()
                .filterExpression(filterExpression)
                .build();

        return movieTable.scan(request)
                .items()
                .stream()
                .collect(Collectors.toList());
    }
}
