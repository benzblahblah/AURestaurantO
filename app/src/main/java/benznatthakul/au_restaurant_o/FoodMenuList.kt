package benznatthakul.au_restaurant_o

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

import kotlinx.android.synthetic.main.activity_food_menu_list.*
import kotlinx.android.synthetic.main.content_food_menu_list.*
import kotlinx.android.synthetic.main.food_ticket.view.*

class FoodMenuList : AppCompatActivity() {

    var listOfFoods = ArrayList<FoodObj>()
    var adapter: FoodAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_menu_list)
        setSupportActionBar(toolbar)


        //        load foodObject
        listOfFoods.add(FoodObj("Cheese", "The Best Cheese from Finland", R.drawable.menu__cheese1, "150 Baht"))
        listOfFoods.add(FoodObj("Apple", "Apple from China", R.drawable.menu_apple1,"50 Baht"))
        listOfFoods.add(FoodObj("Bacon", "Bacon from the kitchen", R.drawable.menu_bacon,"70 Baht"))
        listOfFoods.add(FoodObj("Banana", "Banana from Thailand", R.drawable.menu_banana,"20 Baht"))
        listOfFoods.add(FoodObj("Bread", "Bread from France", R.drawable.menu_bread,"40 Baht"))
        listOfFoods.add(FoodObj("Coke Can", "Coke from 7-11", R.drawable.menu_can,"18 Baht"))
        listOfFoods.add(FoodObj("Candy", "Candy handmade by my child", R.drawable.menu_candy,"50 Baht"))
        listOfFoods.add(FoodObj("Carrot", "Carrot from my garden", R.drawable.menu_carrot,"30 Baht"))
        listOfFoods.add(FoodObj("Coffee", "Seeds Coffee from Starbuck", R.drawable.menu_coffee,"140 Baht"))
        listOfFoods.add(FoodObj("Cookies", "Do you love cookies? Try me, it's the recommended one.", R.drawable.menu_cookies,"300 Baht"))
        adapter = FoodAdapter(this, listOfFoods)

        gvListFood.adapter = adapter

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    class FoodAdapter: BaseAdapter {
        var listOfFoods = ArrayList<FoodObj>()
        var context: Context? = null
        constructor(context: Context, listOfFoods:ArrayList<FoodObj>):super() {
            this.context = context
            this.listOfFoods = listOfFoods
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val food = this.listOfFoods[position]
            var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var foodView = inflator.inflate(R.layout.food_ticket, null)
            foodView.ivMenuImage.setImageResource(food.image!!)
            foodView.tvName.text = food.name!!

            foodView.ivMenuImage.setOnClickListener {
                val intent = Intent(context, FoodDetail :: class.java)
                intent.putExtra("name", food.name!!)
                intent.putExtra("des", food.des!!)
                intent.putExtra("image", food.image!!)
                intent.putExtra("price", food.price!!)
                context!!.startActivity(intent)
            }
            return foodView

        }

        override fun getItem(position: Int): Any {
            return listOfFoods[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listOfFoods.size
        }

    }

}
