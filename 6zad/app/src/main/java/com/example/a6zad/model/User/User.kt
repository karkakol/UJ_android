


import com.example.a6zad.model.Basket.Basket
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User : RealmObject {
    @PrimaryKey var id: String? = null
    var firstName: String? = null
    var lastName: String?= null
    var birthDate: String?= null
    var basket: Basket?= null


    constructor(id: String?, firstName: String?, lastName: String?, birthDate: String?, basket: Basket?) {
        this.id = id
        this.firstName = firstName
        this.lastName = lastName
        this.birthDate = birthDate
        this.basket = basket
    }

    constructor() {} // RealmObject subclasses must provide an empty constructor
}