package admin.build1.ui.adapter;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import admin.build1.R;
import admin.build1.Services.BroadCastService;
import admin.build1.database.TraveliaDatabaseHelper;


public class TaxiAdapter extends RecyclerView.Adapter<TaxiAdapter.TaxiViewHolder> {
    private final Cursor mCursor;
    private final TaxiOnClickListener mListener;
    private RecyclerView mRecycler;

    public TaxiAdapter(Cursor cursor, TaxiOnClickListener listener) {
        mCursor = cursor;
        mListener = listener;
    }


    @Override
    public TaxiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_taxi, parent, false);
        return new TaxiViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(TaxiViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        String name = mCursor.getString(mCursor.getColumnIndex("NAME"));
        String text = mCursor.getString(mCursor.getColumnIndex("CONTACT"));
        int imageResId = mCursor.getInt(mCursor.getColumnIndex("IMAGE_RESOURCE_ID"));
        holder.populateView(imageResId,name, text);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }



    class TaxiViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage;
        TextView mName;
        TextView mText;

        public TaxiViewHolder(View itemView, final TaxiOnClickListener listener) {
            super(itemView);

            mImage = (ImageView) itemView.findViewById(R.id.taxiphoto);
            mName = (TextView) itemView.findViewById(R.id.taxiname);
            mText=(TextView)itemView.findViewById(R.id.taxitext);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCursor.moveToPosition(getAdapterPosition());
                    listener.onTaxiClick(mCursor.getInt(mCursor.getColumnIndex("_id")));
                }
            });
        }

        public void populateView(int imageResId, String name, String text) {
            mImage.setBackgroundResource(imageResId);
            mName.setText(name);
            mText.setText(text);
        }

    }
    public interface TaxiOnClickListener {
        void onTaxiClick(int id);
    }

    public void onClick1 (View view)
    {
    }

}
