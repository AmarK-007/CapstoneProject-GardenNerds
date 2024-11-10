package com.android.msd.capstone.project.gardennerds.models.productResponses;

public class Converter {

//    private static final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm:ss");
//
//    static {
//        DATE_TIME_FORMATTER.setTimeZone(TimeZone.getTimeZone("UTC"));
//        TIME_FORMATTER.setTimeZone(TimeZone.getTimeZone("UTC"));
//    }
//
//    // Date parsing for date-time strings
//    public static Date parseDateTimeString(String str) {
//        try {
//            return DATE_TIME_FORMATTER.parse(str);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    // Date parsing for time-only strings
//    public static Date parseTimeString(String str) {
//        try {
//            return TIME_FORMATTER.parse(str);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    // Deserialize JSON string to ProductDetail object
//    public static ProductDetail fromJsonString(String json) {
//        try {
//            JSONObject jsonObject = new JSONObject(json);
//            ProductDetail productDetail = new ProductDetail();
//
//            // Assuming ProductDetail has fields like `name` and `price`
//            productDetail.setName(jsonObject.optString("name"));
//            productDetail.setPrice(jsonObject.optDouble("price"));
//            // Parse date field if it exists
//            String dateStr = jsonObject.optString("date");
//            if (!dateStr.isEmpty()) {
//                productDetail.setDate(parseDateTimeString(dateStr));
//            }
//
//            return productDetail;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    // Serialize ProductDetail object to JSON string
//    public static String toJsonString(ProductDetail obj) {
//        try {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("name", obj.getName());
//            jsonObject.put("price", obj.getPrice());
//
//            // Format date field if it's not null
//            if (obj.getDate() != null) {
//                jsonObject.put("date", DATE_TIME_FORMATTER.format(obj.getDate()));
//            }
//
//            return jsonObject.toString();
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}
