package benznatthakul.au_restaurant_o

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_restaurant_detail.*
import kotlinx.android.synthetic.main.restaurant_ticket_1.view.*


class RestaurantDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_detail)

        val bundle = intent.extras
        Picasso.with(applicationContext).load(bundle.getString("image")).into(ivRestaurantImage)
        //ivRestaurantImage.setImageResource(bundle.getInt("image"))
        tvName.text = bundle.getString("name")
        tvDetails.text = bundle.getString("des")
        tvAddr.text = bundle.getString("addr")
        tvTel.text = bundle.getString("tel")

    }
}
