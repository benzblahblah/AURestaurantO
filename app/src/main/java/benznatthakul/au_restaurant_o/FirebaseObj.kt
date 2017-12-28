package benznatthakul.au_restaurant_o

data class shop(
        var shop_id: String,
        var image: String,
        var owner: String,
        var rating: Int,
        var shop_address: String,
        var shop_description: String,
        var shop_name: String,
        var shop_type: String,
        var tel_number: String
){
    constructor() : this("","","",0,"","","","","")
}