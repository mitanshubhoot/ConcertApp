package com.example.eventhomescreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.rubensousa.gravitysnaphelper.GravityPagerSnapHelper;

import java.util.ArrayList;
import java.util.List;


public class HomeScreen extends AppCompatActivity implements EventAdapter.OnItemClicked {

    private Button button;
    RecyclerView eventsplace;
    EventAdapter eventAdapter;
    List<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeScreen.this,Profile.class);
                startActivity(a);
            }
        });


        eventsplace = findViewById(R.id.eventsplace);

        eventList = new ArrayList<>();

        eventList.add(
                new Event(
                        "Lincoln Martin Concert",
                        "Concert",
                        R.drawable.guitar

                )
        );

        eventList.add(
                new Event(
                        "Indian Classical Music",
                        "Competition",
                        R.drawable.classical

                )
        );

        eventList.add(
                new Event(
                        "Lincoln Martin Concert",
                        "Concert",
                        R.drawable.guitar

                )
        );

        eventList.add(
                new Event(
                        "Lincoln Martin Concert",
                        "Concert",
                        R.drawable.guitar

                )
        );

        eventList.add(
                new Event(
                        "Indian Classical Music",
                        "Competition",
                        R.drawable.classical

                )
        );

        eventList.add(
                new Event(
                        "Lincoln Martin Concert",
                        "Concert",
                        R.drawable.guitar

                )
        );
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        eventsplace.setLayoutManager(linearLayoutManager);
        eventsplace.setHasFixedSize(true);
        eventAdapter = new EventAdapter(this, eventList);
        eventsplace.setAdapter(eventAdapter);
        eventAdapter.setOnClick((EventAdapter.OnItemClicked) HomeScreen.this);

        final SnapHelper snapHelper = new GravityPagerSnapHelper(Gravity.START);
        snapHelper.attachToRecyclerView(eventsplace);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                RecyclerView.ViewHolder viewHolderDefault = eventsplace.findViewHolderForAdapterPosition(0);
                LinearLayout eventparentDefault = viewHolderDefault.itemView.findViewById(R.id.eventparent);
                eventparentDefault.animate().scaleY(1).scaleX(1).setDuration(350).setInterpolator(new AccelerateInterpolator()).start();
                LinearLayout eventbadgeDefault = viewHolderDefault.itemView.findViewById(R.id.eventbadge);
                eventbadgeDefault.animate().alpha(1).setDuration(300).start();
            }
        }, 100);
        eventsplace.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    View view = snapHelper.findSnapView(linearLayoutManager);
                    int pos = linearLayoutManager.getPosition(view);
                    RecyclerView.ViewHolder viewHolder = eventsplace.findViewHolderForAdapterPosition(pos);

                    LinearLayout eventparent = viewHolder.itemView.findViewById(R.id.eventparent);
                    eventparent.animate().scaleY(1).scaleX(1).setDuration(350).setInterpolator(new AccelerateInterpolator()).start();

                    LinearLayout eventbadge = viewHolder.itemView.findViewById(R.id.eventbadge);
                    eventbadge.animate().alpha(1).setDuration(300).start();


                    TextView eventtitle = viewHolder.itemView.findViewById(R.id.eventtitle);
                    eventtitle.animate().scaleY(1).scaleX(1).setDuration(300).start();

                }
                else{

                    View view = snapHelper.findSnapView(linearLayoutManager);
                    int pos = linearLayoutManager.getPosition(view);
                    RecyclerView.ViewHolder viewHolder = eventsplace.findViewHolderForAdapterPosition(pos);

                    LinearLayout eventparent = viewHolder.itemView.findViewById(R.id.eventparent);
                    eventparent.animate().scaleY(0.7f).scaleX(0.7f).setDuration(350).setInterpolator(new AccelerateInterpolator()).start();

                    LinearLayout eventbadge = viewHolder.itemView.findViewById(R.id.eventbadge);
                    eventbadge.animate().alpha(0).setDuration(300).start();
                }
            }


            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
    public void openActivity2(){
        Intent intent = new Intent(this, Registered.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(getApplicationContext(), moreInfo.class);
        Bundle bundle = new Bundle();
        bundle.putString("title",eventList.get(position).eventtitle);
        bundle.putString("icon", String.valueOf(eventList.get(position).eventpicture));
        bundle.putString("category",eventList.get(position).eventcategory);
        bundle.putString("key", String.valueOf(position));
        i.putExtras(bundle);
        startActivity(i);
    }
}
