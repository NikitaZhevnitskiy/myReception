package woact.android.zhenik.myreception.view.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import woact.android.zhenik.myreception.R;

public class RoomTypesHolder extends RecyclerView.ViewHolder {

        private TextView titleText;
        private TextView contentText;
        private CardView card;

        public RoomTypesHolder(View itemView) {
            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.card_room_type_title);
            contentText = (TextView) itemView.findViewById(R.id.card_room_type_content);
            card = (CardView) itemView;
        }

    public TextView getTitleText() {
        return titleText;
    }

    public void setTitleText(TextView titleText) {
        this.titleText = titleText;
    }

    public TextView getContentText() {
        return contentText;
    }

    public void setContentText(TextView contentText) {
        this.contentText = contentText;
    }

    public CardView getCard() {
        return card;
    }

    public void setCard(CardView card) {
        this.card = card;
    }
}
