# JRMapView
[![](https://jitpack.io/v/jrizani/JRMapView.svg)](https://jitpack.io/#jrizani/JRMapView)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-JRMapView-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7574)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

| :warning: **This project is no longer maintained** |
|---|

Google map view with map type chooser like Google Map App

<a href="https://jrizani.github.io/donate-jrmapview.html"><img src="https://github.com/jrizani/jrizani.github.io/raw/master/images/donate_button.png" width="100px"/></a>

#### [View Releases and Changelogs](https://github.com/jrizani/JRMapView/releases)
<img src="https://github.com/jrizani/JRMapView/raw/master/ss/choose.png" width="200px"/> <img src="https://github.com/jrizani/JRMapView/raw/master/ss/normal.png" width="200px"/> <img src="https://github.com/jrizani/JRMapView/raw/master/ss/satellite.png" width="200px"/> <img src="https://github.com/jrizani/JRMapView/raw/master/ss/terrain.png" width="200px"/> 


# Table of Content
1. [Gradle install](#gradle-install)
2. [How to use](#how-to-use)
3. [Example](#example)

---

## Gradle install
Add this to your project-level build.gradle

```gradle
allprojects {
    repositories {
        ..
        maven {
            url  "https://jitpack.io"
        }
    }
}
```

Implement the dependency to your app-level build.gradle

```gradle
dependencies {
  ..
  implementation 'com.github.jrizani:JRMapView:$version'
}
```

## How to use
Make sure you have google map api key, if you don't understand about that, you can find the tutorial `how to implement google map in android` on google search.

Declare the view in your layout
```xml
<jrizani.jrmapview.JRMapView
        android:id="@+id/googleView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

See how to implement the map view on your google map activity, make sure you add the `ACCESS_COARSE_LOCATION` and `ACCESS_FINE_LOCATION` permission.
```java
private GoogleMap mMap;
private JRMapView mMapView;
private Bundle savedInstanceState;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);
    this.savedInstanceState = savedInstanceState;
    mMapView = findViewById(R.id.googleView);

   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // request permission when device version is higher than Marshmallow
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        } else {
            mMapView.onCreate(savedInstanceState, this);
            mMapView.onResume();
        }
}

@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
         mMapView.onCreate(savedInstanceState, this);
         mMapView.onResume();
    } else {
        finish();
    }
}

@Override
public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    googleMap.setMyLocationEnabled(true);
    mMapView.onMapReady(googleMap); //you must call it when you change googleMap.setMyLocationEnabled(boolean) and googleMap.setMapType(int)

    mMapView.setGoogleMapPadding(0, 24, 24, 0); //optional

    // Add a marker in Sydney and move the camera
    LatLng sydney = new LatLng(-34, 151);
    mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
}
```

## Example
You can found the example code [`here`](https://github.com/jrizani/JRMapView/tree/master/app).

There is the sample gif

<img src="https://github.com/jrizani/JRMapView/raw/master/ss/video.gif" width="200px"/>
