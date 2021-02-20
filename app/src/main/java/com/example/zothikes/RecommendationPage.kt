package com.example.zothikes

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId;

open class Trail(
        @PrimaryKey var _id: ObjectId? = null,
        var difficulty: String? = null,
        var latitude: Double? = null,
        var length: String? = null,
        var longitude: Double? = null,
        var name: String? = null,
        var partitionKey: String? = null,
        var rating: String? = null,
        var ttc: String? = null
): RealmObject() {}


open class Users(
    @PrimaryKey var _id: ObjectId? = null,
    var age: String? = null,
    var gender: String? = null,
    var height: String? = null,
    var name: String? = null,
    var partitionKey: String? = null,
    var target_weight: String? = null,
    var weight: String? = null
): RealmObject() {}

fun degreeToRadian(degree: Double) : Double {
    return degree * Math.PI / 180
}

fun calculateClosestTrail(lat1: Double, long1: Double, lat2: Double, long2: Double) : Double {
    val lat1R = degreeToRadian(lat1)
    val lat2R = degreeToRadian(lat2)
    val deltaLat = degreeToRadian(lat2 - lat1)
    val deltaLong = degreeToRadian(long2 - long1)

    val a = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) + Math.cos(lat1R) * Math.cos(lat2R) * Math.sin(deltaLong/2) * Math.sin(deltaLong/2)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))
    return 6371000.0 * c // 6371000 is Earth radius; returns in meter
}



fun calculateBMI(weight: String?, height: String?) : String {
    if (weight != null && height != null) {
        val bmi: Float = (weight.toFloat() / (height.toFloat() * height.toFloat())) * 703
        if (bmi < 18.5) {
            return "Underweight"
        } else if (bmi < 25) {
            return "Normal weight"
        } else if (bmi < 30) {
            return "Overweight"
        } else {
            return "Obesity"
        }
    }
    return "error"
}


class RecommendationPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendation_page)

        // Temporary way to pass User information
        var bundle: Bundle? = intent.extras
        val name: String? = bundle?.getString("name")
        val age: String? = bundle?.getString("age")
        val height: String? = bundle?.getString("height")
        val weight: String? = bundle?.getString("weight")
        val gender: String? = bundle?.getString("gender")
        val goal: String? = bundle?.getString("goal")
//
//        Log.v("TESTINGNAME", "" + name)
//        Log.v("TESTINGNAGE", "" + age)
//        Log.v("TESTINGHEIGHT", "" + height)


//        Log.v("CREATEDpage", "Testing creating recommendation")
//        Log.v("BMI", "" + calculateBMI(weight, height))
//        Log.v("DISTANCE", ""+calculateClosestTrail(51.5, 0.0, 38.8, -77.1))
        Realm.init(this)
        val appID : String = "cs125-jtcjk"
        var app = App(AppConfiguration.Builder(appID).build())

        val credentials: Credentials = Credentials.anonymous()
        app.loginAsync(credentials) {
            if (it.isSuccess) {
                Log.v("QUICKSTART", "Successfully authenticated anonymously.")
                val user: User? = app.currentUser()
//                 interact with realm using your user object here
                val partitionValue: String = "TRAIL"
                val config = SyncConfiguration.Builder(user, partitionValue)
                        .allowQueriesOnUiThread(true)
                        .allowWritesOnUiThread(true)
                    .build()
                Realm.getInstanceAsync(config, object : Realm.Callback() {
                    override fun onSuccess(realm: Realm) {
                        Log.v("EXAMPLE", "Successfully opened a realm.")
                        val tasks = realm.where(Trail::class.java).findAllAsync()
//                        Log.v("TOTALTRAILS", "" + tasks.count())
                        for (trail in tasks) {
                            if (trail != null) {
                                Log.v("TASKS", "" + trail.name)
                            }
                        }
//                        Log.v("TOTALTRAILS", "" + tasks.count())
//                        realm.close()
                    }
                })

            } else {
                Log.e("QUICKSTART", "Failed to log in. Error: ${it.error}")
            }
        }
        Log.v("finishedpage", "Finished creating recommendation")
    }
}