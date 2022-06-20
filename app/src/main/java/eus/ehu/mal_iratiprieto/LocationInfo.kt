package eus.ehu.mal_iratiprieto

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class LocationInfo(
    var latitude: Double? = 0.0,
    var longitude: Double? = 0.0
)