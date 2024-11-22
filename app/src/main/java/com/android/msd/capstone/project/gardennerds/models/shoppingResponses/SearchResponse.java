package com.android.msd.capstone.project.gardennerds.models.shoppingResponses;


import java.util.List;

public class SearchResponse {
//    private SearchMetadata searchMetadata;
//    private SearchParameters searchParameters;
//    private SearchInformation searchInformation;
//    private Filter[] filters;
//    private InlineShoppingResult[] inlineShoppingResults;
//    private ArrayList<ShoppingResult> shoppingResults;
//    private CategorizedShoppingResult[] categorizedShoppingResults;
//    private Pagination pagination;
//    private SerpapiPagination serpapiPagination;
//
//    @SerializedName("search_metadata")
//    public SearchMetadata getSearchMetadata() { return searchMetadata; }
//    @SerializedName("search_metadata")
//    public void setSearchMetadata(SearchMetadata value) { this.searchMetadata = value; }
//
//    @SerializedName("search_parameters")
//    public SearchParameters getSearchParameters() { return searchParameters; }
//    @SerializedName("search_parameters")
//    public void setSearchParameters(SearchParameters value) { this.searchParameters = value; }
//
//    @SerializedName("search_information")
//    public SearchInformation getSearchInformation() { return searchInformation; }
//    @SerializedName("search_information")
//    public void setSearchInformation(SearchInformation value) { this.searchInformation = value; }
//
//    @SerializedName("filters")
//    public Filter[] getFilters() { return filters; }
//    @SerializedName("filters")
//    public void setFilters(Filter[] value) { this.filters = value; }
//
//    @SerializedName("inline_shopping_results")
//    public InlineShoppingResult[] getInlineShoppingResults() { return inlineShoppingResults; }
//    @SerializedName("inline_shopping_results")
//    public void setInlineShoppingResults(InlineShoppingResult[] value) { this.inlineShoppingResults = value; }
//
//    @SerializedName("shopping_results")
//    public ArrayList<ShoppingResult> getShoppingResults() { return shoppingResults; }
//    @SerializedName("shopping_results")
//    public void setShoppingResults(ArrayList<ShoppingResult> value) { this.shoppingResults = value; }
//
//    @SerializedName("categorized_shopping_results")
//    public CategorizedShoppingResult[] getCategorizedShoppingResults() { return categorizedShoppingResults; }
//    @SerializedName("categorized_shopping_results")
//    public void setCategorizedShoppingResults(CategorizedShoppingResult[] value) { this.categorizedShoppingResults = value; }
//
//    @SerializedName("pagination")
//    public Pagination getPagination() { return pagination; }
//    @SerializedName("pagination")
//    public void setPagination(Pagination value) { this.pagination = value; }
//
//    @SerializedName("serpapi_pagination")
//    public SerpapiPagination getSerpapiPagination() { return serpapiPagination; }
//    @SerializedName("serpapi_pagination")
//    public void setSerpapiPagination(SerpapiPagination value) { this.serpapiPagination = value; }


    private List<ShoppingResult> inline_shopping_results;
    private List<ShoppingResult> shopping_results;

    public List<ShoppingResult> getInlineShoppingResults() {
        return inline_shopping_results;
    }

    public void setInlineShoppingResults(List<ShoppingResult> inline_shopping_results) {
        this.inline_shopping_results = inline_shopping_results;
    }

    public List<ShoppingResult> getShoppingResults() {
        return shopping_results;
    }

    public void setShoppingResults(List<ShoppingResult> shopping_results) {
        this.shopping_results = shopping_results;
    }
}
