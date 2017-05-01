package woact.android.zhenik.myreception.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import woact.android.zhenik.myreception.R;
import woact.android.zhenik.myreception.datalayer.entities.RoomType;
import woact.android.zhenik.myreception.view.holder.RoomTypesHolder;

public class RoomTypesRecyclerAdapter extends RecyclerView.Adapter<RoomTypesHolder> {
    private List<RoomType> types;

    public RoomTypesRecyclerAdapter(List<RoomType> types) {
        this.types = new ArrayList<RoomType>();
        this.types.addAll(types);
    }

    @Override
    public RoomTypesHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext())
                .inflate(R.layout.card_room_type, viewGroup, false);

        return new RoomTypesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RoomTypesHolder roomTypesViewHolder, int i) {
        RoomType roomType = types.get(i);
        roomTypesViewHolder.getTitleText().setText(roomType.getType());
        roomTypesViewHolder.getContentText().setText(roomType.getDescription());
    }

    @Override
    public int getItemCount() {
        return types.size();
    }
}

