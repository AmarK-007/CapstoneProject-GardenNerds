package com.android.msd.capstone.project.gardennerds.models.productResponses;


public class ProductDetail {
    private SearchMetadata search_metadata;
    private SearchParameters search_parameters;
    private ProductResults product_results;
    private SellersResults sellers_results;
    private RelatedProduct related_products;
    private SpecsResults specs_results;
    private ReviewsResults reviewsResults;



    public SearchMetadata getSearchMetadata() {
        return search_metadata;
    }

    public void setSearchMetadata(SearchMetadata searchMetadata) {
        this.search_metadata = searchMetadata;
    }

    public SearchParameters getSearchParameters() {
        return search_parameters;
    }

    public void setSearchParameters(SearchParameters searchParameters) {
        this.search_parameters = searchParameters;
    }

    public ProductResults getProductResults() {
        return product_results;
    }

    public void setProductResults(ProductResults productResults) {
        this.product_results = productResults;
    }

    public SellersResults getSellersResults() {
        return sellers_results;
    }

    public void setSellersResults(SellersResults sellersResults) {
        this.sellers_results = sellersResults;
    }

    public SpecsResults getSpecsResults() {
        return specs_results;
    }

    public void setSpecsResults(SpecsResults specsResults) {
        this.specs_results = specsResults;
    }

    public ReviewsResults getReviewsResults() {
        return reviewsResults;
    }

    public void setReviewsResults(ReviewsResults reviewsResults) {
        this.reviewsResults = reviewsResults;
    }

    public RelatedProduct getRelated_products() {
        return related_products;
    }

    public void setRelated_products(RelatedProduct related_products) {
        this.related_products = related_products;
    }
}
