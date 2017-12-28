package benznatthakul.au_restaurant_o

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_restaurant_detail.*


class RestaurantDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_detail)

        val bundle = intent.extras
        ivRestaurantImage.setImageResource(bundle.getInt("image"))
        tvName.text = bundle.getString("name")
        tvDetails.text = bundle.getString("des")

    }
}
