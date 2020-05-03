package com.example.skillevaluationtest.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skillevaluationtest.R
import com.example.skillevaluationtest.model.common.PostDto
import com.example.skillevaluationtest.model.db.UserEntity

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    var userData: UserEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.title_user_location)

        userData = intent.getSerializableExtra("user_Data") as UserEntity?

        setData()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {

        userFullNameTv.text = this.getString(R.string.user_detail_name) + userData!!.name
        userNameTv.text = this.getString(R.string.user_detail_user_name) + userData!!.username
        addressTv.text =
            this.getString(R.string.user_detail_user_address) + userData!!.city + this.getString(R.string.user_detail_dash) + userData!!.zipCode
        emailTv.text = this.getString(R.string.user_detail_user_email) + userData!!.email
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val userPos = LatLng(userData!!.lat!!.toDouble(), userData!!.lng!!.toDouble())
        mMap.addMarker(
            MarkerOptions().position(userPos).title(userData!!.username)
                .snippet(userData!!.username)
        )
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(userData!!.lat!!.toDouble(), userData!!.lng!!.toDouble())
                , 15f
            )
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userPos))
    }
}
