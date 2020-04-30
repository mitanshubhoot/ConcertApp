package com.example.eventhomescreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>{

    Context context;
    List<Event> eventList;

    private OnItemClicked onClick;

    //make interface like this
    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public EventAdapter(Context context, List<Event> eventList){
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater  layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_event, null);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, final int i) {

        Event event = eventList.get(i);
        eventViewHolder.eventtitle.setText(event.getEventtitle());
        eventViewHolder.eventpicture.setImageDrawable(context.getResources().getDrawable(event.getEventpicture()));
        eventViewHolder.eventpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(i);
            }
        });
        eventViewHolder.eventtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(i);
            }
        });
        eventViewHolder.eventcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(i);
            }
        });
        eventViewHolder.eventlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder{

        TextView eventtitle, eventcategory;
        ImageView eventpicture;
        LinearLayout eventlayout;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            eventtitle = itemView. findViewById(R.id.eventtitle);
            eventcategory = itemView. findViewById(R.id.eventcategory);
            eventpicture = itemView. findViewById(R.id.eventpicture);
            eventlayout = itemView.findViewById(R.id.eventparent);
        }
    }

    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }
}

