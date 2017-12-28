package benznatthakul.au_restaurant_o

/**
 * Created by ntkbb on 27/12/2017.
 */
class RestaurantObj {

    var name: String? = null
    var des: String? = null
    var image: Int? = null
    var addr: String? = null
    var tel: String? = null

    constructor(name: String?, des: String?, image: Int?, addr: String?, tel: String?) {
        this.name = name
        this.des = des
        this.image = image
        this.addr = addr
        this.tel = tel
    }
}