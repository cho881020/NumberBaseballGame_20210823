package com.neppplus.numberbaseballgame_20210823

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

//        만약 3S였다 -> 축하한다는 메세지 추가 출력 -> 입력 못하게 막자.

        if (strikeCount == 3) {
            mMessageList.add(  MessageData("축하합니다! 정답을 맞췄습니다.", "CPU")  )

            mAdapter.notifyDataSetChanged()

            messageListView.smoothScrollToPosition( mMessageList.size - 1 )

            Toast.makeText(this, "게임을 종료합니다.", Toast.LENGTH_SHORT).show()

//            입력을 막는다 => numberEdt 를 enabled : false
            numberEdt.isEnabled = false

        }

    }

    fun makeQuestionNumbers() {

//        고정된 세개 숫자를 임시 문제로.
//        mQuestionNumbers.add(4)
//        mQuestionNumbers.add(7)
//        mQuestionNumbers.add(1)

//        랜덤한 3개 숫자를 진짜 문제로.
//        1~9 숫자만 사용.
//        이미 나온 숫자는 또 나오면 안됨.

        for ( i  in 0..2) {

            while (true) {

//                1~9 사이의 랜덤 정수 추출
                val randomNum = ( Math.random() * 9  + 1 ).toInt()

//                mQue...에 이미 들어있는지 검사.

                var isDuplOk = true

                for ( num   in mQuestionNumbers ) {
                    if (num == randomNum) {
//                        중복된숫자 발견!! 쓰면 안된다.
                        isDuplOk = false
                    }
                }

//                중복검사를 통과한 상태로 유지 : 써도 된다. 배열에 추가해도 된다.
                if (isDuplOk) {
                    mQuestionNumbers.add( randomNum )
//                    무한반복 탈출
                    break
                }


            }

        }


        for (num  in mQuestionNumbers) {
            Log.d("출제된숫자", num.toString())
        }



//        환영 메세지를 채팅창에 띄우자.
//        메세지 리스트에, 띄워줄 말들 데이터를 추가하자.

        mMessageList.add(  MessageData("어서오세요.", "CPU")  )
        mMessageList.add( MessageData("숫자야구 게임입니다.", "CPU") )
        mMessageList.add( MessageData("세자리 숫자를 맞춰주세요.", "CPU") )


    }

}