package kg.iuca.mylab5.network

import retrofit2.http.GET
import com.google.gson.annotations.SerializedName
interface ApiService {
    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse
}
data class CategoriesResponse(
    @SerializedName("categories") val categories: List<Category>
)
data class Category(
    @SerializedName("idCategory") val id: String,
    @SerializedName("strCategory") val name: String,
    @SerializedName("strCategoryThumb") val thumbnail: String,
    @SerializedName("strCategoryDescription") val description: String
)