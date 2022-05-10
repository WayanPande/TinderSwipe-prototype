package com.example.tinderswipetest

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yuyakaido.android.cardstackview.*


class MainActivity : AppCompatActivity() {

    private lateinit var listAdapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        LISTENER WHILE CARD IN INTERACTION
        val manager = CardStackLayoutManager(this, object : CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {}
            override fun onCardSwiped(direction: Direction?) {
                if (direction != Direction.Bottom) {
                    Toast.makeText(this@MainActivity, "Not interested", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onCardRewound() {}
            override fun onCardCanceled() {}
            override fun onCardAppeared(view: View?, position: Int) {}
            override fun onCardDisappeared(view: View?, position: Int) {}
        })

        val list = ArrayList<String>()
        for (i in 1..5) {
            list.add(i.toString())
        }

        manager.setStackFrom(StackFrom.Bottom)
        manager.setVisibleCount(list.size)


        val cardStactView = findViewById<CardStackView>(R.id.recycle_view)
        cardStactView.layoutManager = manager



        listAdapter = CardAdapter(list)
        cardStactView.adapter = listAdapter

        val rewindBtn = findViewById<Button>(R.id.rewind_btn)
        rewindBtn.setOnClickListener {
            cardStactView.rewind()
        }

        val yesBtn = findViewById<Button>(R.id.yes_btn)
        yesBtn.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStactView.swipe()
        }

        val noBtn = findViewById<Button>(R.id.no_btn)
        noBtn.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStactView.swipe()
        }

//        CARD ONCLICK LISTENER
        listAdapter.setOnItemClickCallBack(object : CardAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: String) {
                Toast.makeText(this@MainActivity, data, Toast.LENGTH_SHORT)
                    .show()
                val setting = SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Bottom)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()
                manager.setSwipeAnimationSetting(setting)
                cardStactView.swipe()
            }
        })


    }
}