package benznatthakul.au_restaurant_o

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_restaurant_list.*
import kotlinx.android.synthetic.main.content_restaurant_list.*
import kotlinx.android.synthetic.main.restaurant_ticket_1.view.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser



class RestaurantList : AppCompatActivity() {

    var shopList = ArrayList<shop>()
    var currentFirebaseUser: FirebaseUser? = null

    var listOfRestaurant = ArrayList<RestaurantObj>()
    var adapter: RestaurantAdapter? = null

    lateinit var mPager: ViewPager
    var path: IntArray = intArrayOf(R.drawable.restaurant001, R.drawable.pizza002, R.drawable.coffeeshop003, R.drawable.restaurant004)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_list)
        setSupportActionBar(toolbar)

        //get user info
        currentFirebaseUser = FirebaseAuth.getInstance().currentUser

        //load res from firebase
        var dbRef = FirebaseDatabase.getInstance().getReference()

        //find shop own by user only
        var query = dbRef.child("shop").orderByChild("owner").equalTo(currentFirebaseUser!!.uid)

        query .addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                Toast.makeText(applicationContext,"Failed to get res",Toast.LENGTH_LONG)
                Log.d("WVN","Failed")

            }

            override fun onDataChange(p0: DataSnapshot?) {
                Toast.makeText(applicationContext,"Loaded res",Toast.LENGTH_LONG)
                Log.d("WVN","Loaded")
                shopList.clear()
                if(p0!!.exists()){
//                    shopList.clear()
                    for(s in p0.children){
                        val shop = s.getValue(shop::class.java)
                        shopList.add(shop!!)
                    }
//                    adapter = RestaurantAdapter(applicationContext, shopList)

//                    gvListRestaurant.adapter = adapter

                }
                adapter = RestaurantAdapter(applicationContext, shopList)
                gvListRestaurant.adapter = adapter
            }

        })
        /*
        //load restaurantsObject
        listOfRestaurant.add(RestaurantObj("Ka Mhoo Meng Jai", "The Best Stewed Pork Leg", R.drawable.restaurant001))
        listOfRestaurant.add(RestaurantObj("Je Pu Jeed Jad", "Best Noodle Ever", R.drawable.restaurant004))
        listOfRestaurant.add(RestaurantObj("Restaurant 3", "Description of Restaurant", R.drawable.coffeeshop003))
        listOfRestaurant.add(RestaurantObj("Restaurant 4", "Description of Restaurant", R.drawable.pizza002))
        listOfRestaurant.add(RestaurantObj("Restaurant 5", "Description of Restaurant", R.drawable.coffeeshop005))
        listOfRestaurant.add(RestaurantObj("Restaurant 6", "Description of Restaurant", R.drawable.shop006))
        listOfRestaurant.add(RestaurantObj("Restaurant 7", "Description of Restaurant", R.drawable.constructions007))
        listOfRestaurant.add(RestaurantObj("Restaurant 8", "Description of Restaurant", R.drawable.shop008))
        listOfRestaurant.add(RestaurantObj("Restaurant 9", "Description of Restaurant", R.drawable.pizzeria009))
        listOfRestaurant.add(RestaurantObj("Restaurant 10", "Description of Restaurant", R.drawable.store010))
        adapter = RestaurantAdapter(this, listOfRestaurant)

        gvListRestaurant.adapter = adapter*/

        //TODO: CHANGE TO ADD BUTTON
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Navigate to adding page.", Snackbar.LENGTH_LONG)
                    .setAction("Add Restaurant", null).show()
            gotoadding()

        }



//        TODO: PAGE VIEWER IMAGE SLIDER
//        var adapter: PagerAdapter = PageView(this,path)
//        mPager = findViewById(R.id.pager)
//        mPager.adapter = adapter
//        mPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun onPageScrollStateChanged(state: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onPageSelected(position: Int) {
//                Toast.makeText(this@RestaurantList, path[position], Toast.LENGTH_LONG).show()
//            }
//        })
    }


    private fun gotoadding() {
        startActivity(Intent(applicationContext, AddingRestaurant::class.java))
    }

    class RestaurantAdapter : BaseAdapter {
        var listOfRestaurant = ArrayList<shop>()
        var context: Context? = null

        constructor(context: Context, listOfRestaurant: ArrayList<shop>) : super() {
            this.context = context
            this.listOfRestaurant = listOfRestaurant
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val restaurant = this.listOfRestaurant[position]
            var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var RestaurantView = inflator.inflate(R.layout.restaurant_ticket_1, null)

            //RestaurantView.ivRestaurantImage.setImageResource(restaurant.image!!)
            Picasso.with(this.context).load(restaurant.image).into(RestaurantView.ivRestaurantImage)
            RestaurantView.tvName.text = restaurant.shop_name

            RestaurantView.ivRestaurantImage.setOnClickListener {
                val intent = Intent(context, RestaurantDetail::class.java)
                intent.putExtra("name", restaurant.shop_name!!)
                intent.putExtra("des", restaurant.shop_description!!)
                intent.putExtra("image", restaurant.image!!)
                intent.putExtra("tel", restaurant.tel_number!!)
                intent.putExtra("addr", restaurant.shop_address!!)
                context!!.startActivity(intent)
            }

//            TODO - Delete shop
            RestaurantView.delbtn.setOnClickListener {
                var dbRef = FirebaseDatabase.getInstance().getReference()
                dbRef.child("shop").child(restaurant.shop_id).removeValue()

            }

            return RestaurantView
        }

        override fun getItem(position: Int): Any {
            return listOfRestaurant[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listOfRestaurant.size
        }

    }

}
