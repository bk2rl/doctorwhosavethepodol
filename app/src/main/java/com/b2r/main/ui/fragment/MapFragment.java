package com.b2r.main.ui.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.b2r.main.Constants;
import com.b2r.main.ui.activity.MainActivity;
import com.b2r.main.R;
import com.b2r.main.model.Task;

import org.osmdroid.ResourceProxy;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.ResourceProxyImpl;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    protected MapView mMapView;
    protected ResourceProxy mResourceProxy;
    private MainActivity mActivity;
    private ItemizedOverlayWithFocus<OverlayItem> mPointsOverlay;
    private Task centeredTask;

    private static final BoundingBoxE6 sPodolBoundingBox;
    private static final Paint sPaint;

    static {
        sPodolBoundingBox = new BoundingBoxE6(50.4721542,
                30.5509955, 50.4579884, 30.4969499);
        sPaint = new Paint();
        sPaint.setColor(Color.argb(50, 255, 0, 0));
    }

    private static final int zoomMinLevel = 15;
    private static final int zoomMaxLevel = 18;
    private View mFragmentView;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        if (getArguments() != null) {
            final int taskPosition = getArguments().getInt(Constants.TASK_POSITION);
            final int questPosition = getArguments().getInt(Constants.QUEST_POSITION);
            centeredTask = mActivity.getQuests().get(questPosition).getTaskList().get(taskPosition);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentView = inflater.inflate(R.layout.map_fragment, container, false);
        mResourceProxy = new ResourceProxyImpl(inflater.getContext().getApplicationContext());
        mMapView = (MapView) mFragmentView.findViewById(R.id.mapview);


        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(mActivity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            setTileSource();
        }

        mMapView.setUseDataConnection(true);
        mMapView.setBuiltInZoomControls(true);
        mMapView.setMinZoomLevel(zoomMinLevel);
        mMapView.setMaxZoomLevel(zoomMaxLevel);
        mMapView.getController().setZoom(zoomMinLevel);
        mMapView.setMultiTouchControls(true);
        return mFragmentView;
    }

    private void setTileSource() {
        mMapView.setTileSource(TileSourceFactory.MAPNIK);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addOverlays();
    }


    protected void addOverlays() {
        mPointsOverlay = new ItemizedOverlayWithFocus<>(Task.getMapMarkers(),
                getResources().getDrawable(R.drawable.map_marker),
                getResources().getDrawable(R.drawable.map_marker),
                getResources().getColor(R.color.blue_primary_light),
                this, mResourceProxy);

        mPointsOverlay.setFocusItemsOnTap(true);
        if (centeredTask.isHasBindToMap()) {
            mPointsOverlay.setFocusedItem(centeredTask.getMapItem());
        }


        mMapView.setScrollableAreaLimit(sPodolBoundingBox);
        if (centeredTask.isHasBindToMap()) {
            mMapView.getController().setCenter(centeredTask.getMapItem().getPoint());
        } else {
            mMapView.getController().setCenter(sPodolBoundingBox.getCenter());
        }


        mMapView.getOverlays().add(mPointsOverlay);

    }

    @Override
    public boolean onItemSingleTapUp(int index, OverlayItem item) {
        return false;
    }

    @Override
    public boolean onItemLongPress(int index, OverlayItem item) {
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setTileSource();
                }
                return;
            }

        }
    }
}
