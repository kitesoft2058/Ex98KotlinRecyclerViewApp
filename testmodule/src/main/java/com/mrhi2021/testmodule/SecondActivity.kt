package com.mrhi2021.testmodule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class SecondActivity : AppCompatActivity() {

    val tv:TextView by lazy { findViewById(R.id.tv) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        //자바언어로 만든 클래스도 아무 문제없이 코틀린에서 곧바로 사용가능
        val p: Person = Person("sam", 20)  //코틀린 문법으로 자바클래스 객체 생성
        tv.text = "이름 : ${p.name}    나이:${p.age}"

        //data class
    }

}