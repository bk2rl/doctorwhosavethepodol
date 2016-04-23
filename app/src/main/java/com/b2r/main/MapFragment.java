package com.b2r.main;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.tileprovider.IRegisterReceiver;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.tileprovider.util.SimpleRegisterReceiver;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.ResourceProxyImpl;
import org.osmdroid.util.TileSystem;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {

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

        final Context context = getActivity();
        final Context applicationContext = context.getApplicationContext();
        final IRegisterReceiver registerReceiver = new SimpleRegisterReceiver(applicationContext);

        // Create a custom tile source
        final ITileSource tileSource = new XYTileSource(
                "MapQuest", ResourceProxy.string.mapquest_osm,
                zoomMinLevel, zoomMaxLevel,
                256,
                ".jpg",
                new String[]{"http://otile1.mqcdn.com/tiles/1.0.0/osm/",
                        "http://otile2.mqcdn.com/tiles/1.0.0/osm/",
                        "http://otile3.mqcdn.com/tiles/1.0.0/osm/",
                        "http://otile4.mqcdn.com/tiles/1.0.0/osm/"}
        );
//

        mResourceProxy = new ResourceProxyImpl(inflater.getContext().getApplicationContext());
//        mMapView = new MapView(context, TileSystem.getTileSize(), new DefaultResourceProxyImpl(context), tileProviderArray);
        mFragmentView = inflater.inflate(R.layout.map_fragment, container, false);
        mMapView = (MapView) mFragmentView.findViewById(R.id.mapview);
        mMapView.setTileSource(tileSource);
        mMapView.setBuiltInZoomControls(true);
        mMapView.setMinZoomLevel(zoomMinLevel);
        mMapView.setMaxZoomLevel(zoomMaxLevel);
        mMapView.getController().setZoom(zoomMinLevel);
        mMapView.setMultiTouchControls(true);
        mMapView.setUseDataConnection(true);
        return mFragmentView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addOverlays();
    }


    protected void addOverlays() {


        mPointsOverlay = new ItemizedOverlayWithFocus<>(Task.getMapMarkers(),
                getResources().getDrawable(R.drawable.dw_map_marker_1blue_q48),
                getResources().getDrawable(R.drawable.dw_map_marker_1blue_q48),
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
}
