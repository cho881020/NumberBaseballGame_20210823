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


//            컴퓨터가 ?S ?B인지 판단해서 메세지 추가. (답장)

            checkAnswer( inputNumStr.toInt() )

        }


    }

    fun checkAnswer(inputNum : Int) {

//        사람이 입력한 숫자가 ?S ?B인지 판단 하는 함수.

//        사람이 입력한 숫자를 => 각 자리별로 나눠서 => 목록에 대입.

        val userInputNumArr = ArrayList<Int>()

        userInputNumArr.add(  inputNum / 100  )  // 100의자리가 몇? 456 : 4 => 4
        userInputNumArr.add( inputNum / 10 % 10 ) // 10의자리가 몇? 456: 5 => 4"5"
        userInputNumArr.add( inputNum % 10 ) // 1의자리가 몇? 456: 6 ?

        var strikeCount = 0
        var ballCount = 0

        for ( i   in 0..2  ) {

            for ( j   in 0..2 ) {

//                내가 입력한 숫자 i번째랑,  컴퓨터가 낸 숫자 j번째가 같은 값인가?
                if ( userInputNumArr[i] == mQuestionNumbers[j] ) {

//                    같은 숫자를 찾았다!
//                    위치도 같은 위치였는지? 같으면 S, 다르면 B
                    if (i == j) {
                        strikeCount++
                    }
                    else {
                        ballCount++
                    }

                }

            }

        }


//        ?S ?B인지를 컴퓨터가 말하는걸로 처리.
        mMessageList.add(  MessageData("${strikeCount}S ${ballCount}B 입니다.", "CPU")  )

        mAdapter.notifyDataSetChanged()

        messageListView.smoothScrollToPosition( mMessageList.size - 1 )

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