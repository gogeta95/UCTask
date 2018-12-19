package `in`.farmguide.myapplication.repository.network.model.city

import com.google.gson.annotations.SerializedName

data class LocationSuggestion(

    @SerializedName("country_flag_url")
    val countryFlagUrl: String?,
    @SerializedName("country_id")
    val countryId: Long?,
    @SerializedName("country_name")
    val countryName: String?,
    @SerializedName("discovery_enabled")
    val discoveryEnabled: Long?,
    @SerializedName("has_new_ad_format")
    val hasNewAdFormat: Long?,
    @SerializedName("id")
    val id: Long,
    @SerializedName("is_state")
    val isState: Long?,
    @SerializedName("name")
    val name: String,
    @SerializedName("should_experiment_with")
    val shouldExperimentWith: Long?,
    @SerializedName("state_code")
    val stateCode: String?,
    @SerializedName("state_id")
    val stateId: Long?,
    @SerializedName("state_name")
    val stateName: String?
)
