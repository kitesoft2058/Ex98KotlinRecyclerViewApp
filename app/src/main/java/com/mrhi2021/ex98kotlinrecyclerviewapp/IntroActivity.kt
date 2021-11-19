package com.mrhi2021.ex98kotlinrecyclerviewapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class IntroActivity : AppCompatActivity() {

    //var btn:Button= findViewById(R.id.btn)    //초기화를 안하면 에러여서 지금 여기서 find를 하면 에러.. 멤버변수가 만들어진 후 onCreate()가 호출되기에 아직 뷰가 만들어지지 않았음.
    var btn:Button?= null      //이렇게 시작은 null 값으로 지정

    //또는 늦은 초기화 lateinit
    //lateinit var btn2:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        //id가 btn인 Button에 클릭리스너 설정 [ import kotlinx.android.synthetic.main.activity_intro.* ] - deprecated
        //id를 이용하여 자동으로 참조변수가 연결되는 방식은 초기 android kotlin extension 기능이었음. 지금은 권장하지 않음

        //btn 참조하기
        btn= findViewById(R.id.btn)
        //Button?타입이기에 null safe 연산자 ?. 사용
        btn?.setOnClickListener( object: View.OnClickListener{
            override fun onClick(v: View?) {
                val intent= Intent(this@IntroActivity, MainActivity::class.java)
                startActivity(intent)
            }
        })


        //SAM Conversions [단일 추상메소드 변환  : ex.  val runnable = Runnable { println("This runs in a runnable") } ]
        //리스너 설정을 익명함수 표기법을 이용하면 더 쉽게 코딩 가능. 단, 그 리스너 인터페이스의 추상메소드가 1개일 때[단일 추상 메소드 : SAM (Single Abstract Method]만 사용 가능
//        btn?.setOnClickListener { v -> Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show() }


    }//onCreate Method...

    //onclick속성에 지정된 콜백메소드
    fun clickExit(view: View) {
        finish()
    }

}//IntroActivity class...