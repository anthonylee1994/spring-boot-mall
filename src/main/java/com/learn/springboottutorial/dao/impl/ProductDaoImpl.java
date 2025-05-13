package com.learn.springboottutorial.dao.impl;

import com.learn.springboottutorial.dao.ProductDao;
import com.learn.springboottutorial.dto.ProductQueryParams;
import com.learn.springboottutorial.dto.ProductRequest;
import com.learn.springboottutorial.model.Product;
import com.learn.springboottutorial.rowmapper.ProductRowMapper;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author anthonylee
 */
@Repository
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        String baseSql = "SELECT COUNT(*) FROM mall.product WHERE 1=1";

        StringBuilder sqlBuilder = new StringBuilder(baseSql);
        Map<String, Object> params = new HashMap<>();
        sqlBuilder = new StringBuilder(addFilteringSql(sqlBuilder, params, productQueryParams));

        return namedParameterJdbcTemplate.queryForObject(sqlBuilder.toString(), params, Integer.class);
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        String baseSql = "SELECT * FROM mall.product WHERE 1=1";
        StringBuilder sqlBuilder = new StringBuilder(baseSql);
        Map<String, Object> params = new HashMap<>();

        // query
        sqlBuilder = new StringBuilder(addFilteringSql(sqlBuilder, params, productQueryParams));

        // order by
        String orderBy = validateOrderBy(productQueryParams.getOrderBy());
        String sort = validateSort(productQueryParams.getSort());

        sqlBuilder.append(" ORDER BY ").append(orderBy).append(" ").append(sort);

        // pagination
        sqlBuilder.append(" LIMIT :limit OFFSET :offset");
        params.put("limit", productQueryParams.getLimit());
        params.put("offset", productQueryParams.getOffset());

        return namedParameterJdbcTemplate.query(sqlBuilder.toString(), params, new ProductRowMapper());
    }

    @Override
    public Product getProductById(Integer id) {
        String sql = "select * from mall.product where product_id = :productId";

        Map<String, Object> params = new HashMap<>();
        params.put("productId", id);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, params, new ProductRowMapper());

        if (!productList.isEmpty()) {
            return productList.getFirst();
        } else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO mall.product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES (:productName, :category, :imageUrl, :price, :stock, :description, :createdDate, :lastModifiedDate)";
        Map<String, Object> params = new HashMap<>();
        params.put("productName", productRequest.getProductName());
        params.put("category", productRequest.getCategory().toString());
        params.put("imageUrl", productRequest.getImageUrl());
        params.put("price", productRequest.getPrice());
        params.put("stock", productRequest.getStock());
        params.put("description", productRequest.getDescription());

        Date now = new Date();
        params.put("createdDate", now);
        params.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql = """
                UPDATE mall.product
                SET product_name       = :productName,
                    category           = :category,
                    image_url          = :imageUrl,
                    price              = :price,
                    stock              = :stock,
                    description        = :description,
                    last_modified_date = :lastModifiedDate
                WHERE product_id = :productId;
                """;

        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId);
        params.put("productName", productRequest.getProductName());
        params.put("category", productRequest.getCategory().toString());
        params.put("imageUrl", productRequest.getImageUrl());
        params.put("price", productRequest.getPrice());
        params.put("stock", productRequest.getStock());
        params.put("description", productRequest.getDescription());
        params.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM mall.product WHERE product_id = :productId";

        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, params);
    }

    private String validateOrderBy(String orderBy) {
        List<String> allowedColumns = Arrays.asList("product_id", "product_name", "price", "created_date");
        if (StringUtils.isEmpty(orderBy) || !allowedColumns.contains(orderBy)) {
            return "created_date";
        }
        return orderBy;
    }

    private String validateSort(String sort) {
        if (StringUtils.isEmpty(sort) || (!sort.equalsIgnoreCase("ASC") && !sort.equalsIgnoreCase("DESC"))) {
            return "ASC";
        }
        return sort.toUpperCase();
    }

    private String addFilteringSql(StringBuilder sqlBuilder, Map<String, Object> params, ProductQueryParams productQueryParams) {
        if (productQueryParams.getCategory() != null) {
            sqlBuilder.append(" AND category = :category");
            params.put("category", productQueryParams.getCategory().name());
        }

        if (productQueryParams.getSearch() != null) {
            sqlBuilder.append(" AND product_name LIKE :search");
            params.put("search", "%" + productQueryParams.getSearch() + "%");
        }

        return sqlBuilder.toString();
    }
}
