package com.mrhi2021.ex98kotlinrecyclerviewapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
//import kotlinx.android.synthetic.main.recycler_item.view.*

//코틀린에서 Adapter를 상속받을 때 반드시 <>제네릭 표시 해야 함
class MyAdapter constructor(val context:Context, val items:ArrayList<Item>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var inflater= LayoutInflater.from(context)
        var itemView= inflater.inflate(R.layout.recycler_item, parent, false)
        var holder= VH(itemView)
        return holder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //첫번째 파라미터 : holder를 VH로 형변환
        //VH vh= (VH)holder;
        val vh:VH= holder as VH //코틀린에서 클래스들의 형변환 연산자 as

        val item= items.get(position)
        vh.tvTitle.setText(item.title)
        vh.tvMsg.text= item.msg

        Glide.with(context).load(item.img).into(vh.iv)
    }

    //아이템 1개의 뷰를 저장하는 ViewHolder클래스 - inner class [ inner 키워드가 있어야 outer 클래스의 멤버를 자유롭게 사용할 수 있음]
    inner class VH constructor(itemView: View): RecyclerView.ViewHolder(itemView){

        // by lazy : 지연초기화를 이용하여 자식뷰들 참조
        val tvTitle:TextView by lazy { itemView.findViewById(R.id.tvTitle) }
        val tvMsg:TextView by lazy { itemView.findViewById(R.id.tvMsg) }
        val iv:ImageView by lazy { itemView.findViewById(R.id.iv) }

        init {
//            itemView.setOnClickListener( object : View.OnClickListener{
//                override fun onClick(v: View?) {
//                    //클릭한 itemView의 위치
//                    val item= items.get(layoutPosition)
//
//                    val intent= Intent(context, ItemActivity::class.java)
//                    intent.putExtra("title", item.title)
//                    intent.putExtra("msg", item.msg)
//                    intent.putExtra("img", item.img)
//
//                    context.startActivity(intent)
//                }
//            })

            //SAM conversion 이용하여 이벤트처리
            itemView.setOnClickListener{ v ->

                //getLayoutPosition()을 안해도 이미 Adapter클래스의 property 로 layoutposition 이 있음.
                val item= items.get(layoutPosition)

                val intent= Intent(context, ItemActivity::class.java)
                intent.putExtra("title", item.title)
                intent.putExtra("msg", item.msg)
                intent.putExtra("img", item.img)

                context.startActivity(intent)
            }

        }
    }

}