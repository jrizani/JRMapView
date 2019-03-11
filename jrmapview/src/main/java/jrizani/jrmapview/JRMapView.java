package jrizani.jrmapview;

import android.animation.LayoutTransition;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

import jrizani.JRMapView.R;

/*=============================*/
/*            AUTHOR           */
/*          JULIAN NR          */
/* juliannoorrizani@gmail.com  */
/*         22 Feb 2019         */
/*=============================*/

/**
 * class to create custom View that show mapView and show map type chooser in it
 */
public class JRMapView extends ConstraintLayout {

    /**
     * the real map view in this custom layout
     */
    private MapView mapView;
    /**
     * outer space is view that use to hide map type chooser when it clicked
     */
    private ImageView outerSpace;
    /**
     * button to show map type chooser
     */
    private ImageButton layerButton;
    /**
     * to apply constraint set in this custom layout(use constraintLayout)
     */
    private ConstraintSet constraintSet;
    /**
     * map type chooser view
     */
    private ConstraintLayout mapChooser;
    /**
     * callback when map ready
     */
    private OnMapReadyCallback mOnMapReadyCallback;
    /**
     * google map
     */
    private GoogleMap mGoogleMap;
    /**
     * button to set map to default type
     */
    private ImageView defaultMapButton;
    /**
     * button to set map to satellite type
     */
    private ImageView satelliteMapButton;
    /**
     * button to set map to terrain button
     */
    private ImageView terrainMapButton;
    /**
     * text under default button
     */
    private TextView defaultText;
    /**
     * text under satellite button
     */
    private TextView satelliteText;
    /**
     * text under terrain button
     */
    private TextView terrainText;
    /**
     * default top padding of layer button
     */
    private int topPadding = 12;

    public JRMapView(Context context) {
        super(context);
        init();
    }

    public JRMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public JRMapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public JRMapView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * method to trigger onCreate of mapView and store the onMapReadyCallback
     *
     * @param savedInstanceState is savedInstanceState of activity or fragment
     * @param onMapReadyCallback is callback implementation
     */
    public void onCreate(Bundle savedInstanceState, OnMapReadyCallback onMapReadyCallback) {
        mapView.onCreate(savedInstanceState);
        mOnMapReadyCallback = onMapReadyCallback;
    }

    /**
     * method to trigger mapInitializer and getMapAsync of mapView
     */
    public void onResume() {
        mapView.onResume();

        try {
            MapsInitializer.initialize(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(mOnMapReadyCallback);
    }

    /**
     * method to trigger onPause of mapView
     */
    public void onPause() {
        mapView.onPause();
    }

    /**
     * method to trigger onStart of mapView
     */
    public void onStart() {
        mapView.onStart();
    }

    /**
     * method to trigger onStop of mapView
     */
    public void onStop() {
        mapView.onStop();
    }

    /**
     * method to trigger onDestroy of mapView
     */
    public void onDestroy() {
        mapView.onDestroy();
    }

    /**
     * method to initialize the view of this custom view
     */
    private void init() {
        setLayoutTransition(new LayoutTransition());
        constraintSet = new ConstraintSet();
        mapView = new MapView(getContext());
        mapView.setId(R.id.map);

        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.MATCH_CONSTRAINT,
                LayoutParams.MATCH_CONSTRAINT
        );

        addView(mapView, 0, layoutParams);

        constraintSet.clone(this);
        constraintSet.connect(R.id.map, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(R.id.map, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.connect(R.id.map, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.connect(R.id.map, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.applyTo(this);

        initOuterSpace();

        initLayerButton();

        initMapType();

        addClickAction();
    }

    /**
     * method to add action to all button
     */
    private void addClickAction() {
        layerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mapChooser.setVisibility(VISIBLE);
                outerSpace.setVisibility(VISIBLE);
            }
        });

        outerSpace.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mapChooser.setVisibility(GONE);
                outerSpace.setVisibility(GONE);
            }
        });

        defaultMapButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });

        satelliteMapButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        terrainMapButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            }
        });
    }

    /**
     * method to set map type and change selected view on map type chooser
     *
     * @param mapType the type code of google map
     */
    private void setMapType(int mapType) {
        mGoogleMap.setMapType(mapType);
        switch (mapType) {
            case GoogleMap.MAP_TYPE_NORMAL:
                defaultMapButton.setImageResource(R.drawable.gmwtv_map_default_selected);
                terrainMapButton.setImageResource(R.drawable.gmwtv_map_terrain);
                satelliteMapButton.setImageResource(R.drawable.gmwtv_map_satellite);
                defaultText.setTextColor(ContextCompat.getColor(getContext(), R.color.gmwtv_selected));
                satelliteText.setTextColor(ContextCompat.getColor(getContext(), R.color.gmwtv_not_selected));
                satelliteText.setTextColor(ContextCompat.getColor(getContext(), R.color.gmwtv_not_selected));
                break;
            case GoogleMap.MAP_TYPE_SATELLITE:
                defaultMapButton.setImageResource(R.drawable.gmwtv_map_default);
                terrainMapButton.setImageResource(R.drawable.gmwtv_map_terrain);
                satelliteMapButton.setImageResource(R.drawable.gmwtv_map_satellite_selected);
                defaultText.setTextColor(ContextCompat.getColor(getContext(), R.color.gmwtv_not_selected));
                satelliteText.setTextColor(ContextCompat.getColor(getContext(), R.color.gmwtv_selected));
                terrainText.setTextColor(ContextCompat.getColor(getContext(), R.color.gmwtv_not_selected));
                break;
            case GoogleMap.MAP_TYPE_TERRAIN:
                defaultMapButton.setImageResource(R.drawable.gmwtv_map_default);
                terrainMapButton.setImageResource(R.drawable.gmwtv_map_terrain_selected);
                satelliteMapButton.setImageResource(R.drawable.gmwtv_map_satellite);
                defaultText.setTextColor(ContextCompat.getColor(getContext(), R.color.gmwtv_not_selected));
                satelliteText.setTextColor(ContextCompat.getColor(getContext(), R.color.gmwtv_not_selected));
                terrainText.setTextColor(ContextCompat.getColor(getContext(), R.color.gmwtv_selected));
                break;
        }
    }

    /**
     * method to initialize outer space
     */
    private void initOuterSpace() {
        outerSpace = new ImageView(getContext());
        outerSpace.setId(R.id.outer_space);

        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        );

        addView(outerSpace, 1, layoutParams);

        outerSpace.setVisibility(GONE);
    }

    /**
     * method to initialize layer button
     */
    private void initLayerButton() {
        layerButton = new ImageButton(getContext());
        layerButton.setId(R.id.layer_button);
//        layerButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gmwtv_map_type));
        layerButton.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        layerButton.setImageResource(R.drawable.gmwtv_layer_button);

        ViewCompat.setElevation(layerButton, 4);


        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );

        ImageView image = new ImageView(getContext());
        image.setImageResource(R.drawable.gmwtv_layers);
        image.setId(R.id.map_image);

        addView(layerButton, 2, layoutParams);

        constraintSet.clone(this);
        constraintSet.connect(R.id.layer_button, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, dipToPixel(12));
        constraintSet.connect(R.id.layer_button, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, dipToPixel(12));
        constraintSet.applyTo(this);

        ViewCompat.setElevation(layerButton, 4);
    }

    /**
     * method to initialize map type chooser view
     */
    private void initMapType() {
        mapChooser = (ConstraintLayout) LayoutInflater.from(getContext()).inflate(R.layout.gmwtv_layout_layer_chooser, this, false);

        ViewCompat.setElevation(mapChooser, 5);

        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );

        initMapTypeChildren();

        addView(mapChooser, 3, layoutParams);

        mapChooser.setVisibility(GONE);

        constraintSet.clone(this);
        constraintSet.connect(R.id.map_type_chooser, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, dipToPixel(12));
        constraintSet.connect(R.id.map_type_chooser, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, dipToPixel(12));
        constraintSet.applyTo(this);
    }

    /**
     * method to initialize children of map type chooser view
     */
    private void initMapTypeChildren() {
        defaultMapButton = mapChooser.findViewById(R.id.default_map);
        satelliteMapButton = mapChooser.findViewById(R.id.satellite_map);
        terrainMapButton = mapChooser.findViewById(R.id.terrain_map);
        defaultText = mapChooser.findViewById(R.id.default_text);
        satelliteText = mapChooser.findViewById(R.id.satellite_text);
        terrainText = mapChooser.findViewById(R.id.terrain_text);
    }

    /**
     * method to convert dip to pixel
     *
     * @param dip value in dip
     * @return value in pixel
     */
    private int dipToPixel(float dip) {
        return (int) (dip * getContext().getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * method to complete padding when googleMap padding changed
     *
     * @param left   left padding
     * @param top    top padding
     * @param right  right padding
     * @param bottom bottom padding
     */
    public void setGoogleMapPadding(int left, int top, int right, int bottom) {
        topPadding = dipToPixel(topPadding) + top;
        mGoogleMap.setPadding(left, top, right, bottom);
        constraintSet.clone(this);
        constraintSet.connect(R.id.layer_button, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, topPadding);
        constraintSet.connect(R.id.layer_button, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, right + dipToPixel(12));
        constraintSet.connect(R.id.map_type_chooser, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, topPadding);
        constraintSet.connect(R.id.map_type_chooser, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, right + dipToPixel(12));
        constraintSet.applyTo(this);
    }

    /**
     * method that must run when mapType or myLocationEnabled changed out of this class
     *
     * @param googleMap googleMap field from user
     */
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        setMapType(googleMap.getMapType());

        if (googleMap.isMyLocationEnabled()) {
            topPadding = 56;
            constraintSet.clone(this);
            constraintSet.connect(R.id.layer_button, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, dipToPixel(56));
            constraintSet.connect(R.id.layer_button, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, dipToPixel(12));
            constraintSet.connect(R.id.map_type_chooser, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, dipToPixel(56));
            constraintSet.connect(R.id.map_type_chooser, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, dipToPixel(12));
            constraintSet.applyTo(this);
        }
    }
}
