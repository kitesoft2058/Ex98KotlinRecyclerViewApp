package com.mrhi2021.ex98kotlinrecyclerviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    //대량의 데이터 property[속성:멤버변수]
    var items= arrayListOf<Item>() //ArrayList<Item>와 동일하게 동작
    //var items= ArrayList<Item>()  //이렇게 선언해도 됨

    // 뷰 참조변수들을 한번 뷰객체를 참조하면 다른 뷰객체로 바꾸어 참조하는 경우가 거의 없기에 var보다 val을 선호함
    // 이때, 초기화 안하면 에러 - 하지만 recycler의 객체 참조값을 지금 초기화 할 수 없음 [ onCreate() 에서 findViewById()를 해야 하기에..]
    // 그래서 늦은초기화 사용[ lateinit 은 var에만 사용할 수 있기에 by lazy 지연초기화 사용]
    val recycler:RecyclerView by lazy { findViewById(R.id.recycler) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //대량의 데이터들 추가 [테스트목적]
        items.add( Item("sam","Hello. Kotlin", R.drawable.newyork) )
        items.add( Item("robin","Nice to meet you", R.drawable.paris) )
        items.add( Item("tom","Have a good day", R.drawable.sydney) )
        items.add( Item("lee","Do you have fun", R.drawable.newyork) )
        items.add( Item("sam","Hello. Kotlin", R.drawable.newyork) )
        items.add( Item("robin","Nice to meet you", R.drawable.paris) )
        items.add( Item("tom","Have a good day", R.drawable.sydney) )
        items.add( Item("lee","Do you have fun", R.drawable.newyork) )

        //리사이클러뷰안에 이미 아답터 프로퍼티(멤버변수)가 있어서 아답터객체를 대입해 주면 됨
        recycler.adapter= MyAdapter(this, items)

        //리사이클러뷰에 레이아웃매니저 붙이기
        recycler.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    //액티비티가 화면에 보여질 때 자동으로 발동하는 콜백 라이프사이클 메소드
    override fun onResume() {
        super.onResume()

        //리사이클러뷰를 갱신하려면 아답터에게 데이터변경을 공지!!!!
        //혹시 adapter가 null이면 에러가 나기에 그냥 문법적으로 실수할 여지가
        //없도록... 특이한 연산자 적용 ?. (null이 아닐때 실행해!! - 코드안전성 문법)
        recycler.adapter?.notifyDataSetChanged()

        //또는 널이 나오면 그냥 예외발생해도 되니까.. 그냥 해..라는 문법 !!
        //recycler.adapter!!.notifyDataSetChanged()
    }

    //옵션메뉴를 만드는 작업을 하는 콜백메소드
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //MenuInflater를 get하는 작업 필요없이 이미 이 액티비티의 멤버변수로 menuInflater객체가 있음
        menuInflater.inflate(R.menu.option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //옵션메뉴아이템 선택하면 자동으로 발동하는 콜백메소드
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //switch문법이 없기에 대신에 when 문법
        when(item.itemId){
            R.id.aa-> Toast.makeText(this, "aa", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }



}