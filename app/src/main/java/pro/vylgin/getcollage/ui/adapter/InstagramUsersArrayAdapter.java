package pro.vylgin.getcollage.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.CachedSpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.simple.BitmapRequest;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import pro.vylgin.getcollage.R;
import pro.vylgin.getcollage.model.InstagramUser;
import pro.vylgin.getcollage.ui.activity.BaseActivity;
import pro.vylgin.getcollage.ui.fragment.InstagramPhotosFragment;
import roboguice.util.temp.Ln;

public class InstagramUsersArrayAdapter extends ArrayAdapter<InstagramUser> implements
        AbsListView.OnScrollListener {

    private Context context;
    private List<InstagramUser> instagramUsers;

    private int lastScrollState = SCROLL_STATE_IDLE;

    public InstagramUsersArrayAdapter(Context context, int resource, List<InstagramUser> instagramUsers) {
        super(context, resource, instagramUsers);
        this.context = context;
        this.instagramUsers = instagramUsers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        InstagramUser instagramUser = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.instagram_users_list_item, null);
            holder = new ViewHolder();
            holder.instagramAvatarImageView = (ImageView) convertView.findViewById(R.id.instagramAvatarImageView);
            holder.instagramLoginTextView = (TextView) convertView.findViewById(R.id.instagramLoginTextView);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();

        holder.instagramAvatarImageView.setImageResource(R.drawable.ic_launcher);
        holder.instagramLoginTextView.setText(instagramUser.getUsername());

        if (holder.pendingRequest != null) {
            holder.pendingRequest.cancel();
        }

        boolean isFlinging = lastScrollState == SCROLL_STATE_FLING;
        if (isFlinging) {
            executeImageRequest(position, holder, true);
        } else {
            executeImageRequest(position, holder, false);
        }

        return convertView;
    }

    private void executeImageRequest(int position, ViewHolder holder, boolean cacheOnly) {
        InstagramUser photoSource = instagramUsers.get(position);
        int width = 150;
        int height = 150;
        holder.pendingRequest = create(photoSource.getProfile_picture(), width, height);
        BitmapRequestListener requestListener = new BitmapRequestListener(holder);

        if (cacheOnly) {
            holder.imageState = ImageState.LOADING_CACHE_ONLY;
            ((BaseActivity) context).getSpiceManager().getFromCache(holder.pendingRequest.getResultType(),
                    holder.pendingRequest.getRequestCacheKey(),
                    holder.pendingRequest.getCacheDuration(), requestListener);
        } else {
            holder.imageState = ImageState.LOADING_WITH_NETWORK;
            ((BaseActivity) context).getSpiceManager().execute(holder.pendingRequest, requestListener);
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if (scrollState != SCROLL_STATE_FLING && lastScrollState == SCROLL_STATE_FLING) {
            int firstVisiblePosition = absListView.getFirstVisiblePosition();
            int lastVisiblePosition = absListView.getLastVisiblePosition();
            int numVisiblePositions = lastVisiblePosition - firstVisiblePosition + 1;

            for (int i = 0; i < numVisiblePositions; i++) {

                int adapterItemPosition = firstVisiblePosition + i;
                View visibleItem = absListView.getChildAt(i);
                ViewHolder viewMetaData = (ViewHolder) visibleItem.getTag();

                boolean imageNotLoaded = viewMetaData.imageState == ImageState.EMPTY;
                boolean imageNotLoadingFromNetwork = viewMetaData.imageState == ImageState.LOADING_CACHE_ONLY;
                if (imageNotLoaded || imageNotLoadingFromNetwork) {
                    executeImageRequest(adapterItemPosition, viewMetaData, false);
                }
            }
        }
        lastScrollState = scrollState;
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {

    }

    private enum ImageState {
        EMPTY, LOADING_WITH_NETWORK, LOADING_CACHE_ONLY, LOADING_COMPLETE
    }

    private class ViewHolder {
        ImageView instagramAvatarImageView;
        CachedSpiceRequest<Bitmap> pendingRequest;
        TextView instagramLoginTextView;
        public ImageState imageState;
    }

    public CachedSpiceRequest<Bitmap> create(String photoUrl, int width, int height) {
        File cacheFile = null;
        String filename = null;
        try {
            filename = URLEncoder.encode(photoUrl, "UTF-8");
            cacheFile = new File(context.getCacheDir(), filename);
        } catch (UnsupportedEncodingException e) {
            Ln.e(e);
        }

        BitmapRequest request = new BitmapRequest( photoUrl, width, height, cacheFile);
        return new CachedSpiceRequest<Bitmap>(request, filename, DurationInMillis.ONE_MINUTE * 10);
    }

    private class BitmapRequestListener implements RequestListener<Bitmap> {

        private final ViewHolder holder;

        public BitmapRequestListener(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.d("Error", spiceException.toString());
        }

        @Override
        public void onRequestSuccess(Bitmap bitmap) {
            if (bitmap == null) {
                holder.imageState = ImageState.EMPTY;
            } else {
                holder.imageState = ImageState.LOADING_COMPLETE;
                holder.instagramAvatarImageView.setImageBitmap(bitmap);
            }

            holder.pendingRequest = null;
        }
    }
}
