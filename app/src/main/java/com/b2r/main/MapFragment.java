package com.b2r.main;


import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.ResourceProxy;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.ResourceProxyImpl;
import org.osmdroid.util.TileSystem;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Overlay;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {

    protected MapView mMapView;
    protected ResourceProxy mResourceProxy;
    private ItemizedOverlayWithFocus<OverlayItem> mMyLocationOverlay;

    private static final BoundingBoxE6 sPodolBoundingBox;
    private static final Paint sPaint;

    static {
        sPodolBoundingBox = new BoundingBoxE6(50.4721542,
                30.5268927, 50.4580212, 30.4969499);
        sPaint = new Paint();
        sPaint.setColor(Color.argb(50, 255, 0, 0));
    }

    private Overlay mShadeAreaOverlay;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mResourceProxy = new ResourceProxyImpl(inflater.getContext().getApplicationContext());
        mMapView = new MapView(inflater.getContext(), TileSystem.getTileSize(), mResourceProxy);
        return mMapView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addOverlays();

        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);
    }


    protected void addOverlays() {
        /* Create a static ItemizedOverlay showing some Markers on various cities. */
        final ArrayList<OverlayItem> items = new ArrayList<>();
        items.add(new OverlayItem("Task 1", "Description 1", new GeoPoint(50466618, 30515435)));
        items.add(new OverlayItem("Task 2", "Description 2", new GeoPoint(50473277, 30517441)));
        items.add(new OverlayItem("Task 3", "Description 3", new GeoPoint(50468831, 30506122)));
        items.add(new OverlayItem("Task 4", "Description 4", new GeoPoint(50466186, 30523406)));

        
        mMyLocationOverlay = new ItemizedOverlayWithFocus<>(items, this, mResourceProxy);

        mMyLocationOverlay.setFocusItemsOnTap(true);
        mMyLocationOverlay.setFocusedItem(0);


        mMapView.setScrollableAreaLimit(sPodolBoundingBox);
        mMapView.setMinZoomLevel(15);
        mMapView.setMaxZoomLevel(18);
        mMapView.getController().animateTo(sPodolBoundingBox.getCenter());

        mMapView.getOverlays().add(mMyLocationOverlay);

    }

    @Override
    public boolean onItemSingleTapUp(int index, OverlayItem item) {
        return false;
    }

    @Override
    public boolean onItemLongPress(int index, OverlayItem item) {
        return false;
    }
}
