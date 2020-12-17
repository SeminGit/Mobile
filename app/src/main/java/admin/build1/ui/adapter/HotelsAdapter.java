package admin.build1.ui.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import admin.build1.R;

/**
 * Created by User on 11.05.2016.
 */
public class HotelsAdapter extends RecyclerView.Adapter<HotelsAdapter.HotelsViewHolder> {

    private final Cursor mCursor;
    private final HotelsOnClickListener mListener;

    public HotelsAdapter(Cursor cursor, HotelsOnClickListener listener) {
        mCursor = cursor;
        mListener = listener;
    }

    @Override
    public HotelsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sights, parent, false);
        return new HotelsViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(HotelsViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        String text = mCursor.getString(mCursor.getColumnIndex("NAME"));
        int imageResId = mCursor.getInt(mCursor.getColumnIndex("IMAGE_RESOURCE_ID"));

        holder.populateView(imageResId, text);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class HotelsViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage;
        TextView mText;

        public HotelsViewHolder(View itemView, final HotelsOnClickListener listener) {
            super(itemView);

            mImage = (ImageView) itemView.findViewById(R.id.image);
            mText = (TextView) itemView.findViewById(R.id.text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCursor.moveToPosition(getAdapterPosition());
                    listener.onHotelsClick(mCursor.getInt(mCursor.getColumnIndex("_id")));
                }
            });
        }

        public void populateView(int imageResId, String text) {
            mImage.setBackgroundResource(imageResId);
            mText.setText(text);
        }

    }
    public interface HotelsOnClickListener {
        void onHotelsClick(int id);
    }
}
