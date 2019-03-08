package jrizani.jrmapview;

import android.animation.LayoutTransition;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
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

public class JRMapView extends ConstraintLayout {

    private MapView mapView;
    private ImageView outerSpace;
    private ConstraintLayout layerButton;
    private ConstraintSet constraintSet;
    private ConstraintLayout mapChooser;

    private OnMapReadyCallback mOnMapReadyCallback;
    private GoogleMap mGoogleMap;

    private ImageView defaultMapButton;
    private ImageView satelliteMapButton;
    private ImageView terrainMapButton;
    private TextView defaultText;
    private TextView satelliteText;
    private TextView terrainText;
    private int topPadding;

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

    public void onCreate(Bundle savedInstanceState, OnMapReadyCallback onMapReadyCallback) {
        mapView.onCreate(savedInstanceState);
        mOnMapReadyCallback = onMapReadyCallback;
    }

    public void onResume() {
        mapView.onResume();

        try {
            MapsInitializer.initialize(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(mOnMapReadyCallback);
    }

    public void onPause() {
        mapView.onPause();
    }

    public void onStart() {
        mapView.onStart();
    }

    public void onStop() {
        mapView.onStop();
    }

    public void onDestroy() {
        mapView.onDestroy();
    }

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

    private void initLayerButton() {
        layerButton = new ConstraintLayout(getContext());
        layerButton.setId(R.id.layer_button);
        layerButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gmwtv_map_type));

        ViewCompat.setElevation(layerButton, 4);
//        layerButton.setElevation(4);

        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );

        ImageView image = new ImageView(getContext());
        image.setImageResource(R.drawable.gmwtv_layers);
        image.setId(R.id.map_image);
        layerButton.addView(image, (int) dipToPixel(24), (int) dipToPixel(24));

        ConstraintSet imageConstraintSet = new ConstraintSet();
        imageConstraintSet.clone(layerButton);
        imageConstraintSet.connect(R.id.map_image, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        imageConstraintSet.connect(R.id.map_image, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        imageConstraintSet.connect(R.id.map_image, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        imageConstraintSet.connect(R.id.map_image, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        imageConstraintSet.applyTo(layerButton);

        addView(layerButton, 2, layoutParams);

        constraintSet.clone(this);
        constraintSet.connect(R.id.layer_button, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, (int) dipToPixel(16));
        constraintSet.connect(R.id.layer_button, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, (int) dipToPixel(16));
        constraintSet.applyTo(this);
    }

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
        constraintSet.connect(R.id.map_type_chooser, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, (int) dipToPixel(16));
        constraintSet.connect(R.id.map_type_chooser, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, (int) dipToPixel(16));
        constraintSet.applyTo(this);
    }

    private void initMapTypeChildren() {
        defaultMapButton = mapChooser.findViewById(R.id.default_map);
        satelliteMapButton = mapChooser.findViewById(R.id.satellite_map);
        terrainMapButton = mapChooser.findViewById(R.id.terrain_map);
        defaultText = mapChooser.findViewById(R.id.default_text);
        satelliteText = mapChooser.findViewById(R.id.satellite_text);
        terrainText = mapChooser.findViewById(R.id.terrain_text);
    }

    private float dipToPixel(float dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    public void setGoogleMapPadding(int left, int top, int right, int bottom) {
        topPadding = (int) (topPadding + dipToPixel(top));
        mGoogleMap.setPadding((int) dipToPixel(4.5f + left), topPadding, (int) dipToPixel(4.5f + right), (int) dipToPixel(bottom));
        constraintSet.clone(this);
        constraintSet.connect(R.id.layer_button, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, (int) dipToPixel(top + 16));
        constraintSet.connect(R.id.layer_button, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, (int) dipToPixel(right + 16));
        constraintSet.connect(R.id.map_type_chooser, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, (int) dipToPixel(top + 16));
        constraintSet.connect(R.id.map_type_chooser, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, (int) dipToPixel(right + 16));
        constraintSet.applyTo(this);
    }

    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        setMapType(googleMap.getMapType());
        topPadding = (int) (layerButton.getHeight() + dipToPixel(16));
        googleMap.setPadding((int) dipToPixel(4.5f), topPadding, (int) dipToPixel(4.5f), 0);
    }
}
