package com.mrhi2021.testmodule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {
    //3가지 유형의 늦은 초기화
    val et: EditText by lazy { findViewById(R.id.et) }
    val tv by lazy { findViewById(R.id.tv) as TextView }
    lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //lateinit var 변수 초기화
        btn= findViewById(R.id.btn)

        //버튼 클릭이벤트 리스너 등록 3가지 방법 [ 익명객체 만들어 적용하는 3가지 방법 ]
        //1. 기본적인 익명객체 문법
        btn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                Toast.makeText(this@MainActivity, "clicked", Toast.LENGTH_SHORT).show()
            }
        })

        //2. SAM 변환 [ 익명객체의 추상메소드가 1개일때만 사용가능한 기술 - 축약표현문법 : 추상메소드의 오버라이드 코드를 생략 -람다표기법 사용 ]
        btn.setOnClickListener( View.OnClickListener { v-> Toast.makeText(this@MainActivity, "SAM conversion clicked", Toast.LENGTH_SHORT).show() }  )

        //2.1 파라미터가 1개일때는 파라미터도 생략가능
        btn.setOnClickListener( View.OnClickListener { Toast.makeText(this@MainActivity, "SAM conversion clicked!", Toast.LENGTH_SHORT).show() }  )

        //3. setOnClickListener()메소드가 어차피 다른 클래스는 사용이 불가능 하기에 OnClickListener 인터페이스이름 조차도 생략하여 더 축약형으로..
        btn.setOnClickListener { v-> Toast.makeText(this@MainActivity, "완전 SAM 적용", Toast.LENGTH_SHORT).show() }

        //3.1 당연히 파라미터 생략가능 [ EditText의 글씨를 가져와서 TextView에 설정 - 코틀린의 getXXX(), setXXX()을 사용하지 않고 곧바로 property(멤버변수)를 제어하는 방식을 선호 ]
        btn.setOnClickListener { tv.text= et.text.toString() }

        //3.2 만약 파라미터를 생략했는데 {}안에서 사용하고 싶다면 특별한 키워드 it 사용
        btn.setOnClickListener {
            when(it.id){
                //익명객체를 생략했기에 Context파라미터에 전달한 this가 자연스럽게 MainActivity가 되어 그냥 사용할 수 있음.
                R.id.btn-> Toast.makeText(this, "선택한 버튼의 ID 값 : " + it.id, Toast.LENGTH_SHORT).show()
            }
        }


        //SAM은 리스너중에서 추상메소드가 1개인 모든 곳에서 사용이 가능함 - 롱클릭, 체크박스, 라디오그룹, 레이팅바 등...
        //1) LongClickListener
        btn.setOnLongClickListener {
            Toast.makeText(this, "long click", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true   //리턴값이 있을때의 코드
        }

        //2) CompoundButton Listener
        val cb:CheckBox= findViewById(R.id.cb)
        cb.setOnCheckedChangeListener( object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                Toast.makeText(this@MainActivity, "체크상태 : $isChecked", Toast.LENGTH_SHORT).show()
            }
        })

        //파라미터가 여러개면 생략할 수 없음 , 파라미터들의 변수명만 작성해도 되고. 자료형까지 명시해도 됨
        cb.setOnCheckedChangeListener{ buttonView, isChecked:Boolean ->
            Toast.makeText(this@MainActivity, "체크상태 : $isChecked", Toast.LENGTH_SHORT).show()
        }

        //cb.setOnCheckedChangeListener{ Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show() } //ERROR


        //3) RadioGroup Listener
        val rg:RadioGroup= findViewById(R.id.rg)
        rg.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                val rb: RadioButton= findViewById(checkedId)
                Toast.makeText(this@MainActivity, rb.text, Toast.LENGTH_SHORT).show()
            }
        })

        rg.setOnCheckedChangeListener { group, checkedId ->
            val rb: RadioButton= findViewById(checkedId)
            Toast.makeText(this@MainActivity, "선택 : ${rb.text}", Toast.LENGTH_SHORT).show()
        }


        //4) 특이하게 추상메소드가 1개가 아님에도 사용할 수도 있음. - SAM이라는 이름이 좀 어색
        et.addTextChangedListener( object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //자동으로 써진 TODO 를 지우거나 앞에 주석표시 없으면 에러
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                //TODO("Not yet implemented")
            }
        })

        //위 처럼 추상메소드가 3개인 인터페이스는 원래 SAM이 안됨. 하지만, addTextChangedListener()에서는 3개중 마지막 추상메소드만 사용하는 인터페이스로 변경하여 SAM처리해줌
        //즉,아래 SAM변환 메소드는 위 TextWatcher의 afterTextChanged() 추상메소드 임
        et.addTextChangedListener{
            Toast.makeText(this,"글씨변경 : " + it.toString(),Toast.LENGTH_SHORT).show()
        }

        //즉, 안드로이드 대부분의 리스너는 이런식으로 SAM 변환 코드 가능함..

        // SAM은 리스너 뿐이 아니라 안드로이드의 여러 콜백처리에 사용될 수 있음.
        // ex) 다른 액티비티를 실행할때 결과를 받아 돌아오는 ActivityResultLauncher


    }

    fun clickBtn(view: View) {
        //결과를 받아 돌아오는 Activity Result Launcher 실행객체
        val intent:Intent= Intent(this, SecondActivity::class.java)
        //resultLauncher.launch(intent)
        resultLauncher2?.launch(intent)
    }

    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), object : ActivityResultCallback<ActivityResult>{
        override fun onActivityResult(result: ActivityResult?) {
            if(result?.resultCode== RESULT_OK){
                //.......
            }
        }
    })

    // SAM 변환을 일부 사용하기
    val resultLauncher2: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback {
        if(it?.resultCode== RESULT_OK){
            //.........
        }
    })

}