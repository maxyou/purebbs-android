package com.maxproj.purebbs.net

import com.google.gson.annotations.SerializedName

class HttpData {

    data class ExpressJson(val msg:String)

    data class PostListRet(val code:Int, val message:String, val data:List<String>, val totalDocs:Int)

    data class CnNodeTopics(
        val success:Boolean,
        val data:Array<Content>
    ){
        data class Content(
            val id:String,
            val author_id:String,
            val tab:String,
//            val content:String,
            val title:String,
            val reply_count:Int
        )
    }

    data class JsonNewsGroupRet(
        val status: Status,
        val data: List<Data>
    ) {

        data class Data(
            val id: Int,
            val name: String,
            val symbol: String,
            val slug: String,
            // The annotation to a model property lets you pass the serialized and deserialized
            // name as a string. This is useful if you don't want your model class and the JSON
            // to have identical naming.
            @SerializedName("circulating_supply")
            val circulatingSupply: Double,
            @SerializedName("last_updated")
            val lastUpdated: String,
            val quote: Quote
        ) {

            data class Quote(
                // For additional option during deserialization you can specify value or alternative
                // values. Gson will check the JSON for all names we specify and try to find one to
                // map it to the annotated property.
                @SerializedName(value = "USD", alternate = ["AUD", "BRL", "CAD", "CHF", "CLP",
                    "CNY", "CZK", "DKK", "EUR", "GBP", "HKD", "HUF", "IDR", "ILS", "INR", "JPY",
                    "KRW", "MXN", "MYR", "NOK", "NZD", "PHP", "PKR", "PLN", "RUB", "SEK", "SGD",
                    "THB", "TRY", "TWD", "ZAR"])
                val currency: Currency
            ) {

                data class Currency(
                    val price: Double,
                    @SerializedName("volume_24h")
                    val volume24h: Double,
                    @SerializedName("last_updated")
                    val lastUpdated: String
                )
            }
        }

        data class Status(
            val timestamp: String,
            @SerializedName("error_code")
            val errorCode: Int,
            @SerializedName("error_message")
            val errorMessage: String,
            val elapsed: Int,
            @SerializedName("credit_count")
            val creditCount: Int
        )
    }
}
