package com.android.msd.capstone.project.gardennerds.models.productResponses;


public class ProductDetail {
    private SearchMetadata searchMetadata;
    private SearchParameters searchParameters;
    private ProductResults product_results;
    private SellersResults sellersResults;
    private SpecsResults specsResults;
    private ReviewsResults reviewsResults;



    public SearchMetadata getSearchMetadata() {
        return searchMetadata;
    }

    public void setSearchMetadata(SearchMetadata searchMetadata) {
        this.searchMetadata = searchMetadata;
    }

    public SearchParameters getSearchParameters() {
        return searchParameters;
    }

    public void setSearchParameters(SearchParameters searchParameters) {
        this.searchParameters = searchParameters;
    }

    public ProductResults getProductResults() {
        return product_results;
    }

    public void setProductResults(ProductResults productResults) {
        this.product_results = productResults;
    }

    public SellersResults getSellersResults() {
        return sellersResults;
    }

    public void setSellersResults(SellersResults sellersResults) {
        this.sellersResults = sellersResults;
    }

    public SpecsResults getSpecsResults() {
        return specsResults;
    }

    public void setSpecsResults(SpecsResults specsResults) {
        this.specsResults = specsResults;
    }

    public ReviewsResults getReviewsResults() {
        return reviewsResults;
    }

    public void setReviewsResults(ReviewsResults reviewsResults) {
        this.reviewsResults = reviewsResults;
    }
//    @JsonProperty("search_metadata")
//    public SearchMetadata getSearchMetadata() { return searchMetadata; }
//    @JsonProperty("search_metadata")
//    public void setSearchMetadata(SearchMetadata value) { this.searchMetadata = value; }
//
//    @JsonProperty("search_parameters")
//    public SearchParameters getSearchParameters() { return searchParameters; }
//    @JsonProperty("search_parameters")
//    public void setSearchParameters(SearchParameters value) { this.searchParameters = value; }
//
//    @JsonProperty("product_results")
//    public ProductResults getProductResults() { return productResults; }
//    @JsonProperty("product_results")
//    public void setProductResults(ProductResults value) { this.productResults = value; }
//
//    @JsonProperty("sellers_results")
//    public SellersResults getSellersResults() { return sellersResults; }
//    @JsonProperty("sellers_results")
//    public void setSellersResults(SellersResults value) { this.sellersResults = value; }
//
//    @JsonProperty("specs_results")
//    public SpecsResults getSpecsResults() { return specsResults; }
//    @JsonProperty("specs_results")
//    public void setSpecsResults(SpecsResults value) { this.specsResults = value; }
//
//    @JsonProperty("reviews_results")
//    public ReviewsResults getReviewsResults() { return reviewsResults; }
//    @JsonProperty("reviews_results")
//    public void setReviewsResults(ReviewsResults value) { this.reviewsResults = value; }
}
