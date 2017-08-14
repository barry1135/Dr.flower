package nfu.csie.newdrflower.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;
import java.util.List;

import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.controller.BaseGoogleMapActivity;

/**
 * Created by barry on 2017/8/2.
 */

public class FlowerMap extends BaseGoogleMapActivity implements ClusterManager.OnClusterClickListener<Flower>, ClusterManager.OnClusterInfoWindowClickListener<Flower>, ClusterManager.OnClusterItemClickListener<Flower>, ClusterManager.OnClusterItemInfoWindowClickListener<Flower> {
    private ClusterManager<Flower> mClusterManager;
    private CoordinateDataBases DH=new CoordinateDataBases(this);

    /**
     * Draws profile photos inside markers (using IconGenerator).
     * When there are multiple people in the cluster, draw multiple photos (using MultiDrawable).
     */
    private class PersonRenderer extends DefaultClusterRenderer<Flower> {
        private final IconGenerator mIconGenerator = new IconGenerator(getApplicationContext());
        private final IconGenerator mClusterIconGenerator = new IconGenerator(getApplicationContext());
        private final ImageView mImageView;
        private final ImageView mClusterImageView;
        private final int mDimension;

        public PersonRenderer() {
            super(getApplicationContext(), getMap(), mClusterManager);

            View multiProfile = getLayoutInflater().inflate(R.layout.multi_profile, null);
            mClusterIconGenerator.setContentView(multiProfile);
            mClusterImageView = (ImageView) multiProfile.findViewById(R.id.image);

            mImageView = new ImageView(getApplicationContext());
            mDimension = (int) getResources().getDimension(R.dimen.custom_profile_image);
            mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));
            int padding = (int) getResources().getDimension(R.dimen.custom_profile_padding);
            mImageView.setPadding(padding, padding, padding, padding);
            mIconGenerator.setContentView(mImageView);
        }

        @Override
        protected void onBeforeClusterItemRendered(Flower Flower, MarkerOptions markerOptions) {
            // Draw a single Flower.
            // Set the info window to show their name.
            mImageView.setImageBitmap(Flower.profilePhoto);
            Bitmap icon = mIconGenerator.makeIcon();
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }

        @Override
        protected void onBeforeClusterRendered(Cluster<Flower> cluster, MarkerOptions markerOptions) {
            // Draw multiple people.
            // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).
            List<Drawable> profilePhotos = new ArrayList<Drawable>(Math.min(4, cluster.getSize()));
            int width = mDimension;
            int height = mDimension;

            for (Flower p : cluster.getItems()) {
                // Draw 4 at most.
                if (profilePhotos.size() == 4) break;
                Drawable drawable = new BitmapDrawable(p.profilePhoto);
                drawable.setBounds(0, 0, width, height);
                profilePhotos.add(drawable);
            }
            MultiDrawable multiDrawable = new MultiDrawable(profilePhotos);
            multiDrawable.setBounds(0, 0, width, height);

            mClusterImageView.setImageDrawable(multiDrawable);
            Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster cluster) {
            // Always render clusters.
            return cluster.getSize() > 1;
        }
    }

    @Override
    public boolean onClusterClick(Cluster<Flower> cluster) {

        return true;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<Flower> cluster) {
        // Does nothing, but you could go to a list of the users.
    }

    @Override
    public boolean onClusterItemClick(Flower item) {
        // Does nothing, but you could go into the user's profile page, for example.
        return false;
    }

    @Override
    public void onClusterItemInfoWindowClick(Flower item) {
        // Does nothing, but you could go into the user's profile page, for example.
    }

    @Override
    protected void startDemo() {
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(25.033194,121.564837), 7.5f));

        mClusterManager = new ClusterManager<Flower>(this, getMap());
        mClusterManager.setRenderer(new PersonRenderer());
        getMap().setOnCameraIdleListener(mClusterManager);
        getMap().setOnMarkerClickListener(mClusterManager);
        getMap().setOnInfoWindowClickListener(mClusterManager);
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);

        addItems();
        mClusterManager.cluster();
    }

    private Bitmap base64tobitmap(String base64){
        byte bytes[] = Base64.decode(base64, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    private LatLng setLatLng(double Lat, double Lon){
        return new LatLng(Lat , Lon);
    }

    private void addItems() {
        // http://www.flickr.com/photos/sdasmarchives/5036248203/

        SQLiteDatabase db = DH.getReadableDatabase();
        Cursor PicCur = db.rawQuery("SELECT _Picture FROM FlowerCoordinate",null);
        Cursor Latcur = db.rawQuery("SELECT _Latitude FROM FlowerCoordinate",null);
        Cursor Loncur = db.rawQuery("SELECT _Longitude FROM FlowerCoordinate",null);
        PicCur.moveToFirst();
        Latcur.moveToFirst();
        Loncur.moveToFirst();

        for(int i=0;i<PicCur.getCount();i++) {
            mClusterManager.addItem(new Flower(setLatLng(Double.parseDouble(Latcur.getString(0)),Double.parseDouble(Loncur.getString(0))),base64tobitmap(PicCur.getString(0))));
            PicCur.moveToNext();
            Latcur.moveToNext();
            Loncur.moveToNext();
        }

    }


}
