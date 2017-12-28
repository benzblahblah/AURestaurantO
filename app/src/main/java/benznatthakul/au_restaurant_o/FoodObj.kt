package benznatthakul.au_restaurant_o

/**
 * Created by ntkbb on 28/12/2017.
 */
class FoodObj {

    var name: String? = null
    var des: String? = null
    var image: Int? = null
    var price: String? = null

    constructor(name: String?, des: String?, image: Int?, price: String?) {
        this.name = name
        this.des = des
        this.image = image
        this.price = price
    }
}