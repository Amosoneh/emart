package com.emart.emart.services;

import com.emart.emart.datas.models.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
@RequiredArgsConstructor
public class ElasticSearchQueryService {
    private final ElasticsearchOperations elasticsearchOperations;
    private final String INDEX_NAME = "products";

    public List<Product> processSearch(String query) {
        log.info("Search with query {}", query);

        Pageable pageable = Pageable.ofSize(10);
        NativeQuery searchQuery = new NativeQueryBuilder()
                .withQuery(q -> q.multiMatch(m -> m
                        .fields("name", "description")
                        .query(query)
                        .fuzziness("AUTO")))
                .withSort(Sort.by(Sort.Direction.ASC, "name"))
                .withPageable(pageable)
                .build();

        // 2. Execute search
        SearchHits<Product> productHits =
                elasticsearchOperations
                        .search(searchQuery, Product.class,
                                IndexCoordinates.of(INDEX_NAME));

        // 3. Map searchHits to product list
        List<Product> productMatches = new ArrayList<>();
        productHits.forEach(searchHit -> {
            productMatches.add(searchHit.getContent());
        });
        return productMatches;
    }
}
