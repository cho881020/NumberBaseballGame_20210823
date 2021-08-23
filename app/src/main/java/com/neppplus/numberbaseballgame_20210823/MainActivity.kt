package com.neppplus.numberbaseballgame_20210823

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neppplus.numberbaseballgame_20210823.adapters.MessageAdapter
import com.neppplus.numberbaseballgame_20210823.datas.MessageData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val mMessageList = ArrayList<MessageData>()

    lateinit var mAdapter : MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mMessageList.add(  MessageData("안녕하세요", "CPU")  )
        mMessageList.add(  MessageData("반갑습니다", "USER")  )

        mAdapter = MessageAdapter(this, R.layout.message_list_item, mMessageList)

        messageListView.adapter = mAdapter

        okBtn.setOnClickListener {

            val inputNumStr = numberEdt.text.toString()

            val msg = MessageData( inputNumStr, "USER" )

            mMessageList.add(msg)

            mAdapter.notifyDataSetChanged()


//            numberEdt의 문구를 비워주싶다.

            numberEdt.setText("")


        }


    }
}