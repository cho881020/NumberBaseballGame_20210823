package com.neppplus.numberbaseballgame_20210823

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neppplus.numberbaseballgame_20210823.adapters.MessageAdapter
import com.neppplus.numberbaseballgame_20210823.datas.MessageData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val mMessageList = ArrayList<MessageData>()

    lateinit var mAdapter : MessageAdapter

//    세자리 문제 숫자를 저장하기 위한 ArrayList
    val mQuestionNumbers = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        세자리 랜덤 문제 만들기
        makeQuestionNumbers()


        mAdapter = MessageAdapter(this, R.layout.message_list_item, mMessageList)

        messageListView.adapter = mAdapter

        okBtn.setOnClickListener {

            val inputNumStr = numberEdt.text.toString()

            val msg = MessageData( inputNumStr, "USER" )

            mMessageList.add(msg)

            mAdapter.notifyDataSetChanged()


//            numberEdt의 문구를 비워주싶다.

            numberEdt.setText("")

//            리스트뷰를 최하단 (맨 마지막 데이터) 으로 내리고 싶다.

            messageListView.smoothScrollToPosition( mMessageList.size - 1 )


        }


    }

    fun makeQuestionNumbers() {

//        고정된 세개 숫자를 임시 문제로.
        mQuestionNumbers.add(4)
        mQuestionNumbers.add(7)
        mQuestionNumbers.add(1)

//        환영 메세지를 채팅창에 띄우자.
//        메세지 리스트에, 띄워줄 말들 데이터를 추가하자.

        mMessageList.add(  MessageData("어서오세요.", "CPU")  )
        mMessageList.add( MessageData("숫자야구 게임입니다.", "CPU") )
        mMessageList.add( MessageData("세자리 숫자를 맞춰주세요.", "CPU") )


    }

}