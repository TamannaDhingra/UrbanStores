
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.netSet.urbanstores.R
import com.netSet.urbanstores.base.BaseActivity
import com.netSet.urbanstores.models.AllProductListModel
import com.netSet.urbanstores.ui.shops.ShopProducts.ShopProductsFrag
import kotlinx.android.synthetic.main.fragment_shop_products.view.*
import kotlinx.android.synthetic.main.shopproducts_view.view.*
import java.util.*

class ShopDiscountsAdapters(
    var context: Context?,
    var fragment: ShopProductsFrag,
    val shopProductsList: AllProductListModel,
    val baseActivity: BaseActivity
) : PagerAdapter() {
    // Layout Inflater
    var mLayoutInflater: LayoutInflater
    override fun getCount(): Int {
        // return the number of images
        return shopProductsList.productBanner?.size!!
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // inflating the item.xml
        val itemView: View = mLayoutInflater.inflate(R.layout.shopproducts_view, container, false)


        // Adding the View
        Objects.requireNonNull(container).addView(itemView)


        fragment.setImageUsingGlide(fragment, shopProductsList?.productBanner?.get(position)?.imageUrl.toString(), itemView.shopImg)
        Log.d("TAG", "instantiateItem: BannerImg" +   shopProductsList?.productBanner?.get(position)?.imageUrl.toString())
        itemView.shopImg.setOnClickListener {
            //Toast.makeText(context,"Hello",Toast.LENGTH_LONG).show()
        }

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

    // Viewpager Constructor
    init {
        mLayoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
}